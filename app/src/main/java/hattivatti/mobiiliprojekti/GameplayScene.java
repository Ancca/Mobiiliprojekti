package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

/**
 * Created by Tursake on 6.4.2017.
 */

public class GameplayScene implements Scene {

    Player player; // Pelaaja1 (Ohjattava pelaaja)
    Player player2;// Pelaaja2 (Tarkistaa milloin pelaajan pitäisi tippua alas)
    Player player3; // Pelaaja3 (Tarkistaa milloin pelaaja on osumassa platformiin)
    private Point playerPoint;
    private Point player2Point;
    private Point player3Point;
    private PlatformManager platformManager;
    private Background bgManager;
    public boolean goalReached = false;

    int action; // Toiminta (Klikkaus)
    boolean jump = false; // True kun pelaaja hyppää
    double jumpPowerDefault = 72.5f;
    double jumpPower = jumpPowerDefault; // Pelaajan alkunopeus hypättäessä
    int powerUpSpeedTimer = 100;
    boolean powerUpSpeed = false;

    Bitmap unscaledBackground;
    Bitmap background;
    Bitmap unscaledBackground2;
    Bitmap background2;

    public GameplayScene(Context context){

        player = new Player(new Rect(100, 100, 200, 200), Color.BLACK);
        playerPoint = new Point(100, 880);
        player2 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player2Point = new Point(100, 885);
        player3 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player3Point = new Point(100, 880);

        unscaledBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.background2);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        platformManager = new PlatformManager();
        bgManager = new Background(background, background2);

        jumpPower = -1;
        jump = true;
        action = 1;

    }

    @Override
    public void update() {
        if (jump) playerMove();
        player.update(playerPoint);
        player2.update(player2Point);
        bgManager.update();
        player3.update(player3Point);
        platformManager.update();
        if (powerUpSpeed) powerUpSpeedTimer--;
        if (powerUpSpeedTimer <= 0){
            platformManager.decreaseSpeed();
            bgManager.decreaseBGSpeed();
            powerUpSpeedTimer = 100;
            powerUpSpeed = false;
        }

        if (platformManager.playerCollide(player3) && platformManager.collided != null){
            if (platformManager.collided.platformId == 1){
                System.out.println("COLLIDE");
                playerPoint.y = (int) (platformManager.collided.posY() - platformManager.collided.getHeightHalf() - player.getPlayerHeight() / 2);
                player2Point.y = playerPoint.y + 5;
                player3Point.y = playerPoint.y;
                if (action == MotionEvent.ACTION_UP) jump = false;
                jumpPower = jumpPowerDefault;
                if (powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
            }
            if (platformManager.collided.platformId == 2){

            }
            if (platformManager.collided.platformId == 3){
                System.out.println("POWERED UP");
                if (platformManager.collided.ColorTest(Color.GREEN)){
                    if (!powerUpSpeed){
                        powerUpSpeed = true;
                        platformManager.increaseSpeed();
                        bgManager.inceaseBGSpeed();
                    }
                    if (powerUpSpeed){
                        powerUpSpeedTimer = powerUpSpeedTimer + 100;
                    }
                    platformManager.platforms.remove(platformManager.collided);
                }
            }
            if (platformManager.collided.platformId == 4){
                System.out.println("GOAL");
                platformManager.speed = 0;
                bgManager.bgspeed = 0;
                jumpPower = 0;
                goalReached = true;
            }
            else {
                System.out.println("???");
            }
        }
        else if (platformManager.playerCollide(player2) != true && (!jump) && player.playerPosY() < 1030) {
            System.out.println("FALL");
            jump = true;
            jumpPower = 0.0f;
        }
    }


    @Override
    public void draw(Canvas canvas) {
        bgManager.draw(canvas);
        player.draw(canvas);
        platformManager.draw(canvas);
        //player2.draw(canvas); // Pelaajia 2 ja 3 ei piirretä ollenkaan
        //player3.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        if (powerUpSpeed){
            canvas.drawRect(1500,50,1500+powerUpSpeedTimer*2,75,paint);
        }
        if(goalReached){
            paint.setTextSize(300);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("GOAL!",Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2, paint);
            Constants.LEVEL1_CLEARED = true;
            terminate();
        }
    }

    public void receiveMethod(MotionEvent event){
        action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            if(!goalReached) {
                jump = true;
            }
        }
    }

    public void playerMove() {
        playerPoint.y = (int) player.playerPosY() - (int) jumpPower;
        player2Point.y = playerPoint.y + 5;
        jumpPower -= 5.5f;
        if (powerUpSpeed) jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        if (jumpPower < 0 && powerUpSpeed) jumpPower -= 3.5f;
        player3Point.y = playerPoint.y - (int) jumpPower;
        // Pidetään pelaaja ruudulla, kun se tippuu maahan
        if (playerPoint.y > Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2)) {
            jump = false;
            jumpPower = jumpPowerDefault;
            playerPoint.y = (int) (Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2));
            player2Point.y = playerPoint.y + 5;
            player3Point.y = playerPoint.y;
            if (action == MotionEvent.ACTION_DOWN) jump = true;
            if (powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
