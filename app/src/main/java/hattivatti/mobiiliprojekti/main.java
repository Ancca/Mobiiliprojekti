package hattivatti.mobiiliprojekti;

import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;

import org.w3c.dom.Node;

import java.security.acl.Group;
import java.util.Timer;
import java.util.TimerTask;

public class main extends AppCompatActivity {

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
    int action;

    float playerY = 760.0f;
    float boxX = 1920.0f;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        player = (ImageView) findViewById(R.id.player);
        platform = (ImageView) findViewById(R.id.platform);
        platform2 = (ImageView) findViewById(R.id.platform);

        platform.setY(860);



        startLabel.setVisibility(View.INVISIBLE);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (jump) playerJump();
                        moveObstacles();
                    }
                });
            }
        }, 0, 20);
    }

    public void playerJump() {
        playerY -= jumpPower;
        jumpPower -= 1.0f;
        if (jumpPower < 0) jumpPower -= 0.20f;
        player.setY(playerY);
        if (playerY + 5 > platform.getY()-platform.getHeight() && playerY + 5 < platform.getY()){
            player.setY(platform.getY()-platform.getHeight());
            if (action == MotionEvent.ACTION_UP) jump = false;
            jumpPower = jumpPowerDefault;
        }
    }

    public void moveObstacles() {
        boxX -= obstacleSpeed;
        box.setX(boxX);
        if (boxX < -200) boxX = 1920;
    }

    public boolean onTouchEvent(MotionEvent event) {
        action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            jump = true;
        }
        scoreLabel.setText(String.valueOf(playerY));
        return true;
    }
}
