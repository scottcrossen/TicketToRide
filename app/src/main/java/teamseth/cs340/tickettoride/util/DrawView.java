package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

import teamseth.cs340.common.models.server.cards.CityName;

/**
 * Created by Seth on 10/28/2017.
 */

public class DrawView extends View {
    private Paint paint = new Paint();
    private View startView;
    private View endView;
    private Boolean doubleRoute;
    private DrawView doubleRouteName;
    private Boolean routeOwned = false;
    private Float offsetYstart;
    private Float offsetXstart;
    private Float offsetYend;
    private Float offsetXend;
    private int color;
    private String name;


    //TODO add an int offset for startView and EndView, useful for double routes
    public DrawView(Context context, View startView, View endView, int color, String name) {//, int routeLength) {
        super(context);
        setWillNotDraw(false);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        doubleRoute = false;
        //Route route = getLocalRoute();
        this.color = color;
        paint.setColor(color);

        if(!routeOwned) {
            // only draws dashed line if route is unclaimed
            paint.setPathEffect(new DashPathEffect(new float[] {80,20}, 0));
        }
        else {
            //draw solid line if route is claimed
            paint.setPathEffect(new DashPathEffect(new float[] {80,0}, 0));
        }
        this.name = name;
        this.startView = startView;
        this.endView = endView;
        this.offsetXend = new Float(0);
        this.offsetXstart = new Float(0);
        this.offsetYstart = new Float(0);
        this.offsetYend = new Float(0);
    }

    public DrawView(Context context, View startView, View endView, int color,
                    float offsetStartX, float offsetStartY, float offsetEndX, float offsetEndY,
                    String name) {//, int routeLength) {
        super(context);
        setWillNotDraw(false);

        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        this.color = color;
        //Route route = getLocalRoute();

        paint.setColor(color);
        if(!routeOwned) {
            // only draws dashed line if route is unclaimed
            paint.setPathEffect(new DashPathEffect(new float[] {80,20}, 0));
        }
        else {
            //draw solid line if route is claimed
            paint.setPathEffect(new DashPathEffect(new float[] {80,0}, 0));
        }

        this.name = name;
        this.startView = startView;
        this.endView = endView;
        this.offsetYend = offsetEndY;
        this.offsetYstart = offsetStartY;
        this.offsetXstart = offsetStartX;
        this.offsetXend = offsetEndX;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Float getOffsetYstart() {
        return offsetYstart;
    }

    public void setOffsetYstart(Float offsetYstart) {
        this.offsetYstart = offsetYstart;
    }

    public Float getOffsetXstart() {
        return offsetXstart;
    }

    public void setOffsetXstart(Float offsetXstart) {
        this.offsetXstart = offsetXstart;
    }

    public Float getOffsetYend() {
        return offsetYend;
    }

    public void setOffsetYend(Float offsetYend) {
        this.offsetYend = offsetYend;
    }

    public Float getOffsetXend() {
        return offsetXend;
    }

    public void setOffsetXend(Float offsetXend) {
        this.offsetXend = offsetXend;
    }

    public View getStartView() {
        return startView;
    }

    public void setStartView(View startView) {
        this.startView = startView;
    }

    public View getEndView() {
        return endView;
    }

    public void setEndView(View endView) {
        this.endView = endView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void isOwned(Boolean owned) {
        if(owned) {
            routeOwned = true;
        }
    }

    public DrawView getDoubleRouteName() {
        return doubleRouteName;
    }

    public void setDoubleRouteName(DrawView doubleRouteName) {
        this.doubleRouteName = doubleRouteName;
    }

    public Boolean getDoubleRoute() {
        return doubleRoute;
    }

    public void setDoubleRoute(Boolean doubleRoute) {
        this.doubleRoute = doubleRoute;
    }

    public Boolean getRouteOwned() {
        return routeOwned;
    }

    public void setRouteOwned(Boolean routeOwned) {
        this.routeOwned = routeOwned;
    }

    private CityName getFromRouteCityName(View city) {
        switch (city.toString()) {
            case "vancouver":
                return CityName.Vancouver;
            case "seattle":
                return CityName.Seattle;
            case "portland":
                return CityName.Portland;
            case "sanFrancisco":
                return CityName.SanFrancisco;
            case "losAngeles":
                return CityName.LosAngeles;
            case "phoenix":
                return CityName.Phoenix;
            case "lasVegas":
                return CityName.LasVegas;
            case "elPaso":
                return CityName.ElPaso;
            case "santaFe":
                return CityName.SantaFe;
            case "saltLake":
                return CityName.SaltLakeCity;
            case "calgary":
                return CityName.Calgary;
            case "helena":
                return CityName.Helena;
            case "denver":
                return CityName.Denver;
            case "winnipeg":
                return CityName.Winnipeg;
            case "duluth":
                return CityName.Duluth;
            case "omaha":
                return CityName.Omaha;
            case "oklahoma":
                return CityName.OklahomaCity;
            case "kansas":
                return CityName.KansasCity;
            case "dallas":
                return CityName.Dallas;
            case "houston":
                return CityName.Houston;
            case "newOrleans":
                return CityName.NewOrleans;
            case "littleRock":
                return CityName.LittleRock;
            case "saintLouis":
                return CityName.SaintLouis;
            case "chicago":
                return CityName.Chicago;
            case "nashville":
                return CityName.Nashville;
            case "miami":
                return CityName.Miami;
            case "atlanta":
                return CityName.Atlanta;
            case "charleston":
                return CityName.Charleston;
            case "raleigh":
                return CityName.Raleigh;
            case "dc":
                return CityName.DC;
            case "pittsburgh":
                return CityName.Pittsburgh;
            case "newYork":
                return CityName.NewYork;
            case "boston":
                return CityName.Boston;
            case "toronto":
                return CityName.Toronto;
            case "saultStMarie":
                return CityName.StMarie;
            case "montreal":
                return CityName.Montreal;
        }
        return CityName.Vancouver;
    }
}
