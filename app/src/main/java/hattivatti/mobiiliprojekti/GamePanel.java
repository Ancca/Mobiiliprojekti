package hattivatti.mobiiliprojekti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.support.v4.view.MotionEventCompat;

/**
 * Created by Tursake on 30.3.2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    Player player; // Pelaaja1 (Ohjattava pelaaja)
    Player player2;// Pelaaja2 (Tarkistaa milloin pelaajan pitäisi tippua alas)
    Player player3; // Pelaaja3 (Tarkistaa milloin pelaaja on osumassa platformiin)
    private Point playerPoint;
    private Point player2Point;
    private Point player3Point;
    private PlatformManager platformManager;
    private Background bgManager;

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

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        player = new Player(new Rect(100, 100, 200, 200), Color.BLACK);
        playerPoint = new Point(100, 880);
        player2 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player2Point = new Point(100, 885);
        player3 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player3Point = new Point(100, 880);

        unscaledBackground = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(getResources(), R.drawable.background2);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        platformManager = new PlatformManager();
        bgManager = new Background(background, background2);

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
        jump = true;
        action = 1;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
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

    public void update() {
        if (jump) playerMove();
        player.update(playerPoint);
        player2.update(player2Point);
<<<<<<< HEAD
        bgManager.update();
=======
        player3.update(player3Point);
>>>>>>> origin/master
        platformManager.update();
        if (powerUpSpeed) powerUpSpeedTimer--;
        if (powerUpSpeedTimer <= 0){
            platformManager.decreaseSpeed();
            powerUpSpeedTimer = 100;
            powerUpSpeed = false;
        }

        if (platformManager.playerCollide(player3)) {
            System.out.println("COLLIDE");
            playerPoint.y = (int) (platformManager.collided.posY() - platformManager.collided.getHeightHalf() - player.getPlayerHeight() / 2);
            player2Point.y = playerPoint.y + 5;
            player3Point.y = playerPoint.y;
            if (action == MotionEvent.ACTION_UP) jump = false;
            jumpPower = jumpPowerDefault;
            if (powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
        }
        else if (platformManager.playerCollide(player2) != true && (!jump) && player.playerPosY() < 1030) {
            System.out.println("FALL");
            jump = true;
            jumpPower = 0.0f;
        }
        if (platformManager.playerPowerUp(player)) {
            System.out.println("POWERED UP");
            if (platformManager.poweredUp.powerUpColorTest(Color.GREEN)){
                if (!powerUpSpeed){
                    powerUpSpeed = true;
                    platformManager.increaseSpeed();
                }
                if (powerUpSpeed){
                    powerUpSpeedTimer = powerUpSpeedTimer + 100;
                }
                platformManager.powerups.remove(platformManager.powerups.size() - 1);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //canvas.drawColor(Color.GRAY);
        bgManager.draw(canvas);
        player.draw(canvas);
        platformManager.draw(canvas);
        //player2.draw(canvas); // Pelaajia 2 ja 3 ei piirretä ollenkaan
        //player3.draw(canvas);
    }

    public void playerMove() {
        jumpPower -= 5.5f;
        if (powerUpSpeed) jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        if (jumpPower < 0 && powerUpSpeed) jumpPower -= 3.5f;
        playerPoint.y = (int) player.playerPosY() - (int) jumpPower;
        player2Point.y = playerPoint.y + 5;
        player3Point.y = playerPoint.y - (int) jumpPower;
        // Pidetään pelaaja ruudulla, kun se tippuu alas
        if (playerPoint.y > Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2)) {
            jump = false;
            playerPoint.y = (int) (Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2));
            player2Point.y = playerPoint.y + 5;
            player3Point.y = playerPoint.y;
            if (action == MotionEvent.ACTION_DOWN) jump = true;
            jumpPower = jumpPowerDefault;
            if (powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
        }
    }
}
