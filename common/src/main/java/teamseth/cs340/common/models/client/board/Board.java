package teamseth.cs340.common.models.client.board;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.boards.Routes;
import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;
import teamseth.cs340.common.util.OptionWrapper;

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
    private static Semaphore readers = new Semaphore(100);
    private static Semaphore writers = new Semaphore(1);

    public void resetModel() {
        deleteObservers();
        routes = new Routes();
        setChanged();
        notifyObservers();
    }

    public void claimRouteByPlayer(UUID userId, CityName city1, CityName city2, List<ResourceColor> colors, OptionWrapper<ResourceColor> routeColor) throws ModelActionException {
        try {
            writers.acquire();
            routes.claimRoute(userId, city1, city2, colors, routeColor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writers.release();
        }
        setChanged();
        notifyObservers();
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2, ResourceColor color) {
        List<Route> output = new LinkedList<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            output = routes.getMatchingRoutes(city1, city2, color).stream().map((Route route) -> route.copy()).collect(Collectors.toList());
        } catch (Exception e) {
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public List<Route> getMatchingRoutes(CityName city1, CityName city2) {
        List<Route> output = new LinkedList<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            output = routes.getMatchingRoutes(city1, city2).stream().map((Route route) -> route.copy()).collect(Collectors.toList());
        } catch (Exception e) {
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public Set<Route> getAllClaimedRoutes() {
        Set<Route> output = new HashSet<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
        output = routes.getAllClaimed().stream().map((Route route) -> route.copy()).collect(Collectors.toSet());
        } catch (Exception e) {
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public Set<Route> getAllRoutes() {
        Set<Route> output = new HashSet<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
        return routes.getAll().stream().map((Route route) -> route.copy()).collect(Collectors.toSet());
        } catch (Exception e) {
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }

    public Set<Route> getAllByPlayer(UUID playerId) {
        Set<Route> output = new HashSet<>();
        try {
            if (readers.availablePermits() == 100) {
                writers.acquire();
            }
            readers.acquire();
            return routes.getAllByPlayer(playerId).stream().map((Route route) -> route.copy()).collect(Collectors.toSet());
        } catch (Exception e) {
        } finally {
            readers.release();
            if (readers.availablePermits() == 100) {
                writers.release();
            }
        }
        return output;
    }
}
