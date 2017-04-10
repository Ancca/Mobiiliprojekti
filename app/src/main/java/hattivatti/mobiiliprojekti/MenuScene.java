package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by Tursake on 6.4.2017.
 */

public class MenuScene implements Scene {

    private Background bgManager;

    int action; // Toiminta (Klikkaus)

    Bitmap unscaledBackground;
    Bitmap background;
    Bitmap unscaledBackground2;
    Bitmap background2;
    Rect level1;

    public MenuScene(Context context){

        unscaledBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        bgManager = new Background(background, background2);

        level1 = new Rect(Constants.SCREEN_WIDTH/2 - 300, Constants.SCREEN_HEIGHT/2 - 100, Constants.SCREEN_WIDTH/2 - 200, Constants.SCREEN_HEIGHT/2);

        action = 1;
    }

    @Override
    public void update() {
        bgManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawColor(Color.GRAY);
        bgManager.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        //draw level select
        canvas.drawRect(level1, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("1", level1.centerX(), level1.centerY(), paint);
        paint.setTextSize(300);
        canvas.drawText("PELI",Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2 - 250, paint);
    }

    public void receiveMethod(MotionEvent event){
        action = MotionEventCompat.getActionMasked(event);
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
                if(level1.contains(x,y)){
                    SceneManager.ACTIVE_SCENE = 1;
                }
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
