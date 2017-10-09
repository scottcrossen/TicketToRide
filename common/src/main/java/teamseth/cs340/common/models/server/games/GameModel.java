package teamseth.cs340.common.models.server.games;

import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
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

    private Set<Game> games = new TreeSet<Game>();

    public void create(AuthToken token) throws ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        UUID userId = token.getUser();
        if (playerInGame(userId)) throw new ModelActionException();
        Game game = new Game();
        game.addPlayer(userId);
        games.add(game);
    }

    public void join(UUID gameId, AuthToken token) throws ModelActionException, ResourceNotFoundException, UnauthorizedException {
        AuthAction.user(token);
        UUID userId = token.getUser();
        if (playerInGame(userId)) throw new ModelActionException();
        this.get(gameId).addPlayer(userId);
    }

    public void start(UUID gameId, AuthToken token) throws ResourceNotFoundException, ModelActionException, UnauthorizedException {
        AuthAction.user(token);
        Game game = this.get(gameId);
        if (!game.hasPlayer(token.getUser())) throw new ModelActionException();
        if (game.getState() != GameState.PREGAME) throw new ModelActionException();
        game.setState(GameState.START);
    }

    public Set<Game> getAll() {
        return games;
    }

    public Game get(UUID gameId) throws ResourceNotFoundException {
        return games.stream().filter(game -> game.getId() == gameId).findFirst().orElseThrow(() -> new ResourceNotFoundException());
    }

    public Set<Game> getAfter(Instant instant) {
        return games.stream().filter(game -> game.getUpdate().compareTo(instant) > 0).collect(Collectors.toSet());
    }

    private boolean playerInGame(UUID userId) {
        return games.stream().anyMatch(game -> game.hasPlayer(userId));
    }
}
