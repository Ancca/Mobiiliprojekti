package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Tursake on 6.4.2017.
 */

public class Background {

    private long startTime;
    Bitmap background;
    Bitmap background2;
    public int bgspeed = 5;
    float x_layer1bg1 = 0;
    float x_layer1bg2 = Constants.SCREEN_WIDTH;
    float x_layer2bg1 = 0;
    float x_layer2bg2 = Constants.SCREEN_WIDTH;
    boolean paused = false;
    int storedSpeed = 0;

    public Background(Bitmap background, Bitmap background2){
        this.background = background;
        this.background2 = background2;
        startTime = System.currentTimeMillis();
        storedSpeed = bgspeed;

    }

    public void update(){
        
        if(paused){
            bgspeed = 0;
        } else {
            bgspeed = storedSpeed;
            storedSpeed = bgspeed;
        }

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_WIDTH/4000.0f;

        /*x += -speed * elapsedTime;
        x2 += -speed * elapsedTime;*/
        x_layer1bg1 -= bgspeed * 3;
        x_layer1bg2 -= bgspeed * 3;
        x_layer2bg1 -= bgspeed;
        x_layer2bg2 -= bgspeed;

        if(x_layer1bg1 <= -Constants.SCREEN_WIDTH){
            x_layer1bg1 = Constants.SCREEN_WIDTH;
        }
        if(x_layer1bg2 <= -Constants.SCREEN_WIDTH){
            x_layer1bg2 = Constants.SCREEN_WIDTH;
        }

        if(x_layer2bg1 <= -Constants.SCREEN_WIDTH){
            x_layer2bg1 = Constants.SCREEN_WIDTH;
        }
        if(x_layer2bg2 <= -Constants.SCREEN_WIDTH){
            x_layer2bg2 = Constants.SCREEN_WIDTH;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background,x_layer1bg1,0,null);
        canvas.drawBitmap(background,x_layer1bg2,0,null);
        canvas.drawBitmap(background2,x_layer2bg1,0,null);
        canvas.drawBitmap(background2,x_layer2bg2,0,null);
    }

    public void inceaseBGSpeed(){
        bgspeed = 5;
    }

    public void decreaseBGSpeed(){
        bgspeed = 5;
    }
}
