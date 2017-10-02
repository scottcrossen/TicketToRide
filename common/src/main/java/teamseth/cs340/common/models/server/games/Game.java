package teamseth.cs340.common.models.server.games;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Game {
    public UUID id = UUID.randomUUID();
    private Set<UUID> users= new TreeSet<UUID>();
    private GameState state = GameState.PREGAME;
    public void addPlayer(UUID userId) throws ModelActionException {
        if (users.size() <= 5) {
            this.users.add(userId);
        } else {
            throw new ModelActionException();
        }
    }
    public boolean hasPlayer(UUID userId) {
        return users.contains(userId);
    }
    public void setState(GameState state) {
        this.state = state;
    }
    public GameState getState() {
        return state;
    }
}
