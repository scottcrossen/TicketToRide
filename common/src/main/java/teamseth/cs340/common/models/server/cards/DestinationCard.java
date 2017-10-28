package teamseth.cs340.common.models.server.cards;

import java.io.Serializable;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class DestinationCard implements Serializable {

    private CityName city1;
    private CityName city2;
    private int value;

    public DestinationCard(CityName city1, CityName city2, int value) {
        this.city1 = city1;
        this.city2 = city2;
        this.value = value;
    }

    public CityName getCity1() {
        return this.city1;
    }

    public CityName getCity2() {
        return this.city2;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return city1.toString() + " to " + city2.toString();
    }
}
