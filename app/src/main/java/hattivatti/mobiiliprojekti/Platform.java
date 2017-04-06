package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Valtteri on 1.4.2017.
 */

public class Platform implements GameObject {

    private Rect rectangle;
    private int color;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementX(float x){
        rectangle.left += x;
        rectangle.right += x;
    }

    public Platform(){

    }

    public Platform(int top, int platformWidth, int platformHeight, int color){
        this.rectangle = new Rect(Constants.SCREEN_WIDTH, top, (Constants.SCREEN_WIDTH + platformWidth), (top + platformHeight));
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

    public int getHeightHalf(){
        int heigthHalf = this.rectangle.height() / 2;
        return heigthHalf;
    }

    public int getWidthHalf(){
        int widthHalf = this.rectangle.width() / 2;
        return widthHalf;
    }

    public int getWidth(){
        int widthHalf = this.rectangle.width() / 2;
        return widthHalf;
    }
}
