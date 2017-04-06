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
    public ArrayList<PowerUp> powerups;
    private long startTime;
    public Platform collided;
    public PowerUp poweredUp;
    float speed = Constants.SCREEN_HEIGHT/4000.0f;

    public PlatformManager(){

        startTime = System.currentTimeMillis();

        platforms = new ArrayList<>();
        powerups = new ArrayList<>();

        platforms.add(new Platform(new Rect(500,700,550,750), Color.RED));
        platforms.add(new Platform(new Rect(1500,750,1700,800), Color.BLUE));

        powerups.add(new PowerUp(new Rect(1550,650,1600,700), Color.GREEN));
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
        for(Platform plat : platforms){
            plat.incrementX(-speed * elapsedTime);
        }
        for(PowerUp pwr : powerups){
            pwr.incrementX(-speed * elapsedTime);
        }
        if(platforms.size() > 0) {
            if (platforms.get(platforms.size() - 1).getRectangle().right <= 0) {
                platforms.remove(platforms.size() - 1);
                platforms.add(new Platform(new Rect(500,700,700,750), Color.BLACK));
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
