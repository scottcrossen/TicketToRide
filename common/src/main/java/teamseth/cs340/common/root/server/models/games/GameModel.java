package teamseth.cs340.common.root.server.models.games;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.root.server.models.authentication.AuthToken;
import teamseth.cs340.common.root.server.models.IModel;
import teamseth.cs340.common.util.AuthAction;

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

    Set<Game> games = new TreeSet<Game>();

    public void create(UUID userId, AuthToken token) throws ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        if (playerInGame(userId)) throw new ModelActionException();
        Game game = new Game();
        game.addPlayer(userId);
        games.add(game);
    }

    public void addPlayer(UUID gameId, UUID userId, AuthToken token) throws ModelActionException, ResourceNotFoundException, UnauthorizedException {
        AuthAction.user(token);
        if (playerInGame(userId)) throw new ModelActionException();
        this.get(gameId).addPlayer(userId);
    }

    public void start(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        Game game = this.get(gameId);
        if (!game.hasPlayer(token.userId)) throw new ModelActionException();
        if (game.getState() != GameState.PREGAME) throw new ModelActionException();
        game.setState(GameState.START);
    }

    public Set<Game> getAll() {
        return games;
    }

    public Game get(UUID gameId) throws ResourceNotFoundException {
        Iterator<Game> iterator = games.iterator();
        while (iterator.hasNext()) {
            Game currentGame = iterator.next();
            if (currentGame.id == gameId) {
                return currentGame;
            }
        }
        throw new ResourceNotFoundException();
    }

    private boolean playerInGame(UUID userId) {
        Iterator<Game> iterator = games.iterator();
        while (iterator.hasNext()) {
            Game currentGame = iterator.next();
            if (currentGame.hasPlayer(userId)) {
                return true;
            }
        }
        return false;
    }
}
