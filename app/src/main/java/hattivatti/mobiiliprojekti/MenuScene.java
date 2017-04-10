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
import android.widget.Toast;

/**
 * Created by Tursake on 6.4.2017.
 */

public class MenuScene implements Scene {

    private Background bgManager;
    Context mContext;

    int action; // Toiminta (Klikkaus)

    Bitmap unscaledBackground;
    Bitmap background;
    Bitmap unscaledBackground2;
    Bitmap background2;
    Rect level1;
    Rect level2;
    Rect level3;
    Rect level4;
    Rect level5;

    public MenuScene(Context context){

        mContext = context;

        unscaledBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundsky);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        bgManager = new Background(background, background2);

        int widthCenter = Constants.SCREEN_WIDTH/2;
        int heightCenter = Constants.SCREEN_HEIGHT/2;

        level1 = new Rect(widthCenter - 350, heightCenter - 50, widthCenter - 250, heightCenter + 50);
        level2 = new Rect(widthCenter - 200, heightCenter - 50, widthCenter - 100, heightCenter + 50);
        level3 = new Rect(widthCenter - 50 , heightCenter - 50, widthCenter + 50 , heightCenter + 50);
        level4 = new Rect(widthCenter + 100, heightCenter - 50, widthCenter + 200, heightCenter + 50);
        level5 = new Rect(widthCenter + 250, heightCenter - 50, widthCenter + 350, heightCenter + 50);

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
        //draw level select
        paint.setColor(Color.GREEN);
        canvas.drawRect(level1, paint);
        if(!Constants.LEVEL1_CLEARED){
            paint.setColor(Color.GRAY);
        }
        canvas.drawRect(level2, paint);
        if(!Constants.LEVEL2_CLEARED){
            paint.setColor(Color.GRAY);
        }
        canvas.drawRect(level3, paint);
        if(!Constants.LEVEL3_CLEARED){
            paint.setColor(Color.GRAY);
        }
        canvas.drawRect(level4, paint);
        if(!Constants.LEVEL4_CLEARED){
            paint.setColor(Color.GRAY);
        }
        canvas.drawRect(level5, paint);
        if(!Constants.LEVEL5_CLEARED){
            paint.setColor(Color.GRAY);
        }

        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("1", level1.centerX(), level1.centerY(), paint);
        canvas.drawText("2", level2.centerX(), level2.centerY(), paint);
        canvas.drawText("3", level3.centerX(), level3.centerY(), paint);
        canvas.drawText("4", level4.centerX(), level4.centerY(), paint);
        canvas.drawText("5", level5.centerX(), level5.centerY(), paint);
        paint.setTextSize(300);
        canvas.drawText("PELI",Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2 - 250, paint);
    }

    public void receiveMethod(MotionEvent event){
        action = MotionEventCompat.getActionMasked(event);
        int x = (int)event.getX();
        int y = (int)event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
                // Check which level to load
                if(level1.contains(x,y)){
                    SceneManager.scenes.remove(1);
                    SceneManager.scenes.add(new GameplayScene(mContext, 1));
                    SceneManager.ACTIVE_SCENE = 1;
                } else if(level2.contains(x,y)){
                    if(Constants.LEVEL1_CLEARED){
                        SceneManager.scenes.remove(1);
                        SceneManager.scenes.add(new GameplayScene(mContext, 2));
                        SceneManager.ACTIVE_SCENE = 1;
                    } else { Toast.makeText(mContext, "LEVEL NOT UNLOCKED!", Toast.LENGTH_SHORT).show(); }
                } else if(level3.contains(x,y)){
                    if(Constants.LEVEL2_CLEARED){
                        SceneManager.scenes.remove(1);
                        SceneManager.scenes.add(new GameplayScene(mContext, 3));
                        SceneManager.ACTIVE_SCENE = 1;
                    } else { Toast.makeText(mContext, "LEVEL NOT UNLOCKED!", Toast.LENGTH_SHORT).show(); }
                } else if(level4.contains(x,y)){
                    if(Constants.LEVEL3_CLEARED){
                        SceneManager.scenes.remove(1);
                        SceneManager.scenes.add(new GameplayScene(mContext, 4));
                        SceneManager.ACTIVE_SCENE = 1;
                    } else { Toast.makeText(mContext, "LEVEL NOT UNLOCKED!", Toast.LENGTH_SHORT).show(); }
                } else if(level5.contains(x,y)){
                    if(Constants.LEVEL4_CLEARED){
                        SceneManager.scenes.remove(1);
                        SceneManager.scenes.add(new GameplayScene(mContext, 5));
                        SceneManager.ACTIVE_SCENE = 1;
                    } else { Toast.makeText(mContext, "LEVEL NOT UNLOCKED!", Toast.LENGTH_SHORT).show(); }
                }
        }
    }

    @Override
    public void endScene() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
