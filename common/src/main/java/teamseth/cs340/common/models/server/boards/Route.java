package teamseth.cs340.common.models.server.boards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private Boolean isOwned;
    private float offsetStartX;
    private float offsetStartY;
    private float offsetEndX;
    private float offsetEndY;

    public Route(CityName city1, CityName city2, ResourceColor color, int length,
                 float offsetStartX, float offsetEndX, float offsetStartY, float offsetEndY) {
        this.city1 = city1;
        this.city2 = city2;
        this.length = length;
        this.color = color;
        this.claimedBy = null;
        this.isOwned = false;
        this.offsetStartX = offsetStartX;
        this.offsetStartY = offsetStartY;
        this.offsetEndX = offsetEndX;
        this.offsetEndY = offsetEndY;
    }

    public ResourceColor getColor() {
        return this.color;
    }

    public CityName getCity1() {
        return this.city1;
    }

    public CityName getCity2() {
        return this.city2;
    }

    public Boolean getOwned() {
        return isOwned;
    }

    public void setOwned(Boolean owned) {
        isOwned = owned;
    }

    public Float getOffsetStartX() {
        return offsetStartX;
    }

    public void setOffsetStartX(Float offsetStartX) {
        this.offsetStartX = offsetStartX;
    }

    public Float getOffsetStartY() {
        return offsetStartY;
    }

    public void setOffsetStartY(Float offsetStartY) {
        this.offsetStartY = offsetStartY;
    }

    public Float getOffsetEndX() {
        return offsetEndX;
    }

    public void setOffsetEndX(Float offsetEndX) {
        this.offsetEndX = offsetEndX;
    }

    public Float getOffsetEndY() {
        return offsetEndY;
    }

    public void setOffsetEndY(Float offsetEndY) {
        this.offsetEndY = offsetEndY;
    }

    public Optional<UUID> getClaimedPlayer() { return Optional.ofNullable(this.claimedBy); }

    public int getLength() { return this.length;}

    public void claimBy(UUID playerId) { this.claimedBy = playerId;}

    public boolean equals(CityName city1, CityName city2, List<ResourceColor> colors) {

        return (
            ((this.city1.equals(city1) && this.city2.equals(city2)) || (this.city1.equals(city2) && this.city2.equals(city1))) &&
            (this.length == colors.size()) &&
            colors.stream().allMatch((ResourceColor color) -> (this.color.equals(ResourceColor.RAINBOW) || color.equals(ResourceColor.RAINBOW) || this.color.equals(color)))
        );
    }

    public boolean compareCitiesAndColor(Route o) {
        List<ResourceColor> otherColors = new ArrayList<>();
        otherColors.add(o.color);
        return equals(o.city1, o.city2, otherColors);
    }


    public String toString() {
        return city1.toString() + " to " + city2.toString() + " of length " + Integer.toString(length) + " and color " + color.toString();
    }

    public boolean hasCity(CityName city) {
        return (getCity1().equals(city) || getCity2().equals(city));
    }
}
