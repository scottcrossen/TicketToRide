package teamseth.cs340.common.models.client.carts;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class PlayerCarts extends Observable implements IModel {
    private static PlayerCarts instance;

    public static PlayerCarts getInstance() {
        if(instance == null) {
            instance = new PlayerCarts();
        }
        return instance;
    }
    public void resetModel() {
        playerCarts = new HashMap<>();
        setChanged();
        notifyObservers();
    }

    private Map<UUID, Integer> playerCarts = new HashMap<>();

    public int getPlayerCarts(UUID playerId) {
        try {
            return playerCarts.get(playerId);
        } catch (Exception e) {
            return 45;
        }
    }

    public void decrementPlayerCarts(UUID playerId, int carts) {
        if(playerCarts.containsKey(playerId)) {
            playerCarts.put(playerId, playerCarts.get(playerId) - carts);
        } else {
            playerCarts.put(playerId, 45 - carts);
        }
        setChanged();
        notifyObservers();
    }

    public void incrementPlayerCarts(UUID playerId, int carts) {
        if(playerCarts.containsKey(playerId)) {
            playerCarts.put(playerId, playerCarts.get(playerId) + carts);
        } else {
            playerCarts.put(playerId, 45 + carts);
        }
        setChanged();
        notifyObservers();
    }
}