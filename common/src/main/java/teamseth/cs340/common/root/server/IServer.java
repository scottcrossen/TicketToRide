package teamseth.cs340.common.root.server;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.commands.client.IHistoricalCommand;
import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
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


    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException;
    public AuthToken register(UserCreds creds) throws UnauthorizedException;

    // Card model methods
    public ResourceColor drawResourceCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public DestinationCard drawDestinationCard(UUID deckId, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public void returnResourceCard(UUID deckId, ResourceColor card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;
    public void returnDestinationCard(UUID deckId, DestinationCard card, AuthToken token) throws ResourceNotFoundException, UnauthorizedException, ModelActionException;

    // Chat model methods
    public void sendMessage(UUID room, Message message, AuthToken token) throws UnauthorizedException, ResourceNotFoundException;
    public ArrayList<Message> getMessages(UUID room) throws ResourceNotFoundException;
    public ArrayList<Message> getMessagesAfter(UUID room, int size) throws ResourceNotFoundException;


    // History model methods
    public LinkedList<IHistoricalCommand> getCommandsAfter(UUID historyId, Optional<UUID> afterId, AuthToken token) throws UnauthorizedException, ResourceNotFoundException;
    public void addCommandToHistory(UUID historyId, IHistoricalCommand command, AuthToken token) throws UnauthorizedException, ResourceNotFoundException, ModelActionException;
}
