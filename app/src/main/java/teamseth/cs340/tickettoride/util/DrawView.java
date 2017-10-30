package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Seth on 10/28/2017.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    View startView;
    View endView;

    public DrawView(Context context, View startView, View endView, int color) {
        super(context);
        setWillNotDraw(false);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
        paint.setStrokeWidth(15);
        this.startView = startView;
        this.endView = endView;
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawLine(startView.getX()+10, startView.getY()+15, endView.getX()+10, endView.getY(), paint);
    }
}
