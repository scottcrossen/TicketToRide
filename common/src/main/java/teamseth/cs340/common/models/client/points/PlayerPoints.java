package teamseth.cs340.common.models.client.points;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;

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

    private Map<UUID, Integer> playerPoints = new HashMap<>();

    public int getPlayerPoints(UUID playerId) {
        try {
            return playerPoints.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public void incrementPlayerPoints(UUID playerId, int points) {
        if(playerPoints.containsKey(playerId)) {
            playerPoints.put(playerId, playerPoints.get(playerId) + points);
        } else {
            playerPoints.put(playerId, points);
        }
    }
}
