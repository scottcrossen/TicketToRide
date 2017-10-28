package teamseth.cs340.common.models.server.boards;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Routes implements Serializable, IModel<Route> {
    private UUID id;
    public UUID getId() {return this.id;}
    private HashSet<Route> routes = new HashSet<>();

    public Routes() {
        routes.add(new Route(CityName.Atlanta, CityName.Raleigh, ResourceColor.RAINBOW, 2));
    }

    public void claimRoute(UUID userId, CityName city1, CityName city2, ResourceColor color) throws ModelActionException {
        List<Route> matchedRoutes = getMatchingRoutes(city1, city2, color);
        if (matchedRoutes.size() != 1) {
            throw new ModelActionException(); // not correctly specified
        } else {
            Route toAdd = matchedRoutes.get(0);
            if (toAdd.getClaimedPlayer().isPresent()) {
                throw new ModelActionException();
            } else {
                routes.remove(matchedRoutes.get(0));
                toAdd.claimBy(userId);
                routes.add(toAdd);
            }
        }

    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2, ResourceColor color) {
        return routes.stream().filter((Route currentRoute) -> currentRoute.equals(city1, city2, color)).collect(Collectors.toList());
    }
}
