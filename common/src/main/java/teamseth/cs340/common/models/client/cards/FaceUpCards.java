package teamseth.cs340.common.models.client.cards;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.models.IModel;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class FaceUpCards implements IModel {
    private static FaceUpCards instance;

    public static FaceUpCards getInstance() {
        if(instance == null) {
            instance = new FaceUpCards();
        }
        return instance;
    }

    public void resetModel() {
        resourceCards = new LinkedList<>();
    }

    private List<ResourceColor> resourceCards = new LinkedList<>();
    public List<ResourceColor> getFaceUpCards() { return resourceCards; }

    public void seedCards(List<ResourceColor> cards) {
        this.resourceCards = cards;
    }
    public void replaceCard(ResourceColor oldCard, ResourceColor newCard) throws ResourceNotFoundException {
        Iterator<ResourceColor> iterator = resourceCards.iterator();
        while (iterator.hasNext()) {
            ResourceColor next = iterator.next();
            if (next.equals(oldCard)) {
                iterator.remove();
                resourceCards.add(newCard);
            }
        }
        throw new ResourceNotFoundException();
    }
}
