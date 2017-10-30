package teamseth.cs340.tickettoride.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

/**
 * Created by Seth on 10/28/2017.
 */

public class DrawView extends View {
    Paint paint = new Paint();
    Path path = new Path();
    View startView;
    View endView;

    public DrawView(Context context, View startView, View endView, int color) {
        super(context);
        setWillNotDraw(false);
        paint.setColor(color);
        //paint.setStyle(Paint.Style.STROKE);
        //paint.setPathEffect(new DashPathEffect(new float[] {50,50}, 0));
        //paint.setStrokeMiter(50);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        float[] intervals = new float[]{100.0f, 100.0f};
        float phase = 0;

        DashPathEffect dashPathEffect =
                new DashPathEffect(intervals, phase);

        paint.setPathEffect(dashPathEffect);
        //paint.setPathEffect(new DashPathEffect(new float[] {40,40,40,40}, 0));
        this.startView = startView;
        this.endView = endView;
        path.moveTo(startView.getX()+10, startView.getY()+15);
        path.lineTo(endView.getX()+10, endView.getY());
        //this.setBackground(getResources().getDrawable(R.drawable.dashed_line));
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }
}
