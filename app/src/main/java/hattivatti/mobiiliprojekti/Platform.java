package hattivatti.mobiiliprojekti;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Valtteri on 1.4.2017.
 */

public class Platform implements GameObject {

    public boolean isPowerUp = false;
    private Rect rectangle;
    public int platformId;
    int platformGap;
    private Animation currAnim;
    private Animation ground;
    private Animation powerup;
    private Animation powerup2;
    private Animation powerup3;
    private Animation goal;
    private Animation obstacle;
    private AnimationManager animManager;
    private Rect goalRect;

    public Rect getRectangle(){
        return rectangle;
    }

    public void incrementX(float x){
        rectangle.left += x;
        rectangle.right += x;
        if(platformId == 4){
            goalRect.left += x;
            goalRect.right += x;
        }
    }

    public Platform(){

    }


    public Platform(int top, int platformWidth, int platformHeight, int id, int platformGap){
        this.rectangle = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - top, (Constants.SCREEN_WIDTH + platformWidth), ((Constants.SCREEN_HEIGHT - top) + platformHeight));
        this.platformId = id;
        this.platformGap = platformGap;


        BitmapFactory bf = new BitmapFactory();
        Bitmap groundImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.grass);
        Bitmap powerupImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.powerup_speed);
        Bitmap powerupImg2 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.powerup_doublejump);
        Bitmap powerupImg3 = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.powerup_invincibility);
        Bitmap goalImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.signexit);
        Bitmap obstacleImg = bf.decodeResource(Constants.CONTEXT.getResources(), R.drawable.spikes2);

        ground = new Animation(new Bitmap[]{groundImg}, 2);
        powerup = new Animation(new Bitmap[]{powerupImg}, 2);
        powerup2 = new Animation(new Bitmap[]{powerupImg2}, 2);
        powerup3 = new Animation(new Bitmap[]{powerupImg3}, 2);
        goal = new Animation(new Bitmap[]{goalImg}, 2);
        obstacle = new Animation(new Bitmap[]{obstacleImg}, 2);

        goalRect = new Rect(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT - 100, Constants.SCREEN_WIDTH + 100, Constants.SCREEN_HEIGHT);

        switch (platformId) {
            case 1:
                //platform
                currAnim = ground;
                break;
            case 2:
                //obstacle
                currAnim = obstacle;
                break;
            case 3:
                //speed powerup
                currAnim = powerup;
                break;
            case 4:
                //goal
                currAnim = goal;
                break;
            case 5:
                //jump powerup
                currAnim = powerup2;
                break;
            case 6:
                //invincibility powerup
                currAnim = powerup3;
        }

        animManager = new AnimationManager(new Animation[]{currAnim});
    }

    @Override
    public void draw(Canvas canvas) {
        /*Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);*/
        if(platformId == 4){
            animManager.draw(canvas, goalRect);
        } else {
            animManager.draw(canvas, rectangle);
        }
    }

    @Override
    public void update() {
        animManager.playAnim(0);
        animManager.update();
    }

    public int posX(){
        int x = this.rectangle.centerX();
        return x;
    }

    public int posY(){
        int y = this.rectangle.centerY();
        return y;
    }

    public boolean playerCollide(Player player){
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public int getHeightHalf(){
        int heigthHalf = this.rectangle.height() / 2;
        return heigthHalf;
    }

    public int getWidthHalf(){
        int widthHalf = this.rectangle.width() / 2;
        return widthHalf;
    }

    public int getWidth(){
        int widthHalf = this.rectangle.width() / 2;
        return widthHalf;
    }
}
