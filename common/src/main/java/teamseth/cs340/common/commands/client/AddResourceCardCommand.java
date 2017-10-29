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
public class AddResourceCardCommand implements IClientCommand, IHistoricalCommand {
    private static final long serialVersionUID = -5448126465480545123L;

    private ResourceColor card;
    private UUID id = UUID.randomUUID();
    private UUID owner;

    public AddResourceCardCommand(ResourceColor card, UUID owner) {
        this.card = card;
        this.owner = owner;
    }

    public Result call() {
        return new Result(() -> {ClientFacade.getInstance().addResourceCard(card); return null;});
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return "drew a resource card";
    }

    public Set<UUID> playersVisibleTo() {
        HashSet<UUID> players = new HashSet<>();
        players.add(owner);
        return players;
    }

    public UUID playerOwnedby() {
        return owner;
    }

    public IHistoricalCommand getAlternate() { return new IncrementPlayerResourceCardsCommand(this);}
}
