package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.games.GameState;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SetGameStateCommand implements IHistoricalCommand, IClientCommand {

    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;
    private GameState gameState;

    public SetGameStateCommand(GameState state, Set<UUID> allPlayers, UUID owner) {
        this.players = allPlayers;
        this.owner = owner;
        this.gameState = state;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().setActiveState(this.gameState); return null;});
    }

    public UUID getId() {
        return id;
    }

    public Set<UUID> playersVisibleTo() {
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }

    public String getDescription() {
        return "started the game";
    }
}
