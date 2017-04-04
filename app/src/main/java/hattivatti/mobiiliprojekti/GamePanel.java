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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;

    Player player;
    Player player2;
    private Point playerPoint;
    private Point player2Point;
    private PlatformManager platformManager;

    int action;
    boolean jump = false; // True kun pelaaja hyppää
    double jumpPowerDefault = 72.5f;
    double jumpPower = jumpPowerDefault;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        player = new Player(new Rect(100, 100, 200, 200), Color.BLACK);
        playerPoint = new Point(100, 880);
        player2 = new Player(new Rect(100, 100, 200, 200), Color.WHITE);
        player2Point = new Point(100, 885);

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
        platformManager.update();

        if (platformManager.playerCollide(player)) {
            System.out.println("COLLIDE");
            playerPoint.set((int) player.playerPosX(), (int) (platformManager.collided.posY() - platformManager.collided.getHeightHalf() - player.getPlayerHeight() / 2));
            player2Point.set((int) player.playerPosX(), (int) playerPoint.y + 5);
            if (action == MotionEvent.ACTION_UP) jump = false;
            jumpPower = jumpPowerDefault;
        }
        else if (platformManager.playerCollide(player2) != true && (!jump) && player.playerPosY() < 1030) {
            System.out.println("FALL");
            jump = true;
            jumpPower = 0.0f;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.GRAY);
        player.draw(canvas);
        platformManager.draw(canvas);
        //player2.draw(canvas);
    }

    public void playerMove() {
        jumpPower -= 5.5f;
        if (jumpPower < 0) jumpPower -= 3.5f;
        playerPoint.set((int) player.playerPosX(), (int) player.playerPosY() - (int) jumpPower);
        player2Point.set((int) player.playerPosX(), (int) playerPoint.y + 5);
        //keep player on the screen when landing
        if (playerPoint.y > Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2)) {
            jump = false;
            playerPoint.y = (int) (Constants.SCREEN_HEIGHT - (player.getPlayerHeight() / 2));
            player2Point.y = (int) playerPoint.y + 5;
            if (action == MotionEvent.ACTION_DOWN) jump = true;
            jumpPower = jumpPowerDefault;
        }
    }
}
