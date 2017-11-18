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

/**
 * Created by Seth on 10/28/2017.
 */

public class MapView extends View {
    private Paint paint = new Paint();
    private Set<DrawView> routeNames;
    private Boolean routeOwned = false;
    private Map<String, RectF> rectFList = new HashMap<>();
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
            if(!routeOwned) {
                // only draws dashed line if route is unclaimed
                paint.setPathEffect(new DashPathEffect(new float[] {80,20}, 0));
            }
            else {
                //draw solid line if route is claimed
                paint.setPathEffect(new DashPathEffect(new float[] {80,0}, 0));
            }
            linePath.moveTo(dr.getStartView().getX() + dr.getOffsetXstart(),
                    dr.getStartView().getY() + dr.getOffsetYstart());
            linePath.lineTo(dr.getEndView().getX() + dr.getOffsetXend(),
                    dr.getEndView().getY() + dr.getOffsetYend());

            canvas.drawPath(linePath, paint);
            Path rectLine = new Path();

            float centerOnX = ( dr.getStartView().getX() + dr.getOffsetXstart()
                    + dr.getEndView().getX() + dr.getOffsetXend() ) / 2;
            float centerOnY = ( dr.getStartView().getY() + dr.getOffsetYstart()
                    + dr.getEndView().getY() + dr.getOffsetYend() ) / 2;
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

