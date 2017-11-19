package teamseth.cs340.common.models.server.cards;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.exceptions.ResourceNotFoundException;
import teamseth.cs340.common.util.RandomList;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ResourceDeck implements Deck<ResourceColor> {

    private RandomList<ResourceColor> deck = new RandomList<>();
    private List<ResourceColor> faceUp = new LinkedList<>();
    private UUID id = UUID.randomUUID();

    public ResourceDeck() {
        for (int i = 0; i < 12; i++){
            deck.add(ResourceColor.PURPLE);
            deck.add(ResourceColor.WHITE);
            deck.add(ResourceColor.BLUE);
            deck.add(ResourceColor.YELLOW);
            deck.add(ResourceColor.ORANGE);
            deck.add(ResourceColor.BLACK);
            deck.add(ResourceColor.RED);
            deck.add(ResourceColor.GREEN);
            deck.add(ResourceColor.RAINBOW);
        }
        for (int i = 0; i < 2; i++) {
            deck.add(ResourceColor.RAINBOW);
        }
        for (int i = 0; i < 5; i++) {
            faceUp.add(deck.popRandom());
        }
    }
    public UUID getId() {
        return this.id;
    }
    public List<ResourceColor> getFaceUp() {return faceUp;}

    public ResourceColor draw() throws ModelActionException {
        if (deck.size() == 0) throw new ModelActionException();
        return deck.popRandom();
    }

    public void returnCard(ResourceColor card) {
        deck.add(card);
    }

    public Optional<ResourceColor> drawFaceUpCard(ResourceColor oldCard) throws ModelActionException, ResourceNotFoundException {
        Iterator<ResourceColor> iterator = faceUp.iterator();
        while (iterator.hasNext()) {
            ResourceColor next = iterator.next();
            if (next.equals(oldCard)) {
                iterator.remove();
                try {
                    ResourceColor newCard = draw();
                    faceUp.add(newCard);
                    return Optional.of(newCard);
                } catch (Exception e) {
                    return Optional.empty();
                }
            }
        }
        throw new ResourceNotFoundException();
    }

    public List<ResourceColor> checkAndResuffleFaceUpCards() {
        if (faceUp.stream().filter((ResourceColor color) -> color.equals(ResourceColor.RAINBOW)).count() >= 3 && deck.size() + faceUp.size() > 5) {
            faceUp.stream().forEach((ResourceColor color) -> returnCard(color));
            int amntRainbows = (int) deck.stream().filter((ResourceColor color) -> color.equals(ResourceColor.RAINBOW)).count();
            for (int i = 0; i < 5; i++) {
                faceUp.add(deck.popRandom());
            }
            if (deck.size() + faceUp.size() - amntRainbows >= 3) {
                checkAndResuffleFaceUpCards();
            }
            return getFaceUp();
        } else {
            return new LinkedList<>();
        }
    }
}
