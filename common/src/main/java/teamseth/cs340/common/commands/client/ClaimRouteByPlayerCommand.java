package teamseth.cs340.common.commands.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.root.client.ClientFacade;
import teamseth.cs340.common.util.Result;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ClaimRouteByPlayerCommand implements IHistoricalCommand {
    private static final long serialVersionUID = 6106588683793521213L;

    private UUID id = UUID.randomUUID();
    private Set<UUID> players = new HashSet<UUID>();
    private UUID owner;
    private CityName city1;
    private CityName city2;
    private ArrayList<ResourceColor> colors;
    private ResourceColor routeColor;


    public ClaimRouteByPlayerCommand(CityName city1, CityName city2, ArrayList<ResourceColor> colors, Optional<ResourceColor> routeColor, Set<UUID> allPlayers, UUID owner) {
        this.players = allPlayers;
        this.owner = owner;
        this.city1 = city1;
        this.city2 = city2;
        this.colors = colors;
        this.routeColor = routeColor.orElseGet(() -> null);
    }

    public Result call() {
        return new Result(() -> {
            ClientFacade.getInstance().claimRouteByPlayer(owner, city1, city2, colors,Optional.ofNullable(routeColor)); return null;});
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
        return "claimed a route";
    }

    public IHistoricalCommand getAlternate() {
        return new AlternativeHistoryCommand(this);
    }
}
