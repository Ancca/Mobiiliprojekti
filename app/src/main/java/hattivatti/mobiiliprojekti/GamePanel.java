package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Tursake on 30.3.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;

    private Player player;
    private Point playerPoint;
    private Platform platform;
    private Point platformPoint;
    private Ground ground;
    private Obstacle obstacle;
    private Point obstaclePoint;

    int action;
    boolean jump = false; // True kun pelaaja hyppää
    double jumpPowerDefault = 70.0f;
    double jumpPower = jumpPowerDefault;
    double obstacleSpeedDefault = 10.0f;
    double obstacleSpeed = obstacleSpeedDefault;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        ground = new Ground(new Rect(0,980,200,1180), Color.BLACK);
        player = new Player(new Rect(-100,-100,0,0), Color.BLACK);
        playerPoint = new Point(100,880);
        platform = new Platform(new Rect(-200,-50,0,0), Color.BLACK);
        platformPoint = new Point(1000,650);
        obstacle = new Obstacle(new Rect(-100,-100,0,0), Color.RED);
        obstaclePoint = new Point(1300,1030);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
        jumpPower = -1;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            jump = true;
        }
        return true;
    }

    public void update(){
        if (jump) playerMove();
        playerFallTest();
        moveObstacles();
        player.update(playerPoint);
        platform.update(platformPoint);
        obstacle.update(obstaclePoint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
        ground.draw(canvas);
        platform.draw(canvas);
        obstacle.draw(canvas);
    }

    public void playerMove() {
        playerPoint.set((int)player.playerPosX(),(int)player.playerPosY()-(int)jumpPower);
        jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        playerHitboxTest();
    }

    public void moveObstacles() {
        obstaclePoint.set((int)obstacle.obstaclePosX()-(int)obstacleSpeed, (int)obstacle.obstaclePosY());
        if (obstacle.obstaclePosX() < -50) obstaclePoint.set(1920,1030);
        platformPoint.set((int)platform.platformPosX()-(int)obstacleSpeed, (int)platform.platformPosY());
        if (platform.platformPosX() < -100) platformPoint.set(600,600);
    }

    public void playerHitboxTest() {
        if (player.playerPosY()+(player.getPlayerHeight()/2) + 1 > ground.groundPosY()-(ground.getGroundHeight()/2) && player.playerPosY()+(player.getPlayerHeight()/2) + 1 < ground.groundPosY()+(ground.getGroundHeight()/2)){
            playerPoint.set((int)player.playerPosX(),(int)(ground.groundPosY()-ground.getGroundHeight()+48));
            if (action == MotionEvent.ACTION_UP) jump = false;
            jumpPower = jumpPowerDefault;
        }
        if (player.playerPosY()+(player.getPlayerHeight()/2) + 1 > platform.platformPosY()-(platform.getPlatformHeight()/2) && player.playerPosY()+(player.getPlayerHeight()/2) + 1 < platform.platformPosY()+(platform.getPlatformHeight()/2)){
            if (player.playerPosX()+player.getPlayerWidth()/2 > platform.platformPosX()-(platform.getPlatformWidth()/2) && player.playerPosX()-player.getPlayerWidth()/2 < platform.platformPosX()+(platform.getPlatformWidth()/2)){
                playerPoint.set((int)player.playerPosX(),(int)(platform.platformPosY()-platform.getPlatformHeight()-29));
                if (action == MotionEvent.ACTION_UP) jump = false;
                jumpPower = jumpPowerDefault;
            }
        }
    }

    public void playerFallTest() {
        if (player.playerPosY() < platform.platformPosY()){
            if (player.playerPosX()+player.getPlayerWidth()/2 < platform.platformPosX()-platform.getPlatformWidth()/2 && !jump || player.playerPosX()-player.getPlayerWidth()/2 > platform.platformPosX()+platform.getPlatformWidth()/2 && !jump){
                jump = true;
                jumpPower = -1.0f;
            }
        }
    }
}
