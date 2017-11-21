package teamseth.cs340.common.models.server.boards;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        UUID playerId = token.getUser();
        Routes routeSet = getRoutes(routeSetId);
        Optional<Game> gameOption = ServerModelRoot.getInstance().games.getAll().stream().filter((Game game) -> game.getRoutes().equals(routeSetId)).findFirst();
        List<Route> matchedRoutes = routeSet.getMatchingRoutes(city1, city2, colors);
        List<Route> neighborRoutes = routeSet.getMatchingRoutes(city1, city2);

        // Begin precondition check.
        boolean lessThanFourPlayers = gameOption.map((Game game) -> game.getPlayers().size() < 4).orElseGet(() -> true);
        boolean nonClaimedRouteExists = matchedRoutes.stream().noneMatch((Route currentRoute) -> currentRoute.getClaimedPlayer().isPresent());
        boolean doubleRouteRestrictionObserved = !lessThanFourPlayers ||
                neighborRoutes.size() == 1 ||
                neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().isPresent());
        boolean playerHasEnoughCarts = gameOption.map((Game game) -> {
            try {
                return ServerModelRoot.carts.getPlayerCarts(game.getCarts(), token) >= colors.size();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }).orElseGet(() -> false);
        boolean playerDoesntHaveBothRoutes = neighborRoutes.stream().noneMatch((Route boardRoute) -> boardRoute.getClaimedPlayer().map((UUID claimedPlayer) -> claimedPlayer.equals(playerId)).orElseGet(() -> false));
        boolean onlyOnePossibleSelection = matchedRoutes.size() == 1 || (matchedRoutes.size() == 2 && matchedRoutes.get(0).compareCitiesAndColor(matchedRoutes.get(1)));
        // End of precondition check

        if (nonClaimedRouteExists && doubleRouteRestrictionObserved && playerHasEnoughCarts && playerDoesntHaveBothRoutes && onlyOnePossibleSelection) {
            Route matchingRoute = matchedRoutes.get(0);
            routeSet.claimRoute(playerId, city1, city2, colors);
            return matchingRoute.getLength();
        } else {
            throw new ModelActionException();
        }
    }

    public Set<Route> getByPlayer(UUID routeSetId, UUID playerId) throws ResourceNotFoundException {
        return getRoutes(routeSetId).getAllByPlayer(playerId);
    }

}
