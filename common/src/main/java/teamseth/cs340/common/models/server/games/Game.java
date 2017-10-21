package teamseth.cs340.common.models.server.games;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.models.server.users.User;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Game implements Serializable, Comparable<Game> {
    private static final long serialVersionUID = 2374397675854997368L;
    private UUID id = UUID.randomUUID();
    private HashSet<UUID> users= new HashSet<UUID>();
    private GameState state = GameState.PREGAME;
    private Instant lastUpdate = Instant.now();
    private HashMap<UUID, String> playerNames = new HashMap<>();
    private UUID chatRoom;
    private UUID destinationDeck;
    private UUID resourceDeck;
    private UUID history;

    public void addPlayer(User user) throws ModelActionException {
        if (users.size() <= 5) {
            this.users.add(user.getId());
            this.playerNames.put(user.getId(), user.getUserCreds().getUsername());
            updateTime();
        } else {
            throw new ModelActionException();
        }
    }

    public void removePlayer(UUID userId) {
        users.remove(userId);
        playerNames.remove(userId);
        updateTime();
    }

    private void updateTime() { this.lastUpdate = Instant.now(); }

    public boolean hasPlayer(UUID userId) { return users.contains(userId); }

    public void setState(GameState state) {
        this.state = state;
        updateTime();
    }
    public void setChatRoom(UUID id) {
        this.chatRoom = id;
        updateTime();
    }
    public void setDestinationDeck(UUID id) {
        this.destinationDeck = id;
        updateTime();
    }
    public void setResourceDeck(UUID id){
        this.resourceDeck = id;
        updateTime();
    }
    public void setHistory(UUID id){
        this.history = id;
        updateTime();
    }

    public GameState getState() {
        return state;
    }
    public Instant getUpdate() { return this.lastUpdate; }
    public UUID getId() {
        return this.id;
    }
    public String name() { return "Game " + hashCode(); }
    public HashMap<UUID, String> getPlayerNames() { return playerNames; }
    public HashSet<UUID> getPlayers() { return users; }
    public UUID getChatRoom() { return chatRoom; }
    public UUID getDestinationDeck() { return destinationDeck; }
    public UUID getResourceDeck() { return resourceDeck; }
    public UUID getHistory() { return history; }
    @Override
    public int compareTo(Game game) {
        return this.id.compareTo(game.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (id != null ? !id.equals(game.id) : game.id != null) return false;
        if (users != null ? !users.equals(game.users) : game.users != null) return false;
        if (state != game.state) return false;
        return lastUpdate != null ? lastUpdate.equals(game.lastUpdate) : game.lastUpdate == null;

    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
