package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

/**
 * Created by Tursake on 6.4.2017.
 */

public class SceneManager {
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(Context context){
        ACTIVE_SCENE = 0;
        scenes.add(new MenuScene(context));
        scenes.add(new GameplayScene(context));
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).receiveMethod(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
