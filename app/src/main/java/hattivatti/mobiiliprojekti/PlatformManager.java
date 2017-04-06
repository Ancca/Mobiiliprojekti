package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Tursake on 3.4.2017.
 */

public class PlatformManager {

    private ArrayList<Platform> platforms;
<<<<<<< HEAD
    private ArrayList<Platform> platformStorage;
    private long startTime;
    public Platform collided;
    int width;
=======
    public ArrayList<PowerUp> powerups;
    private long startTime;
    public Platform collided;
    public PowerUp poweredUp;
    float speed = Constants.SCREEN_HEIGHT/4000.0f;
>>>>>>> origin/master

    public PlatformManager(){

        width = Constants.SCREEN_WIDTH;
        startTime = System.currentTimeMillis();
        platforms = new ArrayList<>();
<<<<<<< HEAD
        platformStorage = new ArrayList<>();

        setupPlatforms();

    }

    private void setupPlatforms(){
        //top, width, height
        //add first platform to platforms and rest to storage
        platforms.add(new Platform(700,50,50, Color.BLUE));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(0,50,Constants.SCREEN_WIDTH, Color.GREEN));
=======
        powerups = new ArrayList<>();

        platforms.add(new Platform(new Rect(500,700,550,750), Color.RED));
        platforms.add(new Platform(new Rect(1500,750,1700,800), Color.BLUE));

        powerups.add(new PowerUp(new Rect(1550,650,1600,700), Color.GREEN));
>>>>>>> origin/master
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

    public boolean playerPowerUp(Player player){
        for(PowerUp pwr : powerups){
            if(pwr.playerCollide(player)){
                poweredUp = pwr;
                return true;
            }
        }
        poweredUp = null;
        return false;
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
<<<<<<< HEAD
        float speed = Constants.SCREEN_WIDTH/4000.0f;

=======
>>>>>>> origin/master
        for(Platform plat : platforms){
            plat.incrementX(-speed * elapsedTime);
        }
        for(PowerUp pwr : powerups){
            pwr.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) {
            if (platforms.get(0).getRectangle().left <= (Constants.SCREEN_WIDTH * 0.8f)) {
                if(platformStorage.size() > 0){
                    platforms.add(0, platformStorage.get(0));
                    platformStorage.remove(0);
                }
            }
            if (platforms.get(platforms.size() - 1).getRectangle().right <= 0){
                platforms.remove(platforms.size() - 1);
<<<<<<< HEAD
=======
                platforms.add(new Platform(new Rect(500,700,700,750), Color.BLACK));
>>>>>>> origin/master
            }
        }
        if(powerups.size() > 0) {
            if (powerups.get(powerups.size() - 1).getRectangle().right <= 0) {
                powerups.remove(powerups.size() - 1);
                powerups.add(new PowerUp(new Rect(1550,650,1600,700), Color.GREEN));
            }
        }
        if(powerups.size() == 0) {
            powerups.add(new PowerUp(new Rect(1550,650,1600,700), Color.GREEN));
        }
    }

    public void draw(Canvas canvas){
        for(Platform plat : platforms){
            plat.draw(canvas);
        }
        for(PowerUp pwr : powerups){
            pwr.draw(canvas);
        }
    }

    public void increaseSpeed(){
        speed = speed * 2.0f;
    }

    public void decreaseSpeed(){
        speed = speed / 2;
    }
}
