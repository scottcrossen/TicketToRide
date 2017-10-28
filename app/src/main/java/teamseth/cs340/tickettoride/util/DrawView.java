package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        this.startView = startView;
        this.endView = endView;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawLine(startView.getX()+25, startView.getY()+50, endView.getX()+25, endView.getY(), paint);
    }

}
