package hattivatti.mobiiliprojekti;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Debug;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
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

public class MainActivity extends Activity{

    public boolean levelEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Piilota yläpalkki
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Peli koko näytölle
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final WindowManager w = (WindowManager) getBaseContext().getSystemService(getBaseContext().WINDOW_SERVICE);
        final Display d = w.getDefaultDisplay();
        final DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;
        Constants.SCALE = dm.scaledDensity;

        setContentView( new GamePanel(this));
    }
}
