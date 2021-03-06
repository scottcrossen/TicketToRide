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
public class ChangeTurnCommand implements IHistoricalCommand, ILogoutOkayCommand {
    private static final long serialVersionUID = 2595495252972858369L;

    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;
    private UUID playerTurn;

    public ChangeTurnCommand(Set<UUID> allPlayers, UUID owner, UUID playerTurn) {
        this.players = allPlayers;
        this.owner = owner;
        this.playerTurn = playerTurn;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().setTurn(playerTurn); return null;});
    }

    public UUID getPlayerTurn() {
        return playerTurn;
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
        return "changed the turn";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }

}
