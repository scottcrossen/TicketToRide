package teamseth.cs340.common.models.server.cards;

import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.util.RandomSet;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class ResourceDeck implements Deck<ResourceColor> {

    private RandomSet<ResourceColor> deck = new RandomSet<>();
    private UUID id = UUID.randomUUID();

    public ResourceDeck(){
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
    }
    public UUID getId() {
        return this.id;
    }

    public ResourceColor draw() throws ModelActionException {
        if (deck.size() == 0) throw new ModelActionException();
        return deck.popRandom();
    }
}
