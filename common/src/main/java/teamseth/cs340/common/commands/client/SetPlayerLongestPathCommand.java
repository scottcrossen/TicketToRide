package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SetPlayerLongestPathCommand implements IHistoricalCommand {
    private static final long serialVersionUID = -5036063188550227095L;
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;

    public SetPlayerLongestPathCommand(Set<UUID> allPlayers, UUID owner) {
        this.players = allPlayers;
        this.owner = owner;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().setPlayerLongestPath(owner); return null;});
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
        return "claimed the longest path";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
