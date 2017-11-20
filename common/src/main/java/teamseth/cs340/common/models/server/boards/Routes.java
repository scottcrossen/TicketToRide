package teamseth.cs340.common.models.server.boards;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private UUID id = UUID.randomUUID();
    public UUID getId() {return this.id;}
    private HashSet<Route> routes = new HashSet<>();

    public Routes() {
      routes.add(new Route(CityName.Atlanta, CityName.Raleigh, ResourceColor.RAINBOW, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.Atlanta, CityName.Raleigh, ResourceColor.RAINBOW, 2,10,10,10,10));
      routes.add(new Route(CityName.Atlanta, CityName.Charleston, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Atlanta, CityName.Miami, ResourceColor.BLUE, 5,0,0,0,0));
      routes.add(new Route(CityName.Atlanta, CityName.NewOrleans, ResourceColor.YELLOW, 4,-5,-5,-5,-5));
      routes.add(new Route(CityName.Atlanta, CityName.NewOrleans, ResourceColor.ORANGE, 4,10,10,10,10));
      routes.add(new Route(CityName.Atlanta, CityName.Nashville, ResourceColor.RAINBOW, 1,0,0,0,0));
      routes.add(new Route(CityName.Boston, CityName.Montreal, ResourceColor.RAINBOW, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.Boston, CityName.Montreal, ResourceColor.RAINBOW, 2,25,25,5,5));
      routes.add(new Route(CityName.Boston, CityName.NewYork, ResourceColor.YELLOW, 2,10,10,10,10));
      routes.add(new Route(CityName.Boston, CityName.NewYork, ResourceColor.RED, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.Calgary, CityName.Vancouver, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.Calgary, CityName.Winnipeg, ResourceColor.WHITE, 6,0,0,0,0));
      routes.add(new Route(CityName.Calgary, CityName.Helena, ResourceColor.RAINBOW, 4,0,0,0,0));
      routes.add(new Route(CityName.Calgary, CityName.Seattle, ResourceColor.RAINBOW, 4,0,0,0,0));
      routes.add(new Route(CityName.Charleston, CityName.Raleigh, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Charleston, CityName.Atlanta, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Charleston, CityName.Miami, ResourceColor.PURPLE, 4,0,0,0,0));
      routes.add(new Route(CityName.Chicago, CityName.Pittsburgh, ResourceColor.ORANGE, 3,0,0,10,10));
      routes.add(new Route(CityName.Chicago, CityName.Pittsburgh, ResourceColor.BLACK, 3,0,0,-5,-5));
      routes.add(new Route(CityName.Chicago, CityName.Toronto, ResourceColor.WHITE, 4,0,0,0,0));
      routes.add(new Route(CityName.Chicago, CityName.Duluth, ResourceColor.RED, 3,0,0,0,0));
      routes.add(new Route(CityName.Chicago, CityName.Omaha, ResourceColor.BLUE, 4,0,0,0,0));
      routes.add(new Route(CityName.Chicago, CityName.SaintLouis, ResourceColor.GREEN, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.Chicago, CityName.SaintLouis, ResourceColor.WHITE, 2,10,10,10,10));
      routes.add(new Route(CityName.Dallas, CityName.LittleRock, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Dallas, CityName.OklahomaCity, ResourceColor.RAINBOW, 2,15,15,0,0));
      routes.add(new Route(CityName.Dallas, CityName.OklahomaCity, ResourceColor.RAINBOW, 2,-10,-10,0,0));
      routes.add(new Route(CityName.Dallas, CityName.ElPaso, ResourceColor.RED, 4,0,0,0,0));
      routes.add(new Route(CityName.Dallas, CityName.Houston, ResourceColor.RAINBOW, 1,15,15,0,0));
      routes.add(new Route(CityName.Dallas, CityName.Houston, ResourceColor.RAINBOW, 1,-10,-10,0,0));
      routes.add(new Route(CityName.Denver, CityName.KansasCity, ResourceColor.BLACK, 4,0,0,-10,-10));
      routes.add(new Route(CityName.Denver, CityName.KansasCity, ResourceColor.ORANGE, 4,0,0,5,5));
      routes.add(new Route(CityName.Denver, CityName.Omaha, ResourceColor.PURPLE, 4,0,0,0,0));
      routes.add(new Route(CityName.Denver, CityName.Helena, ResourceColor.GREEN, 4,0,0,0,0));
      routes.add(new Route(CityName.Denver, CityName.SaltLakeCity, ResourceColor.RED, 3,0,0,-10,-10));
      routes.add(new Route(CityName.Denver, CityName.SaltLakeCity, ResourceColor.YELLOW, 3,0,0,15,15));
      routes.add(new Route(CityName.Denver, CityName.Phoenix, ResourceColor.WHITE, 5,0,0,0,0));
      routes.add(new Route(CityName.Denver, CityName.SantaFe, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Denver, CityName.OklahomaCity, ResourceColor.RED, 4,0,0,0,0));
      routes.add(new Route(CityName.Duluth, CityName.Omaha, ResourceColor.RAINBOW, 2,15,15,0,0));
      routes.add(new Route(CityName.Duluth, CityName.Omaha, ResourceColor.RAINBOW, 2,-10,-10,0,0));
      routes.add(new Route(CityName.Duluth, CityName.Toronto, ResourceColor.PURPLE, 6,0,0,0,0));
      routes.add(new Route(CityName.Duluth, CityName.StMarie, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.Duluth, CityName.Winnipeg, ResourceColor.BLACK, 4,0,0,0,0));
      routes.add(new Route(CityName.Duluth, CityName.Helena, ResourceColor.ORANGE, 6,0,0,0,0));
      routes.add(new Route(CityName.ElPaso, CityName.Houston, ResourceColor.GREEN, 6,0,0,0,0));
      routes.add(new Route(CityName.ElPaso, CityName.OklahomaCity, ResourceColor.YELLOW, 5,0,0,0,0));
      routes.add(new Route(CityName.ElPaso, CityName.SantaFe, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.ElPaso, CityName.Phoenix, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.ElPaso, CityName.LosAngeles, ResourceColor.BLACK, 6,0,0,0,0));
      routes.add(new Route(CityName.Helena, CityName.Winnipeg, ResourceColor.BLUE, 4,0,0,0,0));
      routes.add(new Route(CityName.Helena, CityName.Omaha, ResourceColor.RED, 5,0,0,0,0));
      routes.add(new Route(CityName.Helena, CityName.SaltLakeCity, ResourceColor.PURPLE, 3,0,0,0,0));
      routes.add(new Route(CityName.Helena, CityName.Seattle, ResourceColor.YELLOW, 6,0,0,0,0));
      routes.add(new Route(CityName.Houston, CityName.NewOrleans, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.KansasCity, CityName.SaintLouis, ResourceColor.BLUE, 2,0,0,5,5));
      routes.add(new Route(CityName.KansasCity, CityName.SaintLouis, ResourceColor.PURPLE, 2,0,0,-10,-10));
      routes.add(new Route(CityName.KansasCity, CityName.Omaha, ResourceColor.RAINBOW, 1,15,15,0,0));
      routes.add(new Route(CityName.KansasCity, CityName.Omaha, ResourceColor.RAINBOW, 1,-10,-10,0,0));
      routes.add(new Route(CityName.KansasCity, CityName.OklahomaCity, ResourceColor.RAINBOW, 2,15,15,0,0));
      routes.add(new Route(CityName.KansasCity, CityName.OklahomaCity, ResourceColor.RAINBOW, 2,-10,-10,0,0));
      routes.add(new Route(CityName.LasVegas, CityName.SaltLakeCity, ResourceColor.ORANGE, 3,0,0,0,0));
      routes.add(new Route(CityName.LasVegas, CityName.LosAngeles, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.LittleRock, CityName.Nashville, ResourceColor.WHITE, 3,0,0,0,0));
      routes.add(new Route(CityName.LittleRock, CityName.SaintLouis, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.LittleRock, CityName.OklahomaCity, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.LittleRock, CityName.NewOrleans, ResourceColor.GREEN, 3,0,0,0,0));
      routes.add(new Route(CityName.LosAngeles, CityName.SanFrancisco, ResourceColor.PURPLE, 3,10,10,5,5));
      routes.add(new Route(CityName.LosAngeles, CityName.SanFrancisco, ResourceColor.YELLOW, 3,-10,-10,0,0));
      routes.add(new Route(CityName.LosAngeles, CityName.Phoenix, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.Miami, CityName.NewOrleans, ResourceColor.RED, 6,0,0,0,0));
      routes.add(new Route(CityName.Montreal, CityName.NewYork, ResourceColor.BLUE, 3,0,0,0,0));
      routes.add(new Route(CityName.Montreal, CityName.Toronto, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.Montreal, CityName.StMarie, ResourceColor.BLACK, 5,0,0,0,0));
      routes.add(new Route(CityName.Nashville, CityName.Raleigh, ResourceColor.BLACK, 3,0,0,0,0));
      routes.add(new Route(CityName.Nashville, CityName.Pittsburgh, ResourceColor.YELLOW, 4,0,0,0,0));
      routes.add(new Route(CityName.Nashville, CityName.SaintLouis, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.NewYork, CityName.DC, ResourceColor.BLACK, 2,-10,-10,0,0));
      routes.add(new Route(CityName.NewYork, CityName.DC, ResourceColor.ORANGE, 2,5,5,0,0));
      routes.add(new Route(CityName.NewYork, CityName.Pittsburgh, ResourceColor.WHITE, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.NewYork, CityName.Pittsburgh, ResourceColor.GREEN, 2,10,10,10,10));
      routes.add(new Route(CityName.OklahomaCity, CityName.SantaFe, ResourceColor.BLUE, 3,0,0,0,0));
      routes.add(new Route(CityName.Phoenix, CityName.SantaFe, ResourceColor.RAINBOW, 3,0,0,0,0));
      routes.add(new Route(CityName.Pittsburgh, CityName.DC, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Pittsburgh, CityName.Raleigh, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Pittsburgh, CityName.SaintLouis, ResourceColor.GREEN, 5,0,0,0,0));
      routes.add(new Route(CityName.Pittsburgh, CityName.Toronto, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Portland, CityName.Seattle, ResourceColor.RAINBOW, 1,-20,-20,0,0));
      routes.add(new Route(CityName.Portland, CityName.Seattle, ResourceColor.RAINBOW, 1,5,5,0,0));
      routes.add(new Route(CityName.Portland, CityName.SaltLakeCity, ResourceColor.BLUE, 6,0,0,0,0));
      routes.add(new Route(CityName.Portland, CityName.SanFrancisco, ResourceColor.GREEN, 5,5,5,0,0));
      routes.add(new Route(CityName.Portland, CityName.SanFrancisco, ResourceColor.PURPLE, 5,-20,-20,0,0));
      routes.add(new Route(CityName.Raleigh, CityName.DC, ResourceColor.RAINBOW, 2,-5,-5,-5,-5));
      routes.add(new Route(CityName.Raleigh, CityName.DC, ResourceColor.RAINBOW, 2,10,10,10,10));
      routes.add(new Route(CityName.SaltLakeCity, CityName.SanFrancisco, ResourceColor.ORANGE, 5,-5,-5,-10,-10));
      routes.add(new Route(CityName.SaltLakeCity, CityName.SanFrancisco, ResourceColor.WHITE, 5,0,0,5,5));
      routes.add(new Route(CityName.StMarie, CityName.Winnipeg, ResourceColor.RAINBOW, 6,0,0,0,0));
      routes.add(new Route(CityName.StMarie, CityName.Toronto, ResourceColor.RAINBOW, 2,0,0,0,0));
      routes.add(new Route(CityName.Seattle, CityName.Vancouver, ResourceColor.RAINBOW, 1,5,5,0,0));
      routes.add(new Route(CityName.Seattle, CityName.Vancouver, ResourceColor.RAINBOW, 1,-20,-20,0,0));
    }

    public void claimRoute(UUID userId, CityName city1, CityName city2, List<ResourceColor> colors) throws ModelActionException {
        List<Route> matchedRoutes = getMatchingRoutes(city1, city2, colors);
        if (matchedRoutes.size() != 1 && (matchedRoutes.size() != 2 || !matchedRoutes.get(0).compareCitiesAndColor(matchedRoutes.get(1)))) {
            throw new ModelActionException(); // not correctly specified
        } else {
            Route toAdd;
            if (!matchedRoutes.get(0).getClaimedPlayer().isPresent()) {
                toAdd = matchedRoutes.get(0);
            } else if (matchedRoutes.size() > 1 && !matchedRoutes.get(1).getClaimedPlayer().isPresent()) {
                toAdd = matchedRoutes.get(1);
            } else {
                throw new ModelActionException();
            }
            Route toRemove = toAdd;
            routes.remove(toRemove);
            toAdd.claimBy(userId);
            toAdd.setOwned(true);
            routes.add(toAdd);
        }
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2, List<ResourceColor> colors) {
        return routes.stream().filter((Route currentRoute) -> currentRoute.equals(city1, city2, colors)).collect(Collectors.toList());
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2, ResourceColor color) {
        return routes.stream().filter((Route currentRoute) -> currentRoute.equals(city1, city2, color)).collect(Collectors.toList());
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2) {
        return routes.stream().filter((Route currentRoute) -> currentRoute.equals(city1, city2, ResourceColor.RAINBOW)).collect(Collectors.toList());
    }

    public Set<Route> getAll() {return routes;}

    public Set<Route> getAllClaimed() {
        return routes.stream().filter((Route current) -> current.getClaimedPlayer().isPresent()).collect(Collectors.toSet());
    }

    public Set<Route> getAllByPlayer(UUID playerId) {
        return routes.stream().filter((Route current) -> current.getClaimedPlayer().map((UUID claimedPlayer) -> claimedPlayer.equals(playerId)).orElseGet(() -> false)).collect(Collectors.toSet());
    }
}
