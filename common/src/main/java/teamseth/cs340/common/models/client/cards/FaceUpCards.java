package teamseth.cs340.common.models.client.cards;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class FaceUpCards extends Observable implements IModel {
    private static FaceUpCards instance;

    public static FaceUpCards getInstance() {
        if(instance == null) {
            instance = new FaceUpCards();
        }
        return instance;
    }

    public void resetModel() {
        deleteObservers();
        resourceCards = new LinkedList<>();
        setChanged();
        notifyObservers();
    }

    private List<ResourceColor> resourceCards = new LinkedList<>();
    public List<ResourceColor> getFaceUpCards() { return resourceCards; }

    public void seedCards(List<ResourceColor> cards) {
        this.resourceCards = cards;
        setChanged();
        notifyObservers();
    }
    public void replaceCard(ResourceColor oldCard, Optional<ResourceColor> newCard) throws ResourceNotFoundException {
        Iterator<ResourceColor> iterator = resourceCards.iterator();
        while (iterator.hasNext()) {
            ResourceColor next = iterator.next();
            if (next.equals(oldCard)) {
                iterator.remove();
                newCard.map((ResourceColor newColor) -> {
                    resourceCards.add(newColor);
                    return newColor;
                });
                setChanged();
                notifyObservers();
                return;
            }
        }
        throw new ResourceNotFoundException();
    }
}
