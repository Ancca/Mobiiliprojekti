package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import java.util.ArrayList;

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
        collided = null; // Platform, johon pelaaja törmää
        goalReached = false; // True kun pelaaja pääsee maaliin

        setupPlatforms(levelNumber);
    }

    private void setupPlatforms(int levelNumber){

        Constants.CURR_LEVEL = levelNumber;

        //int top, int width, int height
        //bottom = 0 = screen bottom
        //jump height 700px, width 200px
        //1 = platform, 2 = obstacle, 3 = powerup, 4 = goal, 5 = powerup2, 6 = powerup3
        //obstacles should always be width 200, height 50

        switch (levelNumber){
            case 1:
                platformStorage.add(new Platform(25, 200, 50, 2, 0));
                platformStorage.add(new Platform(300, 600, 50, 1, 180));
                platformStorage.add(new Platform(25, 200, 50, 2, 200));
                platformStorage.add(new Platform(25, 200, 50, 2, 500));
                platformStorage.add(new Platform(300, 200, 50, 1, 0));
                platformStorage.add(new Platform(25, 200, 50, 2, 150));
                platformStorage.add(new Platform(550, 200, 50, 1, 50));
                platformStorage.add(new Platform(25, 200, 50, 2, 200));
                platformStorage.add(new Platform(300, 200, 50, 1, 0));
                platformStorage.add(new Platform(450, 200, 50, 2, 500));
                platformStorage.add(new Platform(850, 200, 50, 1, 500));
                platformStorage.add(new Platform(650, 200, 50, 2, 500));
                platformStorage.add(new Platform(Constants.SCREEN_HEIGHT, 50, Constants.SCREEN_HEIGHT * 3, 4, 500));
                break;
            case 2:
                platformStorage.add(new Platform(300, 50, 50, 3, 500));
                platformStorage.add(new Platform(300, 50, 50, 5, 500));
                platformStorage.add(new Platform(300, 50, 50, 6, 500));
                platformStorage.add(new Platform(450, 200, 50, 2, 200));
                platformStorage.add(new Platform(450, 200, 50, 2, 200));
                platformStorage.add(new Platform(450, 200, 50, 2, 200));
                platformStorage.add(new Platform(450, 200, 50, 2, 3000));
                platformStorage.add(new Platform(Constants.SCREEN_HEIGHT, 50, Constants.SCREEN_HEIGHT * 3, 4, 100));
                break;
            case 3:
                platformStorage.add(new Platform(250, 1000, 50, 1, 1000));
                platformStorage.add(new Platform(Constants.SCREEN_HEIGHT, 50, Constants.SCREEN_HEIGHT * 3, 4, 100));
                break;
            case 4:
                platformStorage.add(new Platform(250, 1000, 50, 1, 1000));
                platformStorage.add(new Platform(Constants.SCREEN_HEIGHT, 50, Constants.SCREEN_HEIGHT * 3, 4, 100));
                break;
            case 5:
                platformStorage.add(new Platform(250, 1000, 50, 1, 1000));
                platformStorage.add(new Platform(Constants.SCREEN_HEIGHT, 50, Constants.SCREEN_HEIGHT * 3, 4, 100));
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
            plat.update();
            plat.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) { // Add a platform when the earlier one has moved far enough
            if (platforms.get(0).getRectangle().left <= Constants.SCREEN_WIDTH - platforms.get(0).platformGap) {
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
