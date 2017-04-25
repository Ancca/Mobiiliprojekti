package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Tursake on 6.4.2017.
 */

public class SceneManager {
    public static ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;
    Context mContext;

    public SceneManager(Context context){
        ACTIVE_SCENE = 0;
        mContext = context;
        scenes.add(new MenuScene(context));
        scenes.add(new GameplayScene(context, 1));
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).receiveMethod(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);

        //DEBUG
        /*Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("FPS " + Double.toString(Constants.FPS), 50, 50, paint);
        canvas.drawText("Elapsed " + Integer.toString(Constants.startCounter), 50, 100, paint);
        canvas.drawText("Speed " + Float.toString(Constants.speed), 50, 150, paint);*/
    }
}
