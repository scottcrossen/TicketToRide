package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.games.PlayerColor;

/**
 * Created by Seth on 10/28/2017.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    View startView;
    View endView;
    //maybe use this playerColor to define which color to draw the lines
    PlayerColor playerColor;
    Boolean routeOwned = false;

    public DrawView(Context context, View startView, View endView, int color) {
        super(context);
        setWillNotDraw(false);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        //Route route = getLocalRoute();
        paint.setColor(color);
        if(!routeOwned) {
            paint.setPathEffect(new DashPathEffect(new float[] {80,20}, 0));
        }
        else {
            //TODO set the player color here
            //paint.setColor(playerColor);
        }

        // only draws dashed line if route is unclaimed
        this.startView = startView;
        this.endView = endView;
    }

    public void isOwned(Boolean owned) {
        if(owned) {
            routeOwned = true;
        }
    }

    /*private Route getLocalRoute(View start, View end) {
        CityName startCity = getFromRouteCityName(start);
        CityName endCity = getFromRouteCityName(end);
        Route route = getRouteFromCities(startCity, endCity);

        try {
            PlayerColor color = ClientModelRoot.getInstance().games.getActive().getPlayerColors().get(route.getClaimedPlayer());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return route;
    }*/

    /*private Route getRouteFromCities(CityName start, CityName end) {
        Route route;

        return route;
    }*/

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

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startView.getX()+10, startView.getY()+15, endView.getX()+10, endView.getY(), paint);
    }
}
