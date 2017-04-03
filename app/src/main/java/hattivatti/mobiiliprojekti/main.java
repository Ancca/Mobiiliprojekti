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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //piilota yläpalkki
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //peli koko näytölle
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(new GamePanel(this));
    }
}
