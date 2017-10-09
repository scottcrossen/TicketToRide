package teamseth.cs340.common.root.server;

import java.time.Instant;
import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.server.ServerModelRoot;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public final class ServerFacade implements IServer {
    private static ServerFacade instance;
    public static  ServerFacade getInstance() {
        if (instance == null) {
            instance = new ServerFacade();
        }
        return instance;
    }
    private ServerModelRoot model = ServerModelRoot.getInstance();

    // Game model methods
    public Game createGame(AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        return model.games.create(token);
    }
    public HashSet<Game> listGames() {
        return model.games.getAll();
    }
    public HashSet<Game> listGamesAfter(Instant instant) {
        return model.games.getAfter(instant);
    }
    public void joinGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        model.games.join(gameId, token);
    }
    public void startGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        model.games.start(gameId, token);
    }
    public Game getGame(UUID gameId) throws ResourceNotFoundException {return model.games.get(gameId);}
    public void leaveGame(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {model.games.leave(gameId, token);}

    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException {
        return model.users.login(creds);
    }
    public AuthToken register(UserCreds creds) throws UnauthorizedException {
        return model.users.register(creds);
    }

}
