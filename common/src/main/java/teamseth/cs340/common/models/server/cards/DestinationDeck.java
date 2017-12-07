package teamseth.cs340.common.models.server.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import teamseth.cs340.common.exceptions.ModelActionException;
import teamseth.cs340.common.persistence.IStorable;
import teamseth.cs340.common.util.RandomList;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DestinationDeck implements Deck<DestinationCard>, IStorable {

    private RandomList<DestinationCard> deck = new RandomList<>();
    private List<DestinationCard> discards = new ArrayList<>();
    private UUID id = UUID.randomUUID();

    public DestinationDeck(){
        deck.add(new DestinationCard(CityName.LosAngeles, CityName.NewYork, 21));
        deck.add(new DestinationCard(CityName.Duluth, CityName.Houston, 8));
        deck.add(new DestinationCard(CityName.StMarie, CityName.Nashville, 8));
        deck.add(new DestinationCard(CityName.NewYork, CityName.Atlanta, 6));
        deck.add(new DestinationCard(CityName.Portland, CityName.Nashville, 17));
        deck.add(new DestinationCard(CityName.Vancouver, CityName.Montreal, 20));
        deck.add(new DestinationCard(CityName.Duluth, CityName.ElPaso, 10));
        deck.add(new DestinationCard(CityName.Toronto, CityName.Miami, 10));
        deck.add(new DestinationCard(CityName.Portland, CityName.Phoenix, 11));
        deck.add(new DestinationCard(CityName.Dallas, CityName.NewYork, 11));
        deck.add(new DestinationCard(CityName.Calgary, CityName.SaltLakeCity, 7));
        deck.add(new DestinationCard(CityName.Calgary, CityName.Phoenix, 7));
        deck.add(new DestinationCard(CityName.LosAngeles, CityName.Miami, 20));
        deck.add(new DestinationCard(CityName.Winnipeg, CityName.LittleRock, 11));
        deck.add(new DestinationCard(CityName.SanFrancisco, CityName.Atlanta, 17));
        deck.add(new DestinationCard(CityName.KansasCity, CityName.Houston, 5));
        deck.add(new DestinationCard(CityName.LosAngeles, CityName.Chicago, 16));
        deck.add(new DestinationCard(CityName.Denver, CityName.Pittsburgh, 11));
        deck.add(new DestinationCard(CityName.Chicago, CityName.SantaFe, 9));
        deck.add(new DestinationCard(CityName.Vancouver, CityName.SantaFe, 13));
        deck.add(new DestinationCard(CityName.Boston, CityName.Miami, 12));
        deck.add(new DestinationCard(CityName.Chicago, CityName.NewOrleans, 7));
        deck.add(new DestinationCard(CityName.Montreal, CityName.Atlanta, 9));
        deck.add(new DestinationCard(CityName.Seattle, CityName.NewYork, 22));
        deck.add(new DestinationCard(CityName.Denver, CityName.ElPaso, 4));
        deck.add(new DestinationCard(CityName.Helena, CityName.LosAngeles, 8));
        deck.add(new DestinationCard(CityName.Winnipeg, CityName.Houston, 12));
        deck.add(new DestinationCard(CityName.Montreal, CityName.NewOrleans, 13));
        deck.add(new DestinationCard(CityName.StMarie, CityName.OklahomaCity, 9));
        deck.add(new DestinationCard(CityName.Seattle, CityName.LosAngeles, 9));
    }

    public DestinationCard draw() throws ModelActionException {
        if (deck.size() == 0 && discards.size() > 0) {
            deck.addAll(discards);
            discards.clear();
        } else if (deck.size() == 0 && discards.size() <= 0) throw new ModelActionException();
        return deck.popRandom();
    }
    public void returnCard(DestinationCard card) {
        discards.add(card);
    }
    public UUID getId() {
        return this.id;
    }
}
