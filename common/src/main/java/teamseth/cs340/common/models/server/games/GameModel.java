package teamseth.cs340.common.models.server.games;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.ServerModelRoot;
import teamseth.cs340.common.models.server.cards.DestinationDeck;
import teamseth.cs340.common.models.server.cards.ResourceDeck;
import teamseth.cs340.common.models.server.chat.ChatRoom;
import teamseth.cs340.common.models.server.history.CommandHistory;
import teamseth.cs340.common.models.server.users.User;
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
        ServerModelRoot.chat.upsert(newRoom, token);
        ServerModelRoot.cards.upsert(newDestDeck, token);
        ServerModelRoot.cards.upsert(newResDeck, token);
        ServerModelRoot.history.upsert(history, token);
        game.setState(GameState.START);
        game.setChatRoom(newRoom.getId());
        game.setDestinationDeck(newDestDeck.getId());
        game.setResourceDeck(newResDeck.getId());
        game.setHistory(history.getId());
    }

    public void leave(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        Game game = this.get(gameId);
        if (!game.hasPlayer(token.getUser())) throw new ModelActionException();
        if (!game.getState().equals(GameState.PREGAME)) throw new ModelActionException();
        game.removePlayer(token.getUser());
        if (game.getPlayers().size() == 0) game.setState(GameState.DELETED);
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
}
