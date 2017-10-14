package teamseth.cs340.common.models.client.cards;

import java.util.HashSet;
import java.util.Observable;

import teamseth.cs340.common.models.server.cards.DestinationCard;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class CurrentCards extends Observable {
    private static CurrentCards instance;

    public static CurrentCards getInstance() {
        if(instance == null) {
            instance = new CurrentCards();
        }
        return instance;
    }

    private HashSet<DestinationCard> destinationCards = new HashSet<>();
    private HashSet<ResourceColor> resourceCards = new HashSet<>();

    public HashSet<DestinationCard> getDestinationCards() { return destinationCards; }
    public HashSet<ResourceColor> getResourceCards() { return resourceCards; }

    public void addDestinationCard(DestinationCard destinationCard) {
        this.destinationCards.add(destinationCard);
        setChanged();
        notifyObservers();
    }

    public void addResourceCard(ResourceColor resourceCard) {
        this.resourceCards.add(resourceCard);
        setChanged();
        notifyObservers();
    }

}
