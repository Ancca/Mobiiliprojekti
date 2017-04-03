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

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementX(float x){
        rectangle.left += x;
        rectangle.right += x;
    }

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

    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public int getObstacleHeightHalf(){
        int widthHalf = this.rectangle.height() / 2;
        return widthHalf;
    }

    public int getObstacleWidthHalf(){
        int widthHalf = this.rectangle.width() / 2;
        return widthHalf;
    }
}
