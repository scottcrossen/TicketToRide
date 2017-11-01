package teamseth.cs340.common.models.server.boards;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.cards.ResourceColor;

/**
 * @author Scott Leland Crossen
 * @Copyright 2017 Scott Leland Crossen
 */
public class Route implements Serializable {
    private CityName city1;
    private CityName city2;
    private int length;
    private ResourceColor color;
    private UUID claimedBy;

    public Route(CityName city1, CityName city2, ResourceColor color, int length) {
        this.city1 = city1;
        this.city2 = city2;
        this.length = length;
        this.color = color;
        this.claimedBy = null;
    }

    public CityName getCity1() {
        return this.city1;
    }

    public CityName getCity2() {
        return this.city2;
    }

    public Optional<UUID> getClaimedPlayer() { return Optional.ofNullable(this.claimedBy); }

    public int getLength() { return this.length;}

    public void claimBy(UUID playerId) { this.claimedBy = playerId;}

    public boolean equals(CityName city1, CityName city2, ResourceColor color) {
        return (
            ((this.city1 == city1 && this.city2 == city2) || (this.city1 == city2 && this.city2 == city1)) &&
            (this.color == ResourceColor.RAINBOW || color == ResourceColor.RAINBOW || this.color == color)
            );
    }

    public boolean compareCitiesAndColor(Route o) {
        return equals(o.city1, o.city2, o.color);
    }

    public String toString() {
        return city1.toString() + " to " + city2.toString() + " of length " + Integer.toString(length) + " and color " + color.toString();
    }
}
