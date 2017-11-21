package teamseth.cs340.common.commands.client;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class InitialChooseDestinationCardCommand implements IHistoricalCommand {
    private static final long serialVersionUID = -1820875388164428541L;

    private DestinationCard card;
    private UUID id = UUID.randomUUID();
    private UUID owner;

    public InitialChooseDestinationCardCommand(Optional<DestinationCard> card, UUID playerId) {
        this.card = card.orElseGet(() -> null);
        this.owner = playerId;
    }

    public Result call() {
        return new Result(() -> {
            if (card != null) {
                ClientFacade.getInstance().removeDestinationCard(card);
            }
            return null;
        });
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        if (card != null) {
            return "returned a destination card";
        } else {
            return "did not return a destination card";
        }
    }

    public Set<UUID> playersVisibleTo() {
        HashSet<UUID> players = new HashSet<>();
        players.add(owner);
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }

    public Optional<DestinationCard> getDestinationCard() { return Optional.ofNullable(card); }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
