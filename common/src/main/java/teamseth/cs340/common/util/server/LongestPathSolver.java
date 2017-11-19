package teamseth.cs340.common.util.server;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Stream;

import teamseth.cs340.common.models.server.boards.Route;

/**
 * @author Scott Leland Crossen
 * @copyright (C) Copyright 2017 Scott Leland Crossen
 */
public class LongestPathSolver {
    public static final int calculateLongestPath(Set<Route> routeSet){
        Stream<Route> visited = Stream.empty();
        Stream<Route> routes = routeSet.stream();
        Stream<Integer> solutions = routes.flatMap((Route route) -> recurseOnRoute(routes, route, visited)).map((Tuple<Integer, Set<Route>> solution) -> solution.get1());
        Integer max = solutions.max((Integer solution1, Integer solution2) -> Integer.compare(solution1, solution2)).orElseGet(() -> -1);
        return max;
    }

    private static final Stream<Tuple<Integer, Set<Route>>> recurseOnRoute(Stream<Route> routes, Route route, Stream<Route> visited) {
        Stream<Route> neighborRoutes = routes.filter((Route currentRoute) -> (currentRoute.hasCity(route.getCity1()) || currentRoute.hasCity(route.getCity2())));
        Stream<Route> currentVisited = Stream.concat(visited, Stream.of(route));
        Stream<Tuple<Integer, Set<Route>>> output = neighborRoutes.filter((Route currentRoute) -> {
            boolean hasBeenVisited = currentVisited.noneMatch((visitedRoute) -> visitedRoute.compareCitiesAndColor(currentRoute));
            return hasBeenVisited;
        }).flatMap((Route currentRoute) -> {
            Stream<Tuple<Integer, Set<Route>>> result = recurseOnRoute(routes, currentRoute, currentVisited);
            return result.map((Tuple<Integer, Set<Route>> singleResult) -> new Tuple<>(singleResult.get1() + route.getLength(), singleResult.get2()));
        });
        return Stream.concat(output, Stream.of(new Tuple(route.getLength(), visited)));
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
}
