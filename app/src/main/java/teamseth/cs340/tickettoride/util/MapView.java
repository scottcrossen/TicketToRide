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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import teamseth.cs340.common.models.server.games.PlayerColor;

/**
 * Created by Seth on 10/28/2017.
 */

public class MapView extends View {
    Paint paint = new Paint();
    View startView;
    View endView;
    Boolean doubleRoute;
    Set<DrawView> routeNames;
    //maybe use this playerColor to define which color to draw the lines
    PlayerColor playerColor;
    Boolean routeOwned = false;
    Float offsetYstart;
    Float offsetXstart;
    Float offsetYend;
    Float offsetXend;
    private Map<String, RectF> rectFList = new HashMap<>();


    //TODO add an int offset for startView and EndView, useful for double routes
    public MapView(Context context) {//, int routeLength) {
        super(context);
        setWillNotDraw(false);
        routeNames = new HashSet<DrawView>();
        //this.startView = startView;
        //this.endView = endView;
        //offsetXend = new Float(0);
        //offsetXstart = new Float(0);
        //offsetYstart = new Float(0);
        //offsetYend = new Float(0);
    }


    public void addRouteToMap(DrawView dr) {
        routeNames.add(dr);
    }
    /*public MapView(Context context, View startView, View endView, int color,
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
    }*/

    @Override
    public void onDraw(Canvas canvas) {
        //canvas.drawLine(startView.getX() + offsetXstart, startView.getY() + offsetYstart,
        //       endView.getX() + offsetXend, endView.getY() + offsetYend, paint);

        // draw the line
        for(DrawView dr : routeNames) {
            Path linePath = new Path();
            paint = dr.getPaint();
            if(!routeOwned) {
                // only draws dashed line if route is unclaimed
                paint.setPathEffect(new DashPathEffect(new float[] {80,20}, 0));
            }
            else {
                //draw solid line if route is claimed
                paint.setPathEffect(new DashPathEffect(new float[] {80,0}, 0));
            }
            linePath.moveTo(dr.startView.getX() + dr.offsetXstart, dr.startView.getY() + dr.offsetYstart);
            linePath.lineTo(dr.endView.getX() + dr.offsetXend, dr.endView.getY() + dr.offsetYend);

            canvas.drawPath(linePath, paint);
            Path rectLine = new Path();

            float centerOnX = ( dr.startView.getX() + dr.offsetXstart + dr.endView.getX() + dr.offsetXend ) / 2;
            float centerOnY = ( dr.startView.getY() + dr.offsetYstart + dr.endView.getY() + dr.offsetYend ) / 2;
            rectLine.moveTo(centerOnX - 30, centerOnY - 30);
            rectLine.moveTo(centerOnX + 30, centerOnY + 30);
            RectF rectF = new RectF(centerOnX - 30, centerOnY + 30, centerOnX + 30, centerOnY - 30);
            rectFList.put(dr.getName(), rectF);
            //canvas.drawRect(rectF,paint);
            rectLine.computeBounds(rectFList.get(dr.getName()), false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for(DrawView dr : routeNames) {
                    if (rectFList.get(dr.getName()).contains(touchX, touchY)) {
                        Toast.makeText(getContext(), "Clicked on " + dr.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return true;
    }
}

