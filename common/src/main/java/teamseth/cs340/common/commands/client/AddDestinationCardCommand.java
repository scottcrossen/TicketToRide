package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.commands.IHistoricalCommand;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class AddDestinationCardCommand implements IClientCommand, IHistoricalCommand {

    private DestinationCard card;
    private UUID id = UUID.randomUUID();
    private UUID owner;

    public AddDestinationCardCommand(DestinationCard card, UUID playerId) {
        this.card = card;
        this.owner = playerId;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addDestinationCard(card); return null;});
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return "drew a destination card";
    }

    public Set<UUID> playersVisibleTo() {
        HashSet<UUID> players = new HashSet<>();
        players.add(owner);
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }
}
