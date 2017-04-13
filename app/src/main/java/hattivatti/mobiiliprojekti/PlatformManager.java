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
    int elapsedTime;
    float speed;
    float storedSpeed;
    boolean paused = false;
    boolean speedStored = false;

    public PlatformManager(int levelNumber){
        width = Constants.SCREEN_WIDTH;
        startTime = System.currentTimeMillis();
        platforms = new ArrayList<>();
        platformStorage = new ArrayList<>();
        speed = Constants.SCREEN_HEIGHT/3000.0f;
        pauseSpeed = 0;
        startTime = 0;
        storedSpeed = speed;
        collided = null;
        poweredUp = false;
        goalReached = false;

        setupPlatforms(levelNumber);
    }

    private void setupPlatforms(int levelNumber){
        //top, width, height
        //1 = platform, 2 = obstacle, 3 = powerup, 4 = goal

        Constants.CURR_LEVEL = levelNumber;

        switch (levelNumber){
            case 1:
                platformStorage.add(new Platform(650, 50, 50, Color.GREEN, 3));
                platformStorage.add(new Platform(700, 50, 50, Color.BLACK, 3));
                platformStorage.add(new Platform(850, 200, 50, Color.BLUE, 1));
                platformStorage.add(new Platform(450, 200, 50, Color.RED, 2));
                platformStorage.add(new Platform(850, 200, 50, Color.BLUE, 1));
                platformStorage.add(new Platform(650, 200, 50, Color.RED, 2));
                platformStorage.add(new Platform(0, 50, 1080, Color.GREEN, 4));
                break;
            case 2:
                platformStorage.add(new Platform(650, 50, 50, Color.BLACK, 3));
                platformStorage.add(new Platform(650, 200, 50, Color.RED, 2));
                platformStorage.add(new Platform(0, 50, 1080, Color.GREEN, 4));
                break;
            case 3:
                platformStorage.add(new Platform(850, 200, 50, Color.BLUE, 1));
                platformStorage.add(new Platform(0, 50, 1080, Color.GREEN, 4));
                break;
            case 4:
                platformStorage.add(new Platform(650, 50, 50, Color.GREEN, 3));
                platformStorage.add(new Platform(0, 50, 1080, Color.GREEN, 4));
                break;
            case 5:
                platformStorage.add(new Platform(700, 50, 50, Color.BLUE, 1));
                platformStorage.add(new Platform(850, 200, 50, Color.BLUE, 1));
                platformStorage.add(new Platform(650, 50, 50, Color.GREEN, 3));
                platformStorage.add(new Platform(0, 50, 1080, Color.GREEN, 4));
                break;
        }

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

        if (paused){
            if(!speedStored) {
                storedSpeed = speed;
                speedStored = true;
            }
            speed = pauseSpeed;
        } else if (!paused){
            if(speedStored){
                speed = storedSpeed;
                speedStored = false;
            }
        }

        //debug speed
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
        speed = speed * 2.0f;
    }

    public void decreaseSpeed(){
        speed = speed / 2;
    }
}
