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
    private ArrayList<Platform> platformStorage;
    private long startTime;
    public Platform collided;
    int width;

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
        float speed = Constants.SCREEN_WIDTH/4000.0f;

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
            if (platforms.get(platforms.size() - 1).getRectangle().right <= 0){
                platforms.remove(platforms.size() - 1);
            }
        }
    }

    public void draw(Canvas canvas){
        for(Platform plat : platforms){
            plat.draw(canvas);
        }
    }
}
