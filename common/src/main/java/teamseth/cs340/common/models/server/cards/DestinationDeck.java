package teamseth.cs340.common.models.server.cards;

import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.util.RandomList;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DestinationDeck implements Deck<DestinationCard> {

    private RandomList<DestinationCard> deck = new RandomList<>();
    private UUID id = UUID.randomUUID();

    public DestinationDeck(){
        // TODO: Seed deck from list:
        // http://jkstam.tumblr.com/post/17596731371/all-ticket-to-ride-destination-tickets
        deck.add(new DestinationCard(CityName.LosAngeles, CityName.NewYork, 21));
        deck.add(new DestinationCard(CityName.Duluth, CityName.Houston, 8));
        deck.add(new DestinationCard(CityName.StMarie, CityName.Nashville, 8));
    }

    public DestinationCard draw() throws ModelActionException {
        if (deck.size() == 0) throw new ModelActionException();
        return deck.popRandom();
    }
    public void returnCard(DestinationCard card) {
        deck.add(card);
    }
    public UUID getId() {
        return this.id;
    }
}
