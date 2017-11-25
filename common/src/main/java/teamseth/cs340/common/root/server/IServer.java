package teamseth.cs340.common.root.server;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.NotYourTurnException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.chat.Message;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IServer {

    // Game model methods
    public Game createGame(AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;
    public HashSet<Game> listGames();
    public HashSet<Game> listGamesAfter(Instant instant);
    public void joinGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;
    public void startGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;
    public Game getGame(UUID gameId) throws ResourceNotFoundException;
    public void leaveGame(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException;
    public Optional<Game> getGameAfter(UUID gameId, Instant instant) throws ResourceNotFoundException;
    public boolean attemptStartGame(UUID gameId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException;
    public void nextPlayerTurn(UUID gameId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException;
    public Optional<UUID> getWhosTurnItIs(UUID gameId) throws ResourceNotFoundException;
    public void playerLoginHelper(AuthToken token) throws UnauthorizedException;

    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException;
    public AuthToken register(UserCreds creds) throws UnauthorizedException;

    // Card model methods
    public ResourceColor drawResourceCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public DestinationCard drawDestinationCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public void returnResourceCard(UUID deckId, ResourceColor card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public void returnDestinationCard(UUID deckId, DestinationCard card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public Optional<ResourceColor> drawFaceUpCard(UUID deckId, ResourceColor card, AuthToken token) throws ModelActionException, ResourceNotFoundException, UnauthorizedException;
    public List<ResourceColor> checkAndResuffleFaceUpCards(UUID deckId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException;

    // Chat model methods
    public void sendMessage(UUID room, Message message, AuthToken token) throws UnauthorizedException, ResourceNotFoundException;
    public ArrayList<Message> getMessages(UUID room) throws ResourceNotFoundException;
    public ArrayList<Message> getMessagesAfter(UUID room, int size) throws ResourceNotFoundException;

    // History model methods
    public LinkedList<IHistoricalCommand> getCommandsAfter(UUID historyId, Optional<UUID> afterId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException;
    public void addCommandToHistory(UUID gameId, UUID historyId, IHistoricalCommand command, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException, NotYourTurnException;
    public void forceAddCommandToHistory(UUID historyId, IHistoricalCommand command, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException;
    public boolean playerHasChoseInitialCards(UUID historyId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException;

    // Board methods
    public int claimRoute(UUID routeSetId, CityName city1, CityName city2, List<ResourceColor> colors, Optional<ResourceColor> routeColor, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;

    // Cart methods
    public void decrementPlayerCarts(UUID cartId, int carts, AuthToken token) throws ResourceNotFoundException, UnauthorizedException;
    public void incrementPlayerCarts(UUID cartId, int carts, AuthToken token) throws ResourceNotFoundException, UnauthorizedException;
}
