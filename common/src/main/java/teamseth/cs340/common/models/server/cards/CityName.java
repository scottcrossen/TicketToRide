package teamseth.cs340.common.models.server.cards;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public enum CityName {
    Vancouver("Vancouver"), Calgary("Calgary"), Winnipeg("Winnipeg"), StMarie("Sault Ste. Marie"), Montreal("Montreal"), Seattle("Seattle"),
    Helena("Helena"), Duluth("Duluth"), Toronto("Toronto"), Boston("Boston"), Portland("Portland"), Omaha("Omaha"), Chicago("Chicago"),
    Pittsburgh("Pittsburgh"), NewYork("New York City"), SanFrancisco("San Francisco"), SaltLakeCity("Salt Lake City"), Denver("Denver"),
    KansasCity("Kansas City"), SaintLouis("Saint Louis"), DC("Washington D.C."), LosAngeles("Los Angeles"), LasVegas("Las Vegas"), Phoenix("Phoenix"),
    SantaFe("Santa Fe"), OklahomaCity("Oklahoma City"), LittleRock("Little Rock"), Nashville("Nashville"), Raleigh("Raleigh"), ElPaso("El Paso"),
    Dallas("Dallas"), Atlanta("Atlanta"), Charleston("Charleston"), Houston("Houston"), NewOrleans("New Orleans"), Miami("Miami");

    private final String text;

    CityName(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
