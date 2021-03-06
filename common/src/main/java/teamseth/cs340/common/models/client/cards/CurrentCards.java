package teamseth.cs340.common.models.client.cards;

import java.util.Iterator;
import java.util.LinkedList;
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

    public void resetModel() {
        deleteObservers();
        destinationCards = new LinkedList<>();
        resourceCards = new LinkedList<>();
        others.resetModel();
        faceUp.resetModel();
        setChanged();
        notifyObservers();
    }

    private LinkedList<DestinationCard> destinationCards = new LinkedList<>();
    private LinkedList<ResourceColor> resourceCards = new LinkedList<>();

    public LinkedList<DestinationCard> getDestinationCards() { return destinationCards; }
    public LinkedList<ResourceColor> getResourceCards() { return resourceCards; }

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

    public void removeDestinationCard(DestinationCard destinationCard) {
        Iterator<DestinationCard> iterator = this.destinationCards.iterator();
        while (iterator.hasNext()) {
            DestinationCard next = iterator.next();
            if ((
                    (next.getCity1().equals(destinationCard.getCity1()) && next.getCity2().equals(destinationCard.getCity2())) ||
                    (next.getCity1().equals(destinationCard.getCity2()) && next.getCity2().equals(destinationCard.getCity1()))
                ) && next.getValue() == destinationCard.getValue()) {
                iterator.remove();
                setChanged();
                notifyObservers();
                return;
            }
        }
    }

    public void removeResourceCard(ResourceColor resourceCard) {
        Iterator<ResourceColor> iterator = this.resourceCards.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(resourceCard)) {
                iterator.remove();
                setChanged();
                notifyObservers();
                return;
            }
        }
        setChanged();
        notifyObservers();
    }

    public static final OtherPlayerCards others = OtherPlayerCards.getInstance();
    public static final FaceUpCards faceUp = FaceUpCards.getInstance();

}
