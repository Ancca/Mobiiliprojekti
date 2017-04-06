package hattivatti.mobiiliprojekti;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Tursake on 6.4.2017.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveMethod(MotionEvent event);
}
