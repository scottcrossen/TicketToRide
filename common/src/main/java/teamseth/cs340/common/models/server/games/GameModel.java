package teamseth.cs340.common.models.server.games;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.commands.client.AddDestinationCardCommand;
import teamseth.cs340.common.commands.client.AddResourceCardCommand;
import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.commands.client.InitialChooseDestinationCardCommand;
import teamseth.cs340.common.commands.client.RemoveDestinationCardCommand;
import teamseth.cs340.common.commands.client.SeedFaceUpCardsCommand;
import teamseth.cs340.common.commands.client.SetGameStateCommand;
import teamseth.cs340.common.commands.client.SetPlayerLongestPathCommand;
import teamseth.cs340.common.commands.client.UpdatePlayerPointsByDestinationCardCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.ServerModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.boards.Routes;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.DestinationDeck;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.cards.ResourceDeck;
import teamseth.cs340.common.models.server.carts.CartSet;
import teamseth.cs340.common.models.server.chat.ChatRoom;
import teamseth.cs340.common.models.server.history.CommandHistory;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.util.RouteCalculator;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class GameModel extends AuthAction implements IModel<Game> {
    private static GameModel instance;

    public static GameModel getInstance() {
        if(instance == null) {
            instance = new GameModel();
        }
        return instance;
    }

    private HashSet<Game> games = new HashSet<Game>();

    public Game create(AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        UUID userId = token.getUser();
        if (playerInGame(userId)) throw new ModelActionException();
        Game game = new Game();
        User user = ServerModelRoot.getInstance().users.getById(userId);
        game.addPlayer(user);
        games.add(game);
        return game;
    }

    public void join(UUID gameId, AuthToken token) throws ModelActionException, ResourceNotFoundException, UnauthorizedException {
        AuthAction.user(token);
        UUID userId = token.getUser();
        if (playerInGame(userId)) throw new ModelActionException();
        User user = ServerModelRoot.getInstance().users.getById(userId);
        Game game = this.get(gameId);
        if (!game.getState().equals(GameState.PREGAME)) throw new ModelActionException();
        game.addPlayer(user);
    }

    public void start(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        Game game = this.get(gameId);
        if (!game.hasPlayer(token.getUser())) throw new ModelActionException();
        if (!game.getState().equals(GameState.PREGAME)) throw new ModelActionException();
        ChatRoom newRoom = new ChatRoom();
        DestinationDeck newDestDeck = new DestinationDeck();
        ResourceDeck newResDeck = new ResourceDeck();
        CommandHistory history = new CommandHistory();
        Routes routes = new Routes();
        CartSet cartSet = new CartSet();
        ServerModelRoot.chat.upsert(newRoom, token);
        ServerModelRoot.cards.upsert(newDestDeck, token);
        ServerModelRoot.cards.upsert(newResDeck, token);
        ServerModelRoot.history.upsert(history, token);
        ServerModelRoot.board.upsert(routes, token);
        ServerModelRoot.carts.upsert(cartSet, token);
        game.setChatRoom(newRoom.getId());
        game.setDestinationDeck(newDestDeck.getId());
        game.setResourceDeck(newResDeck.getId());
        game.setHistory(history.getId());
        game.setRoutes(routes.getId());
        game.setCarts(cartSet.getId());
        ServerModelRoot.history.forceAddCommandToHistory(history.getId(), new SeedFaceUpCardsCommand(newResDeck.getFaceUp(), game.getPlayers(), token.getUser()), token);
        seedGame(game, token);
        game.setState(GameState.START);
    }

    private void seedGame(Game game, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException {
        UUID historyId = game.getHistory();
        UUID destinationDeck = game.getDestinationDeck();
        UUID resourceDeck = game.getResourceDeck();
        // Can't use streams because java sucks and has to handle exceptions without deferring them.
        Iterator<UUID> iterator = game.getPlayers().iterator();
        while (iterator.hasNext()) {
            UUID playerId = iterator.next();
            for (int i = 0; i < 4; i++) {
                ResourceColor newCard = ServerModelRoot.cards.drawResourceCard(resourceDeck, token);
                ServerModelRoot.history.forceAddCommandToHistory(historyId, new AddResourceCardCommand(newCard, playerId), token);
            }
            for (int i = 0; i < 3; i++) {
                DestinationCard newCard = ServerModelRoot.cards.drawDestinationCard(destinationDeck, token);
                ServerModelRoot.history.forceAddCommandToHistory(historyId, new AddDestinationCardCommand(newCard, playerId), token);
            }
        }
    }

    public void leave(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        Game game = this.get(gameId);
        if (!game.hasPlayer(token.getUser())) throw new ModelActionException();
        if (!game.getState().equals(GameState.PREGAME)) throw new ModelActionException();
        game.removePlayer(token.getUser());
        if (game.getPlayers().size() == 0) game.setState(GameState.DELETED);
    }

    public boolean attemptPlayGame(UUID gameId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException {
        AuthAction.user(token);
        Game game = get(gameId);
        UUID historyId = game.getHistory();
        Set<UUID> destinationCardsDecided = new TreeSet<>();
        ServerModelRoot.getInstance().history.getAllCommands(historyId).forEach((IHistoricalCommand command) -> {
            if (command instanceof InitialChooseDestinationCardCommand) {
                destinationCardsDecided.add(command.playerOwnedby());
            }
        });
        boolean success = game.getPlayers().stream().allMatch((UUID player) -> destinationCardsDecided.contains(player));
        if (success) {
            game.setState(GameState.PLAYING);
        }
        return success;
    }

    public HashSet<Game> getAll() {
        return games;
    }

    public Game get(UUID gameId) throws ResourceNotFoundException {
        Game output = games.stream().filter(game -> game.getId().equals(gameId)).findFirst().orElseThrow(() -> new ResourceNotFoundException());
        return output;
    }

    public HashSet<Game> getAfter(Instant instant) {
        return (HashSet<Game>) games.stream().filter(game -> game.getUpdate().compareTo(instant) > 0).collect(Collectors.toSet());
    }
    public Optional<Game> getAfter(UUID id, Instant instant) throws ResourceNotFoundException {
        return games.stream().filter(game -> game.getUpdate().compareTo(instant) > 0 && game.getId().equals(id)).findFirst();
    }

    private boolean playerInGame(UUID userId) {
        return games.stream().anyMatch(game -> game.hasPlayer(userId));
    }

    private void endGame(UUID gameId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException {
        Game game = get(gameId);
        List<IHistoricalCommand> destinationCardCommands = ServerModelRoot.getInstance().history.getAllCommands(game.getHistory());
        Map<UUID, Set<DestinationCard>> playerDestinationCards = new HashMap<>();
        for (IHistoricalCommand command : destinationCardCommands) {
            UUID playerId = command.playerOwnedby();

            if (command instanceof AddDestinationCardCommand) {
                DestinationCard card = ((AddDestinationCardCommand) command).getDestinationCard();
                try {
                    playerDestinationCards.get(playerId).add(card);
                } catch (Exception e) {
                    Set<DestinationCard> cards = new HashSet<DestinationCard>();
                    cards.add(card);
                    playerDestinationCards.put(playerId, cards);
                }
            } else if (command instanceof InitialChooseDestinationCardCommand) {
                Optional<DestinationCard> cardOption = ((InitialChooseDestinationCardCommand) command).getDestinationCard();
                try {
                    cardOption.map((DestinationCard card) -> { playerDestinationCards.get(playerId).remove(card); return card; });
                } catch (Exception e) {
                    System.out.println("Error: Code 1");
                }
            } else if (command instanceof RemoveDestinationCardCommand) {
                DestinationCard card = ((RemoveDestinationCardCommand) command).getDestinationCard();
                try {
                    playerDestinationCards.get(playerId).remove(card);
                } catch (Exception e) {
                    System.out.println("Error: Code 2");
                }
            }
        }
        UUID historyId = game.getHistory();
        Set<UUID> allPlayers = game.getPlayers();
        for (UUID playerId : playerDestinationCards.keySet()) {
            for (DestinationCard card : playerDestinationCards.get(playerId)) {
                ServerModelRoot.getInstance().history.forceAddCommandToHistory(historyId, new UpdatePlayerPointsByDestinationCardCommand(card, allPlayers, playerId), token);
            }
        }
        UUID longestPathPlayer = null;
        int longestPath = -1;
        for (UUID playerId : game.getPlayers()) {
            Set<Route> routes = ServerModelRoot.getInstance().board.getByPlayer(game.getRoutes(), playerId);
            int playerPathScore = RouteCalculator.calculateLongestPath(routes);
            if (playerPathScore > longestPath) {
                longestPathPlayer = playerId;
                longestPath = playerPathScore;
            }
        }
        ServerModelRoot.getInstance().history.forceAddCommandToHistory(historyId, new SetPlayerLongestPathCommand(game.getPlayers(), longestPathPlayer), token);
        game.setState(GameState.FINISHED);
        ServerModelRoot.getInstance().history.forceAddCommandToHistory(historyId, new SetGameStateCommand(GameState.FINISHED, game.getPlayers(), token.getUser()), token);
    }

    private void attemptEndGame(UUID gameId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException {
        UUID currentPlayerId = token.getUser();
        Game game = get(gameId);
        Optional<UUID> nextPlayerIdOption = game.getWhosTurnItIsNextById(currentPlayerId);
        UUID cartId = game.getCarts();
        boolean nextPlayerHasNoCarts = nextPlayerIdOption.map((UUID nextPlayerId) -> {
            try {
                int carts_of_next = ServerModelRoot.getInstance().carts.getPlayerCartsById(cartId, nextPlayerId, token);
                return carts_of_next <= 0;
            } catch (Exception e) {
                return false;
            }
        }).orElseGet(() -> false);
        if (nextPlayerHasNoCarts) {
            endGame(gameId, token);
        }
    }

    public void nextPlayerTurn(UUID gameId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException {
        AuthAction.user(token);
        try {
            attemptEndGame(gameId, token);
        } catch (UnauthorizedException e) {
            throw e;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (ModelActionException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("Error: Code 3");
        }
        get(gameId).nextTurn();
    }
}
