package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import teamseth.cs340.common.models.server.cards.CityName;
import teamseth.cs340.common.models.server.games.PlayerColor;

/**
 * Created by Seth on 10/28/2017.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    View startView;
    View endView;
    Boolean doubleRoute;
    DrawView doubleRouteName;
    //maybe use this playerColor to define which color to draw the lines
    PlayerColor playerColor;
    Boolean routeOwned = false;
    Float offsetYstart;
    Float offsetXstart;
    Float offsetYend;
    Float offsetXend;

    //TODO add an int offset for startView and EndView, useful for double routes
    public DrawView(Context context, View startView, View endView, int color) {//, int routeLength) {
        super(context);
        setWillNotDraw(false);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        doubleRoute = false;
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
        this.startView = startView;
        this.endView = endView;
        offsetXend = new Float(0);
        offsetXstart = new Float(0);
        offsetYstart = new Float(0);
        offsetYend = new Float(0);
    }

    public DrawView(Context context, View startView, View endView, int color,
                    float offsetStartX, float offsetStartY, float offsetEndX, float offsetEndY) {//, int routeLength) {
        super(context);
        setWillNotDraw(false);

        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

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


        this.startView = startView;
        this.endView = endView;
        offsetYend = offsetEndY;
        offsetYstart = offsetStartY;
        offsetXstart = offsetStartX;
        offsetXend = offsetEndX;
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
    Path linePath = new Path();
    RectF rectF;

    @Override
    public void onDraw(Canvas canvas) {
        //canvas.drawLine(startView.getX() + offsetXstart, startView.getY() + offsetYstart,
         //       endView.getX() + offsetXend, endView.getY() + offsetYend, paint);


    // initialize components

    // draw the line
        linePath.moveTo(startView.getX() + offsetXstart, startView.getY() + offsetYstart);
        linePath.lineTo(endView.getX() + offsetXend, endView.getY() + offsetYend);

        canvas.drawPath(linePath, paint);
        rectF = new RectF(startView.getX() + offsetXstart, startView.getY() + offsetYstart,
                       endView.getX() + offsetXend, endView.getY() + offsetYend);
        linePath.computeBounds(rectF, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();
        Toast.makeText(getContext(), "Clicked" + startView.getY(), Toast.LENGTH_SHORT).show();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (rectF.contains(touchX, touchY)) {
                    Toast.makeText(getContext(), "Clicked on Route", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
