package teamseth.cs340.common.models.client.cards;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.util.client.Login;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class OtherPlayerCards extends Observable implements IModel {
    private static OtherPlayerCards instance;

    public static OtherPlayerCards getInstance() {
        if(instance == null) {
            instance = new OtherPlayerCards();
        }
        return instance;
    }

    public void resetModel() {
        deleteObservers();
        playerResourceCards = new HashMap<>();
        playerDestinationCards = new HashMap<>();
        setChanged();
        notifyObservers();
    }

    private Map<UUID, Integer> playerResourceCards = new HashMap<>();
    private Map<UUID, Integer> playerDestinationCards = new HashMap<>();

    public int getPlayerDestintationCard(UUID playerId) {
        try {
            return playerDestinationCards.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public void addPlayerDestinationCard(UUID playerId) {
        if(playerDestinationCards.containsKey(playerId) && !playerId.equals(Login.getUserId())) {
            playerDestinationCards.put(playerId, playerDestinationCards.get(playerId) + 1);
        } else {
            playerDestinationCards.put(playerId, 1);
        }
        setChanged();
        notifyObservers();
    }

    public void removePlayerDestinationCard(UUID playerId) {
        if(playerDestinationCards.containsKey(playerId) && !playerId.equals(Login.getUserId())) {
            playerDestinationCards.put(playerId, playerDestinationCards.get(playerId) - 1);
        }
        setChanged();
        notifyObservers();
    }

    public int getPlayerResourceCards(UUID playerId) {
        try {
            return playerResourceCards.get(playerId);
        } catch (Exception e) {
            return 0;
        }
    }

    public void addPlayerResourceCard(UUID playerId) {
        if(playerResourceCards.containsKey(playerId) && !playerId.equals(Login.getUserId())) {
            playerResourceCards.put(playerId, playerResourceCards.get(playerId) + 1);
        } else {
            playerResourceCards.put(playerId, 1);
        }
        setChanged();
        notifyObservers();
    }

    public void removePlayerResourceCard(UUID playerId) {
        if(playerResourceCards.containsKey(playerId) && !playerId.equals(Login.getUserId())) {
            playerResourceCards.put(playerId, playerResourceCards.get(playerId) - 1);
        }
        setChanged();
        notifyObservers();
    }

    public int getResourceAmountUsed() {
        return playerResourceCards.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getDestinationAmountUsed() {
        return playerDestinationCards.values().stream().mapToInt(Integer::intValue).sum();
    }
}
