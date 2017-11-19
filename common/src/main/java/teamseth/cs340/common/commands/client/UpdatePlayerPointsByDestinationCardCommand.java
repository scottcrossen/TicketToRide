package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class UpdatePlayerPointsByDestinationCardCommand implements IHistoricalCommand {
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private DestinationCard card;
    private UUID owner;

    public UpdatePlayerPointsByDestinationCardCommand(DestinationCard card, Set<UUID> allPlayers, UUID owner) {
        this.players = allPlayers;
        this.owner = owner;
        this.card = card;
    }

    public Result call() {
        return new Result(() -> { ClientFacade.getInstance().updatePlayerPointsByDestinationCard(owner, card); return null; });
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
        return "gained points from a destination card";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
