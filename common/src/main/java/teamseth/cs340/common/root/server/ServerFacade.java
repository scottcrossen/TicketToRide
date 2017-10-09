package teamseth.cs340.common.root.server;

import java.time.Instant;
import java.util.Set;
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
    public void createGame(AuthToken token) throws ModelActionException, UnauthorizedException {
        model.games.create(token);
    }
    public Set<Game> listGames() {
        return model.games.getAll();
    }
    public Set<Game> listGamesAfter(Instant instant) {
        return model.games.getAfter(instant);
    }
    public void joinGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        model.games.join(gameId, token);
    }
    public void startGame(UUID gameId, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        model.games.start(gameId, token);
    }

    // User model methods
    public AuthToken login(UserCreds creds) throws ResourceNotFoundException, UnauthorizedException {
        return model.users.login(creds);
    }
    public AuthToken register(UserCreds creds) throws UnauthorizedException {
        return model.users.register(creds);
    }

}
