package teamseth.cs340.common.models.client.board;

import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.boards.Routes;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Board extends Observable implements IModel {
    private static Board instance;

    public static Board getInstance() {
        if(instance == null) {
            instance = new Board();
        }
        return instance;
    }

    private Routes routes = new Routes();

    public void resetModel() {
        routes = new Routes();
        setChanged();
        notifyObservers();
    }

    public void claimRouteByPlayer(UUID userId, CityName city1, CityName city2, List<ResourceColor> colors) throws ModelActionException {
        routes.claimRoute(userId, city1, city2, colors);
        setChanged();
        notifyObservers();
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2, ResourceColor color) {
        return routes.getMatchingRoutes(city1, city2, color);
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2) {
        return routes.getMatchingRoutes(city1, city2);
    }

    public Set<Route> getAllClaimedRoutes() {
        return routes.getAllClaimed();
    }

    public Set<Route> getAllRoutes() {
        return routes.getAll();
    }

    public Set<Route> getAllByPlayer(UUID playerId) {
        return routes.getAllByPlayer(playerId);
    }
}
