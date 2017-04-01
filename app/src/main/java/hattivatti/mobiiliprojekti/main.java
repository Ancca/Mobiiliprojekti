package hattivatti.mobiiliprojekti;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;

import org.w3c.dom.Node;

import java.security.acl.Group;
import java.util.Timer;
import java.util.TimerTask;

public class main extends Activity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView player;
    private ImageView platform;
    private ImageView platform2;

    boolean jump = false; // True kun pelaaja hyppää
    float jumpPowerDefault = 30.0f; // Kuinka monta pikseliä pelaaja hyppää per frame (alussa)
    float jumpPower = jumpPowerDefault;
    float obstacleSpeedDefault = 20.0f; // Esteiden nopeus
    float obstacleSpeed = obstacleSpeedDefault;
    public int action;

    float playerY = 760.0f;
    float playerX = 100.0f;
    float boxX = 1920.0f;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //piilota yläpalkki
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //peli koko näytölle
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(new GamePanel(this));

        /*scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        player = (ImageView) findViewById(R.id.player);
        platform = (ImageView) findViewById(R.id.platform);
        platform2 = (ImageView) findViewById(R.id.platform2);

        platform.setY(860);
        platform2.setY(500);
        platform2.setX(1920);


        startLabel.setVisibility(View.INVISIBLE);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (jump) playerJump();
                        playerFallTest();
                        moveObstacles();
                    }
                });
            }
        }, 0, 20);*/
    }

    /*public void playerJump() {
        playerY -= jumpPower;
        jumpPower -= 1.0f;
        if (jumpPower < 0) jumpPower -= 0.20f;
        player.setY(playerY);
        playerHitboxTest();
    }

    public void playerHitboxTest() {
        if (playerY + 5 > platform.getY()-platform.getHeight() && playerY + 5 < platform.getY()){
            player.setY(platform.getY()-platform.getHeight());
            if (action == MotionEvent.ACTION_UP) jump = false;
            jumpPower = jumpPowerDefault;
        }
        if (playerY + 5 > platform2.getY()-platform2.getHeight() && playerY + 5 < platform2.getY()){
            if (playerX > platform2.getX() && playerX < platform2.getX()+platform2.getWidth()){
                player.setY(platform2.getY()-platform2.getHeight());
                if (action == MotionEvent.ACTION_UP) jump = false;
                jumpPower = jumpPowerDefault;
            }
        }
    }

    public void playerFallTest() {
        if (playerY + 5 > platform2.getY()-platform2.getHeight() && playerY + 5 < platform2.getY()){
            if (playerX < platform2.getX() && !jump || playerX > platform2.getX()+platform2.getWidth() && !jump){
                jump = true;
                jumpPower = -1.0f;
            }
        }
    }


    public void moveObstacles() {
        boxX -= obstacleSpeed;
        box.setX(boxX);
        if (boxX < -200) boxX = 1920;
        platform2.setX(platform2.getX()-obstacleSpeed+5);
        if (platform2.getX() < -200) platform2.setX(1920);

    }

    public void moveBackground(){

    }

    public boolean onTouchEvent(MotionEvent event) {
        action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            jump = true;
        }
        scoreLabel.setText(String.valueOf(playerY));
        return true;
    }*/
}
