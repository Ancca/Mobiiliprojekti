package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by Tursake on 3.4.2017.
 */

public class PlatformManager {

    public ArrayList<Platform> platforms;
    private ArrayList<Platform> platformStorage;
    int width;
    private long startTime;
    public Platform collided;
    public boolean poweredUp;
    public boolean goalReached;
    float pauseSpeed;
    float normalSpeed;
    int elapsedTime;
    float speed = Constants.SCREEN_HEIGHT/4000.0f;

    public PlatformManager(){
        resetLevel();
    }

    public void resetLevel(){
        width = Constants.SCREEN_WIDTH;
        startTime = System.currentTimeMillis();
        platforms = new ArrayList<>();
        platformStorage = new ArrayList<>();
        speed = 0;
        pauseSpeed = 0;
        normalSpeed = Constants.SCREEN_HEIGHT/4000.0f;
        startTime = 0;
        collided = null;
        poweredUp = false;
        goalReached = false;

        setupPlatforms();
    }

    private void setupPlatforms(){
        //top, width, height
        platformStorage.add(new Platform(700,50,50, Color.BLUE, 1));
        platformStorage.add(new Platform(850,200,50, Color.BLUE, 1));
        platformStorage.add(new Platform(650,50,50, Color.GREEN, 3));
        platformStorage.add(new Platform(450,200,50, Color.RED, 2));
        /*platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));*/
        platformStorage.add(new Platform(650,200,50, Color.RED, 2));
        platformStorage.add(new Platform(0,50,1080, Color.GREEN, 4));

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
        if(SceneManager.ACTIVE_SCENE == 1){
            speed = normalSpeed;
        } else {
            speed = pauseSpeed;
        }

        Constants.speed = speed;

        elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        for(Platform plat : platforms){
            plat.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) { // Add a platform when the earlier one has moved far enough
            if (platforms.get(0).getRectangle().left <= (Constants.SCREEN_WIDTH * 0.8f)) {
                if(platformStorage.size() > 0){
                    platforms.add(0, platformStorage.get(0));
                    platformStorage.remove(0);
                }
            }
        } else if(platforms.size() == 0 || platformStorage.size() != 0){ // Add the first platform of the level
            platforms.add(0, platformStorage.get(0));
            platformStorage.remove(0);
        }
    }

    public void draw(Canvas canvas){

        for(Platform plat : platforms){
            plat.draw(canvas);
        }
    }

    public void increaseSpeed(){
        normalSpeed = normalSpeed * 2.0f;
    }

    public void decreaseSpeed(){
        normalSpeed = normalSpeed / 2;
    }
}
