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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamseth.cs340.common.models.client.ClientModelRoot;
import teamseth.cs340.common.models.server.boards.Route;

/**
 * Created by Seth on 10/28/2017.
 */

public class MapView extends View {
    private Paint paint = new Paint();
    private List<DrawView> routeNames;
    private Map<Route, RectF> rectFList = new HashMap<>();

    public MapView(Context context) {
        super(context);
        setWillNotDraw(false);
        routeNames = new ArrayList<>();
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

    List<Route> listDoubleRoute = new ArrayList<>();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for(DrawView dr : routeNames) {
                    if (rectFList.get(dr.getRoute()).contains(touchX, touchY)) {
                        Toast.makeText(getContext(), "Clicked on " + dr.getRoute(), Toast.LENGTH_SHORT).show();

                        //if(dr.getRoute().isDouble()) then go to the choose which route screen
                        //else go immediately to the choose which cards to use screen
                        listDoubleRoute = ClientModelRoot.board.getMatchingRoutes(dr.getRoute().getCity1(),
                                dr.getRoute().getCity2());
                        for( Route rt : listDoubleRoute) {
                            Toast.makeText(getContext(), "found match on " + rt, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
        return true;
    }
}

