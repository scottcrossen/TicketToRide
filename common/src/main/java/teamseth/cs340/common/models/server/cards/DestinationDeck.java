package teamseth.cs340.common.models.server.cards;

import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.util.RandomSet;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DestinationDeck implements Deck<DestinationCard> {

    private RandomSet<DestinationCard> deck = new RandomSet<>();
    private UUID id = UUID.randomUUID();

    public DestinationDeck(){
        // Seed deck from list:
        // http://jkstam.tumblr.com/post/17596731371/all-ticket-to-ride-destination-tickets
        deck.add(new DestinationCard(CityName.LosAngeles, CityName.NewYork, 21));
    }

    public DestinationCard draw() throws ModelActionException {
        if (deck.size() == 0) throw new ModelActionException();
        return deck.popRandom();
    }
    public UUID getId() {
        return this.id;
    }
}
