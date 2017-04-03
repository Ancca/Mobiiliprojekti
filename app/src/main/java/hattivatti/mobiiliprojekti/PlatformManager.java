package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Tursake on 3.4.2017.
 */

public class PlatformManager {

    //higher index = lower on screen = higher y value
    private ArrayList<Platform> platforms;
    private long startTime;
    public Platform collided;

    public PlatformManager(){

        startTime = System.currentTimeMillis();

        platforms = new ArrayList<>();

        platforms.add(new Platform(new Rect(500,700,550,750), Color.RED));
        platforms.add(new Platform(new Rect(1500,750,1700,800), Color.BLUE));
    }

    public boolean playerCollide(Player player){
        for(Platform plat : platforms){
            if(plat.playerCollide(player)){
                collided = plat;
                return true;
            }
        }
        collided = null;
        return false;
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT/4000.0f;
        for(Platform plat : platforms){
            plat.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) {
            if (platforms.get(platforms.size() - 1).getRectangle().right <= 0) {
                platforms.remove(platforms.size() - 1);
                platforms.add(new Platform(new Rect(500,700,550,750), Color.RED));
            }
        }
    }

    public void draw(Canvas canvas){
        for(Platform plat : platforms){
            plat.draw(canvas);
        }
    }
}
