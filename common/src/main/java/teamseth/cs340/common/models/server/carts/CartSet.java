package teamseth.cs340.common.models.server.carts;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.persistence.IStorable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CartSet implements IModel, IStorable {
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
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
    }

    public void incrementPlayerCarts(UUID playerId, int carts) {
        if(playerCarts.containsKey(playerId)) {
            playerCarts.put(playerId, playerCarts.get(playerId) + carts);
        } else {
            playerCarts.put(playerId, 45 + carts);
        }
    }
}