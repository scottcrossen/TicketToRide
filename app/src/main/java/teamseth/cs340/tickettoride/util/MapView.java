package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

import teamseth.cs340.common.models.server.boards.Route;

/**
 * Created by Seth on 10/28/2017.
 */

public class MapView extends View {
    private Paint paint = new Paint();
    private Set<DrawView> routeNames;
    private Boolean routeOwned = false;
    private Map<Route, RectF> rectFList = new HashMap<>();
    //TODO print length of route here, do it in center of route

    //TODO add an int offset for startView and EndView, useful for double routes
    public MapView(Context context) {
        super(context);
        setWillNotDraw(false);
        routeNames = new HashSet<DrawView>();
    }


    public void addRouteToMap(DrawView dr) {
        routeNames.add(dr);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // draw the line
        for(DrawView dr : routeNames) {
            Path linePath = new Path();
            paint = dr.getPaint();

            linePath.moveTo(dr.getStartView().getX() + dr.getOffsetXstart(),
                    dr.getStartView().getY() + dr.getOffsetYstart());
            linePath.lineTo(dr.getEndView().getX() + dr.getOffsetXend(),
                    dr.getEndView().getY() + dr.getOffsetYend());

            canvas.drawPath(linePath, paint);
            Path rectLine = new Path();

            float centerOnX = (dr.getStartView().getX() + dr.getEndView().getX()) / 2;
            float centerOnY = (dr.getStartView().getY() + dr.getEndView().getY()) / 2;
            rectLine.moveTo(centerOnX - 30, centerOnY - 30);
            rectLine.moveTo(centerOnX + 30, centerOnY + 30);
            RectF rectF = new RectF(centerOnX - 30, centerOnY + 30, centerOnX + 30, centerOnY - 30);
            if(!dr.getRoute().getOwned()) {
                // only draws dashed line if route is unclaimed
                rectFList.put(dr.getRoute(), rectF);
                //canvas.drawRect(rectF,paint);
                rectLine.computeBounds(rectFList.get(dr.getRoute()), false);
                String length = Integer.toString(dr.getRoute().getLength());
                Paint paint2 = new Paint();
                paint2.setColor(Color.WHITE);
                paint2.setTextSize(40);
                paint2.setShadowLayer(5.0f, 5.0f, 5.0f, Color.BLACK);
                canvas.drawText(length,centerOnX,centerOnY,paint2);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for(DrawView dr : routeNames) {
                    if (rectFList.get(dr.getRoute()).contains(touchX, touchY)) {
                        //if(dr.getRoute().isDouble()) then go to the choose which route screen
                        //else go immediately to the choose which cards to use screen
                        Toast.makeText(getContext(), "Clicked on " + dr.getRoute(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return true;
    }
}

