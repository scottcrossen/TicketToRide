package teamseth.cs340.common.root.server;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public interface IServer {

    // Game model methods
    public void createGame(AuthToken token) throws ModelActionException, UnauthorizedException;
    public Set<Game> listGames();
    public Set<Game> listGamesAfter(Instant instant);
    public void joinGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;
    public void startGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException;

    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException;
    public AuthToken register(UserCreds creds) throws UnauthorizedException;
}
