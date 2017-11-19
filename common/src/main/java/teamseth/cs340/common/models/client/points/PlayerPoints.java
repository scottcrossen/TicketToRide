package teamseth.cs340.common.models.client.points;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;

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
    }

    private Map<UUID, Integer> playerPoints = new HashMap<>();
    private Map<UUID, Integer> playerUnmetDestinationPoints = new HashMap<>();
    private Map<UUID, Integer> playerDestinationPoints = new HashMap<>();

    public int getPlayerPoints(UUID playerId) {
        try {
            return playerPoints.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public int getPlayerDestinationCardPoints(UUID playerId) {
        try {
            return playerDestinationPoints.get(playerId);
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

    public void incrementPlayerPoints(UUID playerId, int points) {
        playerPoints.put(playerId, playerPoints.get(playerId) + points);
    }

    public void updatePlayerPointsByDestinationCard(UUID playerId, DestinationCard card) {
        boolean playerControlsRoute = ClientModelRoot.getInstance().board.getMatchingRoutes(card.getCity1(), card.getCity2(), ResourceColor.RAINBOW).stream().findFirst().flatMap((Route route) -> route.getClaimedPlayer()).map((UUID routePlayerId) -> routePlayerId.equals(playerId)).orElse(false);
        int points = card.getValue();
        if (playerControlsRoute) {
            playerPoints.put(playerId, playerPoints.get(playerId) + points);
        } else {
            playerPoints.put(playerId, playerPoints.get(playerId) - points);
        }
    }
}
