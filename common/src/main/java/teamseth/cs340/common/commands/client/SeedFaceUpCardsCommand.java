package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class SeedFaceUpCardsCommand implements IHistoricalCommand {
    private static final long serialVersionUID = -2226174445616083465L;

    private List<ResourceColor> cards;
    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;

    public SeedFaceUpCardsCommand(List<ResourceColor> cards, Set<UUID> allPlayers, UUID owner) {
        this.cards = cards;
        this.players = allPlayers;
        this.owner = owner;
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().seedCards(cards); return null;});
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
        return "set the face-up cards";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
