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

    Player player;
    private Point playerPoint;
    private Platform platform;
    private Point platformPoint;
    private Ground ground;
    private Obstacle obstacle;
    private Point obstaclePoint;
    private ObstacleManager obstacleManager;

    int action;
    boolean jump = false; // True kun pelaaja hyppää
    boolean playerOnPlatform;
    double jumpPowerDefault = 72.5f;
    double jumpPower = jumpPowerDefault;
    double obstacleSpeedDefault = 10.0f;
    double obstacleSpeed = obstacleSpeedDefault;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        ground = new Ground(new Rect(0,1080,200,1280), Color.BLACK);
        player = new Player(new Rect(-100,-100,0,0), Color.BLACK);
        playerPoint = new Point(100,880);
        platform = new Platform(new Rect(-200,-50,0,0), Color.BLACK);
        platformPoint = new Point(1000,650);
        obstacle = new Obstacle(new Rect(-100,-100,0,0), Color.RED);
        obstaclePoint = new Point(1300,1030);

        obstacleManager = new ObstacleManager();

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

        if(obstacleManager.playerCollide(player)){
            System.out.println("COLLIDE");
            playerPoint.set((int)player.playerPosX(),(int)(obstacleManager.collided.obstaclePosY()-obstacleManager.collided.getObstacleHeightHalf()));
            jump = false;
            jumpPower = jumpPowerDefault;
        }

        if (jump) playerMove();
        if (!jump) playerFall();
        moveObstacles();
        player.update(playerPoint);
        platform.update(platformPoint);
        obstacle.update(obstaclePoint);
        obstacleManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
        ground.draw(canvas);
        platform.draw(canvas);
        obstacle.draw(canvas);
        obstacleManager.draw(canvas);
    }

    public void playerMove() {
        playerPoint.set((int)player.playerPosX(),(int)player.playerPosY()-(int)jumpPower);
        jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        playerHitboxTest();
    }

    public void moveObstacles() {
        obstaclePoint.set((int)obstacle.obstaclePosX()-(int)obstacleSpeed, (int)obstacle.obstaclePosY());
        if (obstacle.obstaclePosX() < -obstacle.getObstacleWidthHalf()) obstaclePoint.set(1920,1030);
        platformPoint.set((int)platform.platformPosX()-(int)obstacleSpeed, (int)platform.platformPosY());
        if (platform.platformPosX() < -100) platformPoint.set(600,600);
    }

    public void playerHitboxTest() {
        if (platform.playerCollide(player) == 1){
            playerPoint.set((int) player.playerPosX(), (int) (platform.platformPosY()-platform.getPlatformHeight()/2-player.getPlayerHeight()/2-1));
            jumpPower = jumpPowerDefault;
            if (action == MotionEvent.ACTION_UP){
                jump = false;
                playerOnPlatform = true;
            }
        }
        else if (platform.playerCollide(player) == 2){
            playerPoint.set((int) player.playerPosX(), (int) (platform.platformPosY()+platform.getPlatformHeight()/2+player.getPlayerHeight()/2+1));
            jumpPower = - 10.0f;
        }

        if (ground.playerCollide(player)){
            playerPoint.set((int) player.playerPosX(), (int) (ground.groundPosY()-ground.getGroundHeight()/2-player.getPlayerHeight()/2-1));
            jumpPower = jumpPowerDefault;
            if (action == MotionEvent.ACTION_UP){
                jump = false;
            }
        }
    }

    public void playerFall(){
        if (player.playerPosY() < platform.platformPosY()){
            if (player.playerPosX()-player.getPlayerWidth()/2 > platform.platformPosX()+platform.getPlatformWidth()/2) {
                jump = true;
                jumpPower = -2.0f;
            }
        }
    }
}
