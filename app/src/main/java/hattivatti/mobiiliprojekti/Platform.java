package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Valtteri on 1.4.2017.
 */

public class Platform implements GameObject {

    public boolean isPowerUp = false;
    private Rect rectangle;
    private int color;
    public int platformId;
    int platformGap;
    private Animation ground;
    private AnimationManager animManager;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementX(float x){
        rectangle.left += x;
        rectangle.right += x;
    }

    public Platform(){

    }


    public Platform(int top, int platformWidth, int platformHeight, int color, int id, int platformGap){
        this.rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - top, (Constants.SCREEN_WIDTH + platformWidth), ((Constants.SCREEN_HEIGHT - top) + platformHeight));
        this.color = color;
        this.platformId = id;
        this.platformGap = platformGap;


        BitmapFactory bf = new BitmapFactory();
        Bitmap groundImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.grass);

        ground = new Animation(new Bitmap[]{groundImg}, 2);

        animManager = new AnimationManager(new Animation[]{ground});
    }

    @Override
    public void draw(Canvas canvas) {
        /*Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);*/
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        int state = 0;
        animManager.playAnim(state);
        animManager.update();
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

    public boolean ColorTest(int col){
        if (this.color == col){
            return true;
        }
        return false;
    }
}
