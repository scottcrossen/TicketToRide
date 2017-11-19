package teamseth.cs340.common.util;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Stream;

import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.CityName;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class RouteCalculator {
    public static final int calculateLongestPath(Set<Route> routeSet){
        Stream<Route> visited = Stream.empty();
        Stream<Route> routes = routeSet.stream();
        Stream<Integer> solutions = routes.flatMap((Route route) -> recurseOnRoute(routes, route, visited)).map((Tuple<Integer, Stream<Route>> solution) -> solution.get1());
        Integer max = solutions.max((Integer solution1, Integer solution2) -> Integer.compare(solution1, solution2)).orElseGet(() -> -1);
        return max;
    }

    private static final Stream<Tuple<Integer, Stream<Route>>> recurseOnRoute(Stream<Route> routes, Route route, Stream<Route> visited) {
        Stream<Route> neighborRoutes = routes.filter((Route currentRoute) -> (currentRoute.hasCity(route.getCity1()) || currentRoute.hasCity(route.getCity2())));
        Stream<Route> currentVisited = Stream.concat(visited, Stream.of(route));
        Stream<Tuple<Integer, Stream<Route>>> output = neighborRoutes.filter((Route currentRoute) -> {
            boolean hasBeenVisited = currentVisited.noneMatch((visitedRoute) -> visitedRoute.compareCitiesAndColor(currentRoute));
            return hasBeenVisited;
        }).flatMap((Route currentRoute) -> {
            Stream<Tuple<Integer, Stream<Route>>> result = recurseOnRoute(routes, currentRoute, currentVisited);
            return result.map((Tuple<Integer, Stream<Route>> singleResult) -> new Tuple<>(singleResult.get1() + route.getLength(), singleResult.get2()));
        });
        return Stream.concat(output, Stream.of(new Tuple<>(route.getLength(), visited)));
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

    public static boolean citiesConnected(CityName city1, CityName city2, Set<Route> routeSet) {
        Stream<Route> routes = routeSet.stream();
        return getCityClusters(routes).anyMatch((Stream<CityName> cluster) -> {
            boolean clusterContainsCity1 = cluster.anyMatch((CityName city) -> city.equals(city1));
            boolean clusterContainsCity2 = cluster.anyMatch((CityName city) -> city.equals(city2));
            return (clusterContainsCity1 && clusterContainsCity2);
        });
    }

    private static Stream<Stream<CityName>> getCityClusters(Stream<Route> routes) {
        Stream<Stream<CityName>> collectionAddTo = Stream.empty();
        Stream<Stream<CityName>> collectionToAdd = routes.map((Route route) -> Stream.concat(Stream.of(route.getCity1()), Stream.of(route.getCity2())));
        return combineCollections(collectionToAdd, collectionAddTo);
    }

    private static Stream<Stream<CityName>> addClusterToCollection(Stream<Stream<CityName>> clusters, Stream<CityName> clusterToAdd) {
        Stream<Stream<CityName>> clustersMatchCities = Stream.concat(clusters.filter((Stream<CityName> cities) -> {
            boolean cityInCluster = cities.anyMatch((CityName city) -> clusterToAdd.anyMatch((CityName routeCity) -> routeCity.equals(city)));
            return cityInCluster;
        }), Stream.of(clusterToAdd));
        Stream<CityName> combinedCluster = clustersMatchCities.flatMap((Stream<CityName> cluster) -> cluster).distinct();
        Stream<Stream<CityName>> clustersDontMatch = clusters.filter((Stream<CityName> cities) -> {
            boolean cityNotInCluster = cities.anyMatch((CityName city) -> clusterToAdd.noneMatch((CityName routeCity) -> routeCity.equals(city)));
            return cityNotInCluster;
        });
        return Stream.concat(Stream.of(combinedCluster), clustersDontMatch);
    }

    private static Stream<Stream<CityName>> combineCollections(Stream<Stream<CityName>> collectionToAdd, Stream<Stream<CityName>> collectionAddTo) {
        Stream<Stream<CityName>> output = collectionToAdd.reduce(collectionAddTo, (Stream<Stream<CityName>> clusters, Stream<CityName> cluster) -> addClusterToCollection(clusters, cluster),
                (Stream<Stream<CityName>> clusters1, Stream<Stream<CityName>> clusters2) -> combineCollections(clusters1, clusters2));
        return output;
    }
}
