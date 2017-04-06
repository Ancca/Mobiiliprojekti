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

    public ArrayList<Platform> platforms;
    private ArrayList<Platform> platformStorage;
    int width;
    private long startTime;
    public Platform collided;
    public PowerUp collidedpw;
    public boolean poweredUp;
    float speed = Constants.SCREEN_HEIGHT/4000.0f;

    public PlatformManager(){

        width = Constants.SCREEN_WIDTH;
        startTime = System.currentTimeMillis();
        platforms = new ArrayList<>();
        platformStorage = new ArrayList<>();

        setupPlatforms();

    }

    private void setupPlatforms(){
        //top, width, height
        //add first platform to platforms and rest to storage
        platforms.add(new Platform(700,50,50, Color.BLUE));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new PowerUp(650,50,50, Color.GREEN));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(850,200,50, Color.BLUE));
        platformStorage.add(new Obstacle(450,200,50, Color.RED));
        platformStorage.add(new Platform(0,50,Constants.SCREEN_WIDTH, Color.GREEN));

    }

    public boolean playerCollide(Player player){
        for(Platform plat : platforms){
            if(plat.playerCollide(player)){
                if(plat instanceof PowerUp) {
                    poweredUp = true;
                    collidedpw = (PowerUp)plat;
                } else {
                    collided = plat;
                }
                return true;
            }
        }
        collided = null;
        collidedpw = null;
        poweredUp = false;
        return false;
    }

    public void update(){
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        for(Platform plat : platforms){
            plat.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) {
            if (platforms.get(0).getRectangle().left <= (Constants.SCREEN_WIDTH * 0.8f)) {
                if(platformStorage.size() > 0){
                    platforms.add(0, platformStorage.get(0));
                    platformStorage.remove(0);
                }
            }
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
