package teamseth.cs340.common.root.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.authentication.AuthToken;
import teamseth.cs340.common.models.server.authentication.UserCreds;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.exceptions.*;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IServer {

    // Game model methods
    public void createGame(UUID userId, AuthToken token) throws ModelActionException, UnauthorizedException;
    public Set<Game> listGames();
    public void joinGame(UUID gameId, UUID userId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;
    public void startGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;

    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException;
    public AuthToken register(UserCreds creds) throws UnauthorizedException;
}
