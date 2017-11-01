package teamseth.cs340.common.models.server.boards;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.exceptions.UnauthorizedException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
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

    public int claimRoute(UUID routeSetId, CityName city1, CityName city2, ResourceColor color, int colorCount, AuthToken token) throws ModelActionException, UnauthorizedException, ResourceNotFoundException {
        AuthAction.user(token);
        Routes routeSet = getRoutes(routeSetId);
        List<Route> matchedRoutes = routeSet.getMatchingRoutes(city1, city2, color);
        if (matchedRoutes.size() != 1 && (matchedRoutes.size() != 2 || !matchedRoutes.get(0).compareCitiesAndColor(matchedRoutes.get(1)))) {
            throw new ModelActionException();
        } else {
            Route matchingRoute = matchedRoutes.get(0);
            if (matchingRoute.getLength() > colorCount) {
                throw new ModelActionException();
            } else {
                routeSet.claimRoute(token.getUser(), city1, city2, color);
                return matchingRoute.getLength();
            }
        }
    }

}
