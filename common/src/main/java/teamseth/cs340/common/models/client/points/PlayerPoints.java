package teamseth.cs340.common.models.client.points;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.util.RouteCalculator;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class PlayerPoints extends Observable implements IModel {
    private static PlayerPoints instance;

    public static PlayerPoints getInstance() {
        if(instance == null) {
            instance = new PlayerPoints();
        }
        return instance;
    }

    public void resetModel() {
        playerPoints = new HashMap<>();
        setChanged();
        notifyObservers();
    }

    private Map<UUID, Integer> playerPoints = new HashMap<>();
    private Map<UUID, Integer> playerUnmetDestinationPoints = new HashMap<>();
    private Map<UUID, Integer> playerMetDestinationPoints = new HashMap<>();
    private UUID playerWithLongestPath = null;
    private Map<UUID, Set<DestinationCard>> playerDestinationCardsMet = new HashMap<>();
    private Map<UUID, Set<DestinationCard>> playerDestinationCardsUnmet = new HashMap<>();

    public int getTotalPlayerPoints(UUID playerId) {
        try {
            return playerPoints.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPlayerMetDestinationCardPoints(UUID playerId) {
        try {
            return playerMetDestinationPoints.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPlayerUnmetDestinationCardPoints(UUID playerId) {
        try {
            return playerUnmetDestinationPoints.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public Set<DestinationCard> getPlayerMetDestinationCards(UUID playerId) {
        try {
            return playerDestinationCardsMet.get(playerId);
        } catch (Exception e) {
            Set<DestinationCard> output = new HashSet<>();
            playerDestinationCardsMet.put(playerId, output);
            return output;
        }
    }

    public Set<DestinationCard> getPlayerUnmetDestinationCards(UUID playerId) {
        try {
            return playerDestinationCardsUnmet.get(playerId);
        } catch (Exception e) {
            Set<DestinationCard> output = new HashSet<>();
            playerDestinationCardsUnmet.put(playerId, output);
            return output;
        }
    }

    public Optional<UUID> getPlayerWithLongestPath() {
        return Optional.ofNullable(playerWithLongestPath);
    }

    private void addMetDestinationCard(UUID playerId, DestinationCard card) {
        try {
            getPlayerMetDestinationCards(playerId).add(card);
        } catch (Exception e) {
            Set<DestinationCard> newPlayerCardSet = new HashSet<>();
            newPlayerCardSet.add(card);
            playerDestinationCardsMet.put(playerId, newPlayerCardSet);
        }
    }

    private void addUnmetDestinationCard(UUID playerId, DestinationCard card) {
        try {
            getPlayerUnmetDestinationCards(playerId).add(card);
        } catch (Exception e) {
            Set<DestinationCard> newPlayerCardSet = new HashSet<>();
            newPlayerCardSet.add(card);
            playerDestinationCardsUnmet.put(playerId, newPlayerCardSet);
        }
    }

    public void incrementPlayerPoints(UUID playerId, int points) {
        playerPoints.put(playerId, getTotalPlayerPoints(playerId) + points);
        setChanged();
        notifyObservers();
    }

    public void decrementPlayerPoints(UUID playerId, int points) {
        playerPoints.put(playerId, getTotalPlayerPoints(playerId) - points);
        setChanged();
        notifyObservers();
    }

    public void updatePlayerPointsByDestinationCard(UUID playerId, DestinationCard card) {
        Set<Route> routesOwnedByPlayer = ClientModelRoot.getInstance().board.getAllByPlayer(playerId);
        boolean playerControlsRoute = RouteCalculator.citiesConnected(card.getCity1(), card.getCity2(), routesOwnedByPlayer);
        int points = card.getValue();
        if (playerControlsRoute) {
            addMetDestinationCard(playerId, card);
            playerMetDestinationPoints.put(playerId, getPlayerMetDestinationCardPoints(playerId) + points);
            incrementPlayerPoints(playerId, points);
        } else {
            addUnmetDestinationCard(playerId, card);
            playerUnmetDestinationPoints.put(playerId, getPlayerUnmetDestinationCardPoints(playerId) - points);
            decrementPlayerPoints(playerId, points);
        }
        setChanged();
        notifyObservers();
    }

    public void setPlayerWithLongestPath(UUID playerId) {
        if (playerWithLongestPath != null) {
            decrementPlayerPoints(playerWithLongestPath, 10);
        }
        incrementPlayerPoints(playerId, 10);
        playerWithLongestPath = playerId;
    }
}
