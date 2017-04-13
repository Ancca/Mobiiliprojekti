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

    Rect level1, level2, level3, level4, level5;
    Bitmap level1Img, level2Img, level3Img, level4Img, level5Img;
    private Animation level1anim, level2anim, level3anim, level4anim, level5anim;

    boolean set1 = false, set2 = false, set3 = false, set4 = false;

    public MenuScene(Context context){

        mContext = context;

        unscaledBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundsky);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        bgManager = new Background(background, background2);

        BitmapFactory bf = new BitmapFactory();
        level1Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_1);
        level2Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_2locked);
        level3Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_3locked);
        level4Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_4locked);
        level5Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_5locked);

        int widthCenter = Constants.SCREEN_WIDTH/2;
        int heightCenter = Constants.SCREEN_HEIGHT/2;

        level1 = new Rect(widthCenter - 350, heightCenter - 50, widthCenter - 250, heightCenter + 50);
        level2 = new Rect(widthCenter - 200, heightCenter - 50, widthCenter - 100, heightCenter + 50);
        level3 = new Rect(widthCenter - 50 , heightCenter - 50, widthCenter + 50 , heightCenter + 50);
        level4 = new Rect(widthCenter + 100, heightCenter - 50, widthCenter + 200, heightCenter + 50);
        level5 = new Rect(widthCenter + 250, heightCenter - 50, widthCenter + 350, heightCenter + 50);


        level1anim = new Animation(new Bitmap[]{level1Img}, 2);
        level2anim = new Animation(new Bitmap[]{level2Img}, 2);
        level3anim = new Animation(new Bitmap[]{level3Img}, 2);
        level4anim = new Animation(new Bitmap[]{level4Img}, 2);
        level5anim = new Animation(new Bitmap[]{level5Img}, 2);

        set1 = false;
        set2 = false;
        set3 = false;
        set4 = false;

        action = 1;
    }

    @Override
    public void update() {
        bgManager.update();
        level1anim.update();
        level2anim.update();
        level3anim.update();
        level4anim.update();
        level5anim.update();
    }

    @Override
    public void draw(Canvas canvas) {
        bgManager.draw(canvas);

        BitmapFactory bf = new BitmapFactory();

        if(Constants.LEVEL1_CLEARED && !set1) {
            level2Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_2);level1anim = new Animation(new Bitmap[]{level1Img}, 2);
            level2anim = new Animation(new Bitmap[]{level2Img}, 2);
            set1 = true;
        }
        if(Constants.LEVEL2_CLEARED && !set2) {
            level3Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_3);
            level3anim = new Animation(new Bitmap[]{level3Img}, 2);
            set2 = true;
        }
        if(Constants.LEVEL3_CLEARED && !set3) {
            level4Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_4);
            level4anim = new Animation(new Bitmap[]{level4Img}, 2);
            set3 = true;
        }
        if(Constants.LEVEL4_CLEARED && !set4) {
            level5Img = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.hud_5);
            level5anim = new Animation(new Bitmap[]{level5Img}, 2);
            set4 = true;
        }

        Paint paint = new Paint();
        //draw level select
        level1anim.play();
        level1anim.draw(canvas, level1);
        level2anim.play();
        level2anim.draw(canvas, level2);
        level3anim.play();
        level3anim.draw(canvas, level3);
        level4anim.play();
        level4anim.draw(canvas, level4);
        level5anim.play();
        level5anim.draw(canvas, level5);

        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
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
