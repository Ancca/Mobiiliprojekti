package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Tursake on 30.3.2017.
 */

public class Player implements GameObject{

    public Rect rectangle;
    private int color;

    public Player(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;
    }

    public Rect getRectangle(){
        return rectangle;
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

    public double playerPosX(){
        double x = this.rectangle.centerX();
        return x;
    }

    public double playerPosY(){
        double y = this.rectangle.centerY();
        return y;
    }

    public double getPlayerHeight(){
        double height = this.rectangle.height();
        return height;
    }

    public double getPlayerWidth(){
        double width = this.rectangle.width();
        return width;
    }
}
