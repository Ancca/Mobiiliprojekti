package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Valtteri on 6.4.2017.
 */

public class PowerUp extends Platform {

    public boolean isPowerUp;
    private Rect rectangle;
    private int color;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementX(float x){
        rectangle.left += x;
        rectangle.right += x;
    }

    public PowerUp(int top, int platformWidth, int platformHeight, int color){
        this.rectangle = new Rect(Constants.SCREEN_WIDTH, top, (Constants.SCREEN_WIDTH + platformWidth), (top + platformHeight));
        this.color = color;
        isPowerUp = true;
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

    public int posX(){
        int x = this.rectangle.centerX();
        return x;
    }

    public int posY(){
        int y = this.rectangle.centerY();
        return y;
    }

    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public boolean powerUpColorTest(int col){
        if (this.color == col){
            return true;
        }
        return false;
    }
}
