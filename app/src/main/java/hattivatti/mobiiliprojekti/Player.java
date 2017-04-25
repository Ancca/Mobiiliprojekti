package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Tursake on 30.3.2017.
 */

public class Player implements GameObject{

    public Rect rectangle;
    private int color;
    public boolean paused = false;
    public boolean dead = false; // True kun pelaaja kuolee
    public boolean playerJump = false; // True kun pelaaja hyppää
    public boolean powerUpSpeed = false; // True kun pelaajalla on speed powerup
    public boolean powerUpDouble = false; // True kun pelaajalla on double(jump) powerup
    public boolean doubleJumpAvailable = false; // True kun pelaaja voi hypätä toisen kerran (ilmassa)
    public boolean powerUpInvincibility = false; // True kun pelaajalla on invicibility powerup
    public int powerUpInvincibilityTimer = 100;
    public int powerUpSpeedTimer = 100; // Powerup ajastimet
    public int powerUpDoubleTimer = 100;

    private Animation run;
    private Animation jump;
    private Animation death;
    private Animation runEffect;
    private AnimationManager animManager;

    public Player(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap jumpImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_jump);
        Bitmap run1 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk01);
        Bitmap run2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk02);
        Bitmap run3 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk03);
        Bitmap run4= bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk04);
        Bitmap run5 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk05);
        Bitmap run6 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk06);
        Bitmap run7 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk07);
        Bitmap run8 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk08);
        Bitmap run9 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk09);
        Bitmap run10 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk10);
        Bitmap run11 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_walk11);
        Bitmap deathImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_hurt);
        Bitmap deathImg2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.p1_hurt2);

        jump = new Animation(new Bitmap[]{jumpImg}, 2);
        run = new Animation(new Bitmap[]{run1,run2, run3, run4, run5, run6, run7, run8, run9, run10, run11}, 0.3f);
        death = new Animation(new Bitmap[]{deathImg, deathImg2}, 0.2f);

        animManager = new AnimationManager(new Animation[]{run, jump, death});
    }

    public Rect getRectangle(){
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        /*Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);*/
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point){
        int state = 0;

        if(dead) state = 2;

        System.out.println("Current state: " + state);

        if(!paused) {
            float oldTop = rectangle.top;

            rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2,
                    point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

            //state 0 = run right, state 1 = jumping/falling
            if (rectangle.top != oldTop) {
                state = 1;
            }

            animManager.playAnim(state);
            animManager.update();

        } else if (state == 2){
            animManager.playAnim(state);
            animManager.update();
        }
    }

    public double playerPosX(){
        double x = this.rectangle.centerX();
        return x;
    }

    public double playerPosY(){
        double y = this.rectangle.centerY();
        return y;
    }

    public double getPlayerHeight(){
        double height = this.rectangle.height();
        return height;
    }
}
