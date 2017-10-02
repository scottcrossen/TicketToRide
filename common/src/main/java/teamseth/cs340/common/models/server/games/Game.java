package teamseth.cs340.common.models.server.games;

import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Game {
    private UUID id = UUID.randomUUID();
    private Set<UUID> users= new TreeSet<UUID>();
    private GameState state = GameState.PREGAME;
    private Instant lastUpdate = Instant.now();

    private void updateTime() {
        this.lastUpdate = Instant.now();
    }

    public void addPlayer(UUID userId) throws ModelActionException {
        if (users.size() <= 5) {
            this.users.add(userId);
            updateTime();
        } else {
            throw new ModelActionException();
        }
    }

    public boolean hasPlayer(UUID userId) {
        return users.contains(userId);
    }

    public void setState(GameState state) {
        this.state = state;
        updateTime();
    }

    public GameState getState() {
        return state;
    }
    public Instant getUpdate() {
        return this.lastUpdate;
    }
    public UUID getId() {
        return this.id;
    }
}
