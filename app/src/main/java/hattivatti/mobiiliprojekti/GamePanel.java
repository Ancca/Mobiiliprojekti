package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.support.v4.view.MotionEventCompat;

/**
 * Created by Tursake on 30.3.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;

    Player player;
    private Point playerPoint;
    private Platform platform;
    private Point platformPoint;
    private Obstacle obstacle;
    private Point obstaclePoint;
    private PlatformManager platformManager;

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
        player = new Player(new Rect(-100,-100,0,0), Color.BLACK);
        playerPoint = new Point(100,880);
        platform = new Platform(new Rect(-200,-50,0,0), Color.BLACK);
        platformPoint = new Point(1000,650);
        obstacle = new Obstacle(new Rect(-100,-100,0,0), Color.RED);
        obstaclePoint = new Point(1300,1030);

        platformManager = new PlatformManager();

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

        if(platformManager.playerCollide(player)){
            System.out.println("COLLIDE");
            playerPoint.set((int)player.playerPosX(),(int)(platformManager.collided.posY()- platformManager.collided.getHeightHalf()));
            jump = false;
            jumpPower = jumpPowerDefault;
        }

        if (jump) playerMove();
        if (!jump) playerFall();
        moveObstacles();
        System.out.println(Constants.SCREEN_HEIGHT - (player.getPlayerHeight()/2));
        player.update(playerPoint);
        platform.update(platformPoint);
        obstacle.update(obstaclePoint);
        platformManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
        platform.draw(canvas);
        obstacle.draw(canvas);
        platformManager.draw(canvas);
    }

    public void playerMove() {
        playerPoint.set((int)player.playerPosX(),(int)player.playerPosY()-(int)jumpPower);
        //keep player on the screen when landing
        if(playerPoint.y >= Constants.SCREEN_HEIGHT - (player.getPlayerHeight()/2)){
            if(jump){
                playerPoint.y = (int)(Constants.SCREEN_HEIGHT - (player.getPlayerHeight()/2));
                jump = false;
                jumpPower = jumpPowerDefault;
            }
        }
        jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        playerHitboxTest();
    }

    public void moveObstacles() {
        obstaclePoint.set((int)obstacle.posX()-(int)obstacleSpeed, (int)obstacle.posY());
        if (obstacle.posX() < -obstacle.getWidthHalf()) obstaclePoint.set(1920,1030);
        platformPoint.set((int)platform.posX()-(int)obstacleSpeed, (int)platform.posY());
        if (platform.posX() < -100) platformPoint.set(600,600);
    }

    public void playerHitboxTest() {
        if (platform.playerCollide(player)){
            playerPoint.set((int) player.playerPosX(), (int) (platform.posY()-platform.getHeightHalf()-player.getPlayerHeight()/2-1));
            jumpPower = jumpPowerDefault;
            if (action == MotionEvent.ACTION_UP){
                jump = false;
                playerOnPlatform = true;
            }
        }
        else if (platform.playerCollide(player)){
            playerPoint.set((int) player.playerPosX(), (int) (platform.posY()+platform.getHeightHalf()+player.getPlayerHeight()/2+1));
            jumpPower = - 10.0f;
        }
    }

    public void playerFall(){
        if (player.playerPosY() < platform.posY()){
            if (player.playerPosX()-player.getPlayerWidth()/2 > platform.posX()+platform.getWidth()/2) {
                jump = true;
                jumpPower = -2.0f;
            }
        }
    }
}
