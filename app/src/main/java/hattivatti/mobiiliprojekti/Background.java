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
    float x_layer1bg1 = 0;
    float x_layer1bg2 = Constants.SCREEN_WIDTH;
    float x_layer2bg1 = 0;
    float x_layer2bg2 = Constants.SCREEN_WIDTH;

    public Background(Bitmap background, Bitmap background2){
        this.background = background;
        this.background2 = background2;
        startTime = System.currentTimeMillis();

    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_WIDTH/4000.0f;

        /*x += -speed * elapsedTime;
        x2 += -speed * elapsedTime;*/
        x_layer1bg1 -= 15;
        x_layer1bg2 -= 15;
        x_layer2bg1 -= 5;
        x_layer2bg2 -= 5;

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
}
