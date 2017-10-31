package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

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
        if(!routeOwned) {
            paint.setColor(color);
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

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startView.getX()+10, startView.getY()+15, endView.getX()+10, endView.getY(), paint);
    }
}
