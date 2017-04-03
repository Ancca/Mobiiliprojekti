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

    public Platform(Rect rectangle, int color){
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

    public double platformPosX(){
        double x = this.rectangle.exactCenterX();
        return x;
    }

    public double platformPosY(){
        double y = this.rectangle.exactCenterY();
        return y;
    }

    public int getPlatformHeight(){
        int height = this.rectangle.height();
        return height;
    }

    public int getPlatformWidth(){
        int width = this.rectangle.width();
        return width;
    }

    public int playerCollide(Player player){
        int luku = 0;
        if (rectangle.contains(player.getRectangle().left,player.getRectangle().top)
                || rectangle.contains(player.getRectangle().right,player.getRectangle().top)){
            luku = 2;
        }
        else if (rectangle.contains(player.getRectangle().left,player.getRectangle().bottom)
                || rectangle.contains(player.getRectangle().right,player.getRectangle().bottom)){
            luku = 1;
        }
        return luku;
    }
}
