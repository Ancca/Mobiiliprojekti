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
    double jumpPowerDefault = 72.5f;
    double jumpPower = jumpPowerDefault; // Pelaajan alkunopeus hypättäessä
    boolean paused = false;

    Bitmap unscaledBackground, background, unscaledBackground2, background2, unscaledPause, pause;

    public GameplayScene(Context context, int levelNumber){

        player = new Player(new Rect(100, 100, 200, 200), Color.BLACK);
        playerPoint = new Point(100, 880);
        player2 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player2Point = new Point(100, 885);
        player3 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player3Point = new Point(100, 880);

        unscaledBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.background5);
        background = Bitmap.createScaledBitmap(unscaledBackground, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledBackground2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundsky5);
        background2 = Bitmap.createScaledBitmap(unscaledBackground2, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);

        unscaledPause = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        pause = resize(unscaledPause, Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT/10);

        platformManager = new PlatformManager(levelNumber);
        bgManager = new Background(background, background2);

        jumpPower = -1;
        player.playerJump = true;
        action = 1;

    }

    @Override
    public void update() {
        if(!paused) {
            if (player.playerJump) playerMove();
            player.update(playerPoint);
            player2.update(player2Point);
            player3.update(player3Point);
            bgManager.update();
            platformManager.update();
            if (!paused) {
                if (player.powerUpSpeed) player.powerUpSpeedTimer--;
                if (player.powerUpDouble) player.powerUpDoubleTimer--;
            }
            if (player.powerUpSpeedTimer <= 0) {
                platformManager.decreaseSpeed();
                bgManager.decreaseBGSpeed();
                player.powerUpSpeedTimer = 100;
                player.powerUpSpeed = false;
            }
            if (player.powerUpDoubleTimer <= 0) {
                player.powerUpDoubleTimer = 100;
                player.powerUpDouble = false;
                player.doubleJumpAvailable = false;
            }

            if (platformManager.playerCollide(player3) && platformManager.collided != null) {
                if (platformManager.collided.platformId == 1 && jumpPower < 0) {
                    System.out.println("COLLIDE");
                    playerPoint.y = (int) (platformManager.collided.posY() - platformManager.collided.getHeightHalf() - player.getPlayerHeight() / 2);
                    player2Point.y = playerPoint.y + 5;
                    player3Point.y = playerPoint.y;
                    if (action == MotionEvent.ACTION_UP) player.playerJump = false;
                    jumpPower = jumpPowerDefault;
                    if (player.powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
                    if (player.powerUpDouble) player.doubleJumpAvailable = true;
                }
                if (platformManager.collided.platformId == 2) {
                    System.out.println("DEAD");
                    playerPoint.y = (int) (platformManager.collided.posY() - platformManager.collided.getHeightHalf() - player.getPlayerHeight() / 2);
                    player.update(playerPoint);
                    pause(true);
                    player.dead = true;
                }
                if (platformManager.collided.platformId == 3) {
                    System.out.println("SPEED UP");
                    if (!player.powerUpSpeed) {
                        player.powerUpSpeed = true;
                        platformManager.increaseSpeed();
                        bgManager.inceaseBGSpeed();
                    }
                    if (player.powerUpSpeed) {
                        player.powerUpSpeedTimer = player.powerUpSpeedTimer + 100;
                    }
                    platformManager.platforms.remove(platformManager.collided);
                }
                if (platformManager.collided.platformId == 5) {
                    System.out.println("DOUBLE JUMP");
                    if (!player.powerUpDouble) {
                        player.powerUpDouble = true;
                        player.doubleJumpAvailable = true;
                    }
                    if (player.powerUpDouble) {
                        player.powerUpDoubleTimer = player.powerUpDoubleTimer + 100;
                    }
                    platformManager.platforms.remove(platformManager.collided);
                }
                if (platformManager.collided.platformId == 6) {
                    System.out.println("INVICIBILITY");
                    if (!player.powerUpDouble) {
                        player.powerUpDouble = true;
                        player.doubleJumpAvailable = true;
                    }
                    if (player.powerUpDouble) {
                        player.powerUpDoubleTimer = player.powerUpDoubleTimer + 100;
                    }
                    platformManager.platforms.remove(platformManager.collided);
                }
                if (platformManager.collided.platformId == 4) {
                    System.out.println("GOAL");
                    pause(true);
                    goalReached = true;
                }
            } else if (platformManager.playerCollide(player2) != true && (!player.playerJump) && player.playerPosY() < 1030) {
                System.out.println("FALL");
                player.playerJump = true;
                jumpPower = 0.0f;
            }
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
        if (player.powerUpSpeed){
            paint.setColor(Color.GREEN);
            canvas.drawRect(Constants.SCREEN_WIDTH * 0.7f, 50, Constants.SCREEN_WIDTH * 0.7f + player.powerUpSpeedTimer * 2, 75, paint);
        }
        if (player.powerUpDouble){
            paint.setColor(Color.BLACK);
            canvas.drawRect(Constants.SCREEN_WIDTH * 0.7f, 100, Constants.SCREEN_WIDTH * 0.7f + player.powerUpDoubleTimer * 2, 125, paint);
        }

        canvas.drawBitmap(pause, Constants.SCREEN_WIDTH - pause.getWidth() - 10, 10, null);

        if (paused) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            paint.setTextAlign(Paint.Align.CENTER);

            if (goalReached) {
                canvas.drawText("GOAL REACHED! - PRESS TO CONTINUE", Constants.SCREEN_WIDTH / 2 + paint.getTextSize() / 2, Constants.SCREEN_HEIGHT / 2 + paint.getTextSize() / 2, paint);
                switch (Constants.CURR_LEVEL) {
                    case 1:
                        Constants.LEVEL1_CLEARED = true;
                        break;
                    case 2:
                        Constants.LEVEL2_CLEARED = true;
                        break;
                    case 3:
                        Constants.LEVEL3_CLEARED = true;
                        break;
                    case 4:
                        Constants.LEVEL4_CLEARED = true;
                        break;
                    case 5:
                        Constants.LEVEL5_CLEARED = true;
                        break;
                }
            }
            else if (player.dead) {
                canvas.drawText("YOU DIED! - PRESS TO CONTINUE", Constants.SCREEN_WIDTH / 2 + paint.getTextSize() / 2, Constants.SCREEN_HEIGHT / 2 + paint.getTextSize() / 2, paint);
            }
            else {
                canvas.drawText("- PRESS SCREEN TO CONTINUE -", Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT / 2 + paint.getTextSize() / 2, paint);
            }
        }
    }

    public void pause(boolean pause){

        paused = pause;

        if(paused){
            platformManager.paused = true;
            bgManager.paused = true;
            player.paused = true;
            player2.paused = true;
            player3.paused = true;
        } else {
            platformManager.paused = false;
            bgManager.paused = false;
            player.paused = false;
            player2.paused = false;
            player3.paused = false;
        }
    }

    public void receiveMethod(MotionEvent event){

        int x = (int)event.getX();
        int y = (int)event.getY();

        action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            if(paused){
                if(goalReached){
                    endScene();
                }
                else if(player.dead){
                    endScene();
                }
                else {
                    pause(false);
                }
            } else {
                if (x >= (Constants.SCREEN_WIDTH - pause.getWidth() - 10) && x < (Constants.SCREEN_WIDTH - 10)
                        && y >= 10 && y < (10 + pause.getHeight())) {
                    pause(true);
                } else {
                    if (player.playerJump && player.doubleJumpAvailable){
                        if (player.powerUpSpeed) jumpPower = jumpPowerDefault * 1.5;
                        else jumpPower = jumpPowerDefault;
                        player.doubleJumpAvailable = false;
                    }
                    player.playerJump = true;
                }
            }
        }
    }

    public void playerMove() {
        playerPoint.y = (int) player.playerPosY() - (int) jumpPower;
        player2Point.y = playerPoint.y + 5;
        if (jumpPower >= 0) jumpPower -= 5.0f;
        else if (jumpPower <= 0) jumpPower -= 7.5f;
        if (jumpPower >= 0 && player.powerUpSpeed) jumpPower -= 5.0f;
        else if (jumpPower <= 0 && player.powerUpSpeed) jumpPower -= 7.5f;
        player3Point.y = playerPoint.y - (int) jumpPower;
        player3Point.x = playerPoint.x - (int) platformManager.speed;
        // Pidetään pelaaja ruudulla, kun se tippuu maahan
        if (playerPoint.y > Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2)) {
            jumpPower = jumpPowerDefault;
            playerPoint.y = (int) (Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2));
            player2Point.y = playerPoint.y + 5;
            player3Point.y = playerPoint.y;
            if (action == MotionEvent.ACTION_DOWN) player.playerJump = true;
            else player.playerJump = false;
            if (player.powerUpSpeed) jumpPower = jumpPowerDefault * 1.5f;
            if (player.powerUpDouble) player.doubleJumpAvailable = true;
        }
    }

    private Bitmap resize(Bitmap image, int maxWidth, int maxHeight){
        if(maxHeight > 0 && maxWidth > 0){
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1){
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    @Override
    public void endScene() {
        SceneManager.ACTIVE_SCENE = 0;
    }
}
