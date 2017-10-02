package teamseth.cs340.common.root.server;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.util.auth.AuthToken;
import teamseth.cs340.common.models.server.users.UserCreds;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.models.server.ServerModelRoot;

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
    public void createGame(UUID userId, AuthToken token) throws ModelActionException, UnauthorizedException {
        model.games.create(userId, token);
    }
    public Set<Game> listGames() {
        return model.games.getAll();
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
