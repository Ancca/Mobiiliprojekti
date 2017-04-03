package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Tursake on 3.4.2017.
 */

public class ObstacleManager {

    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private long startTime;
    public Obstacle collided;

    public ObstacleManager(){

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        obstacles.add(new Obstacle(new Rect(500,700,550,750), Color.RED));
        obstacles.add(new Obstacle(new Rect(1500,750,1700,800), Color.BLUE));
    }

    public boolean playerCollide(Player player){
        for(Obstacle ob : obstacles){
            if(ob.playerCollide(player)){
                collided = ob;
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
        for(Obstacle ob : obstacles){
            ob.incrementX(-speed * elapsedTime);
        }
        if(obstacles.size() > 0) {
            if (obstacles.get(obstacles.size() - 1).getRectangle().right <= 0) {
                obstacles.remove(obstacles.size() - 1);
                obstacles.add(new Obstacle(new Rect(500,700,550,750), Color.RED));
            }
        }
    }

    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles){
            ob.draw(canvas);
        }
    }
}
