package teamseth.cs340.common.commands.client;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.IHistoricalCommand;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class HistoryOnlyCommand implements IClientCommand, IHistoricalCommand {

    private UUID id;
    private UUID owner;
    private Set<UUID> players;
    private String description;

    public HistoryOnlyCommand(IHistoricalCommand command) {
        this.id = command.getId();
        this.owner = command.playerOwnedby();
        this.players = command.playersVisibleTo();
        this.description = command.getDescription();
    }

    public HistoryOnlyCommand(UUID id, UUID owner, Set<UUID> players, String description) {
        this.id = id;
        this.owner = owner;
        this.players = players;
        this.description = description;
    }

    public UUID getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Result call() {
        return new Result(() -> null);
    }

    public Set<UUID> playersVisibleTo() {
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }
}
