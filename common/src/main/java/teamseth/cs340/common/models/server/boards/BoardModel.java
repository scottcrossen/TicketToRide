package teamseth.cs340.common.models.server.boards;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.ServerModelRoot;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.models.server.games.Game;
import teamseth.cs340.common.util.auth.AuthAction;
import teamseth.cs340.common.util.auth.AuthToken;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class BoardModel extends AuthAction implements IModel<Routes> {
    private static BoardModel instance;

    public static BoardModel getInstance() {
        if(instance == null) {
            instance = new BoardModel();
        }
        return instance;
    }

    private HashSet<Routes> routes = new HashSet<Routes>();

    public void upsert(Routes newRouteSet, AuthToken token) throws UnauthorizedException, ModelActionException {
        AuthAction.user(token);
        try {
            getRoutes(newRouteSet.getId());
            throw new ModelActionException();
        } catch (ResourceNotFoundException e) {
            routes.add(newRouteSet);
        }
    }

    private Routes getRoutes(UUID id) throws ResourceNotFoundException {
        return routes.stream().filter((Routes routeModel) -> routeModel.getId().equals(id)).findFirst().orElseThrow(() -> new ResourceNotFoundException());
    }

    public int claimRoute(UUID routeSetId, CityName city1, CityName city2, List<ResourceColor> colors, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        Routes routeSet = getRoutes(routeSetId);
        List<Route> matchedRoutes = routeSet.getMatchingRoutes(city1, city2, colors);
        UUID playerId = token.getUser();
        boolean lessThanFourPlayers = ServerModelRoot.getInstance().games.getAll().stream().filter((Game game) -> game.getRoutes().equals(routeSetId)).findFirst().map((Game game) -> game.getPlayers().size() < 4).orElseGet(() -> true);
        boolean routeClaimable = !matchedRoutes.stream().anyMatch((Route route) -> route.getClaimedPlayer().map((UUID claimedPlayer) -> (lessThanFourPlayers && !claimedPlayer.equals(playerId))).orElseGet(() -> true));
        if (matchedRoutes.size() != 1 && (matchedRoutes.size() != 2 || !matchedRoutes.get(0).compareCitiesAndColor(matchedRoutes.get(1))) && routeClaimable) {
            throw new ModelActionException();
        } else {
            Route matchingRoute = matchedRoutes.get(0);
            routeSet.claimRoute(token.getUser(), city1, city2, colors);
            return matchingRoute.getLength();
        }
    }

    public Set<Route> getByPlayer(UUID routeSetId, UUID playerId) throws ResourceNotFoundException {
        return getRoutes(routeSetId).getAllByPlayer(playerId);
    }

}
