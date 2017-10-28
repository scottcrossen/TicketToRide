package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ReplaceFaceUpCardCommand implements IHistoricalCommand {

    private ResourceColor oldCard;
    private ResourceColor newCard;
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;

    public ReplaceFaceUpCardCommand(ResourceColor oldCard, ResourceColor newCard, Set<UUID> allPlayers, UUID owner) {
        this.oldCard = oldCard;
        this.newCard = newCard;
        this.players = allPlayers;
        this.owner = owner;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().replaceCard(oldCard, newCard); return null;});
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
        return "replaced a face-up card";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
