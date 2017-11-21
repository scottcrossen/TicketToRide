package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.CityName;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class RouteCalculator {
    public static final int calculateLongestPath(Set<Route> routes){
        Set<Route> visited = new HashSet<>();
        Set<Integer> solutions = routes.stream().flatMap((Route route) -> recurseOnRoute(routes, route, visited).stream()).map((Tuple<Integer, Set<Route>> solution) -> solution.get1()).collect(Collectors.toSet());
        Integer max = solutions.stream().max((Integer solution1, Integer solution2) -> Integer.compare(solution1, solution2)).orElseGet(() -> 0);
        return max;
    }

    private static final Set<Tuple<Integer, Set<Route>>> recurseOnRoute(Set<Route> routes, Route route, Set<Route> visited) {
        Set<Route> neighborRoutes = routes.stream().filter((Route currentRoute) -> (currentRoute.hasCity(route.getCity1()) || currentRoute.hasCity(route.getCity2()))).collect(Collectors.toSet());
        Set<Route> currentVisited = Stream.concat(visited.stream(), Stream.of(route)).collect(Collectors.toSet());
        Stream<Tuple<Integer, Set<Route>>> output = neighborRoutes.stream().filter((Route currentRoute) -> {
            boolean hasBeenVisited = currentVisited.stream().noneMatch((visitedRoute) -> visitedRoute.compareCitiesAndColor(currentRoute));
            return hasBeenVisited;
        }).flatMap((Route currentRoute) -> {
            Stream<Tuple<Integer, Set<Route>>> result = recurseOnRoute(routes, currentRoute, currentVisited).stream();
            return result.map((Tuple<Integer, Set<Route>> singleResult) -> new Tuple<>(singleResult.get1() + route.getLength(), singleResult.get2()));
        });
        return Stream.concat(output, Stream.of(new Tuple<>(route.getLength(), visited))).collect(Collectors.toSet());
    }

    // You can probably get away without returning the set of visited routes but in case we need it:
    private static class Tuple<A, B> implements Serializable {
        private A object1;
        private B object2;
        public Tuple(A object1, B object2) {
            this.object1 = object1;
            this.object2 = object2;
        }
        public A get1() { return object1; }
        public B get2() { return object2; }
    }

    public static boolean citiesConnected(CityName city1, CityName city2, Set<Route> routes) {
        return getCityClusters(routes).stream().anyMatch((Set<CityName> cluster) -> {
            boolean clusterContainsCity1 = cluster.stream().anyMatch((CityName city) -> city.equals(city1));
            boolean clusterContainsCity2 = cluster.stream().anyMatch((CityName city) -> city.equals(city2));
            return (clusterContainsCity1 && clusterContainsCity2);
        });
    }

    private static Set<Set<CityName>> getCityClusters(Set<Route> routes) {
        Set<Set<CityName>> collectionAddTo = new HashSet<>();
        Set<Set<CityName>> collectionToAdd = routes.stream().map((Route route) -> {
            Set<CityName> singleCluster = new HashSet<CityName>();
            singleCluster.add(route.getCity1());
            singleCluster.add(route.getCity2());
            return singleCluster;
        }).collect(Collectors.toSet());
        //Stream<Stream<CityName>> output = combineCollections(collectionToAdd, collectionAddTo);
        // Why is there no fold function? This is stupid.
        for (Set<CityName> cluster : collectionToAdd) {
            collectionAddTo = addClusterToCollection(collectionAddTo, cluster);
        }
        return collectionAddTo;
    }

    private static Set<Set<CityName>> addClusterToCollection(Set<Set<CityName>> clusters, Set<CityName> clusterToAdd) {
        Set<Set<CityName>> clustersMatchCities = Stream.concat(clusters.stream().filter((Set<CityName> cities) -> {
            boolean cityInCluster = cities.stream().anyMatch((CityName city) -> clusterToAdd.stream().anyMatch((CityName routeCity) -> routeCity.equals(city)));
            return cityInCluster;
        }), Stream.of(clusterToAdd)).collect(Collectors.toSet());
        Set<CityName> combinedCluster = clustersMatchCities.stream().flatMap((Set<CityName> cluster) -> cluster.stream()).distinct().collect(Collectors.toSet());
        Set<Set<CityName>> clustersDontMatch = clusters.stream().filter((Set<CityName> cities) -> {
            boolean cityNotInCluster = cities.stream().anyMatch((CityName city) -> clusterToAdd.stream().noneMatch((CityName routeCity) -> routeCity.equals(city)));
            return cityNotInCluster;
        }).collect(Collectors.toSet());
        return Stream.concat(Stream.of(combinedCluster), clustersDontMatch.stream()).collect(Collectors.toSet());
    }

    private static Set<Set<CityName>> combineCollections(Set<Set<CityName>> collectionToAdd, Set<Set<CityName>> collectionAddTo) {
        // This might not work because of it's weird recursive nature.
        Set<Set<CityName>> output = collectionToAdd.stream().reduce(collectionAddTo, (Set<Set<CityName>> clusters, Set<CityName> cluster) -> addClusterToCollection(clusters, cluster),
                (Set<Set<CityName>> clusters1, Set<Set<CityName>> clusters2) -> combineCollections(clusters1, clusters2));
        return output;
    }
}
