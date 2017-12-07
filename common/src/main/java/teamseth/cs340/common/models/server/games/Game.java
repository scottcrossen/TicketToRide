package teamseth.cs340.common.models.server.games;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.models.server.users.User;
import teamseth.cs340.common.persistence.IStorable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Game implements Serializable, Comparable<Game>, IStorable {
    private static final long serialVersionUID = 2374397675854997368L;
    private UUID id = UUID.randomUUID();
    private HashSet<UUID> users= new HashSet<UUID>();
    private GameState state = GameState.PREGAME;
    private Instant lastUpdate = Instant.now();
    private HashMap<UUID, String> playerNames = new HashMap<>();
    private HashMap<UUID, PlayerColor> playerColors = new HashMap<>();
    private List<UUID> playerTurns = new LinkedList<UUID>();
    private UUID chatRoom;
    private UUID destinationDeck;
    private UUID resourceDeck;
    private UUID history;
    private UUID routes;
    private UUID carts;
    private int turn = 0;

    public void addPlayer(User user) throws ModelActionException {
        if (users.size() <= 5) {
            this.users.add(user.getId());
            this.playerNames.put(user.getId(), user.getUserCreds().getUsername());
            this.playerColors.put(user.getId(), PlayerColor.values()[playerTurns.size()]);
            this.playerTurns.add(user.getId());
            updateTime();
        } else {
            throw new ModelActionException();
        }
    }

    public void removePlayer(UUID userId) {
        users.remove(userId);
        // Keep player name for reference if messages exist.
        playerColors.remove(userId);
        playerTurns.remove(userId);
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
    public void setRoutes(UUID id){
        this.routes = id;
        updateTime();
    }
    public void setCarts(UUID id){
        this.carts = id;
        updateTime();
    }
    public void setTurn(UUID playerId) {
        this.turn = playerTurns.indexOf(playerId);
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
    public UUID getRoutes() { return routes; }
    public UUID getCarts() { return carts; }
    public Map<UUID, PlayerColor> getPlayerColors() { return playerColors; }

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

    public void nextTurn() {
        this.turn = (this.turn + 1) % this.getPlayers().size();
    };

    public Optional<UUID> getWhosTurnItIs() {
        if (getState().equals(GameState.PLAYING)) {
            return Optional.of(playerTurns.get(this.turn));
        } else {
            return Optional.empty();
        }
    }

    public Optional<UUID> getWhosTurnItIsNext() {
        if (getState().equals(GameState.PLAYING)) {
            return Optional.of(playerTurns.get((turn + 1) % playerTurns.size()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<UUID> getWhosTurnItIsNextById(UUID playerId) {
        if (getState().equals(GameState.PLAYING)) {
            return Optional.of(playerTurns.get((playerTurns.indexOf(playerId) + 1) % playerTurns.size()));
        } else {
            return Optional.empty();
        }
    }

    public synchronized void startGameHasLock() throws ModelActionException {
        if (!getState().equals(GameState.PREGAME)) throw new ModelActionException();
        setState(GameState.START);
    }

    public synchronized void playGameHasLock() throws ModelActionException {
        if (!getState().equals(GameState.START)) throw new ModelActionException();
        setState(GameState.PLAYING);
    }
}