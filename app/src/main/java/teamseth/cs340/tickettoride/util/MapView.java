package teamseth.cs340.tickettoride.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamseth.cs340.common.models.server.boards.Route;
import teamseth.cs340.tickettoride.fragment.SelectRouteFragment;

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
            if(!dr.getRoute().getClaimedPlayer().isPresent()) {
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
                List<DrawView> tempSetRoutes = new ArrayList<>();
                for(DrawView dr : routeNames) {
                    if (rectFList.get(dr.getRoute()).contains(touchX, touchY)) {
                        tempSetRoutes.add(dr);
                        //if(dr.getRoute().isDouble()) then go to the choose which route screen
                        //else go immediately to the choose which cards to use screen
                        /*listDoubleRoute = ClientModelRoot.board.getMatchingRoutes(dr.getRoute().getCity1(),
                                dr.getRoute().getCity2());
                        for( Route rt : listDoubleRoute) {
                            Toast.makeText(getContext(), "found match on " + rt, Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }
                if(!tempSetRoutes.isEmpty()){
                    if(tempSetRoutes.size() > 1) {
                        String route1 = tempSetRoutes.get(0).getRoute().toString();
                        String route2 = tempSetRoutes.get(1).getRoute().toString();
                        String[] rts = new String[2];
                        rts[0] = route1;
                        rts[1] = route2;
                        SelectRouteFragment newFragment = new SelectRouteFragment();
                        newFragment.setArray(rts);
                        Route[] arrayRoutes = new Route[2];
                        arrayRoutes[0] = tempSetRoutes.get(0).getRoute();
                        arrayRoutes[1] = tempSetRoutes.get(1).getRoute();
                        newFragment.setRoutes(arrayRoutes);
                        newFragment.show(((Activity) getContext()).getFragmentManager(), "Select Route");
                    }
                    else {
                        String route1 = tempSetRoutes.get(0).getRoute().toString();
                        String[] rts = new String[1];
                        rts[0] = route1;
                        SelectRouteFragment newFragment = new SelectRouteFragment();
                        newFragment.setArray(rts);
                        Route[] arrayRoutes = new Route[1];
                        arrayRoutes[0] = tempSetRoutes.get(0).getRoute();
                        newFragment.setRoutes(arrayRoutes);
                        newFragment.show(((Activity) getContext()).getFragmentManager(), "Select Route");
                    }
                }
                break;
        }
        return true;
    }
}

