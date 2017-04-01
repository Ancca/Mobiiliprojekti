package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Valtteri on 1.4.2017.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private int color;

    public Obstacle(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point){
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2,
                point.x + rectangle.width()/2, point.y + rectangle.height()/2);
    }

    public double obstaclePosX(){
        int x = this.rectangle.centerX();
        return x;
    }

    public double obstaclePosY(){
        int y = this.rectangle.centerY();
        return y;
    }

    public int getObstacleHeight(){
        int height = this.rectangle.height();
        return height;
    }
}
