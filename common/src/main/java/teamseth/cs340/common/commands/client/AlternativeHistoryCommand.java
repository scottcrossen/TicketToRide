package teamseth.cs340.common.commands.client;

import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AlternativeHistoryCommand implements IClientCommand, IHistoricalCommand {

    protected UUID id;
    protected UUID owner;
    protected Set<UUID> players;
    protected String description;

    public AlternativeHistoryCommand(IHistoricalCommand command) {
        this.id = command.getId();
        this.owner = command.playerOwnedby();
        this.players = command.playersVisibleTo();
        this.description = command.getDescription();
    }

    public AlternativeHistoryCommand(UUID id, UUID owner, Set<UUID> players, String description) {
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

    public IHistoricalCommand getAlternate() {
        return this;
    }
}
