package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import static android.R.color.black;

//import android.widget.ImageView;

/**
 * Created by Evan on 6/22/2017.
 */

public class GameBlock extends AppCompatImageView {

    private float scale = 0.65f;//scales block down to correct size
    private int xPos;
    private int yPos;
    private int destination;

    private int blockOffset = -40;
    private int numbOffsetX = 110;
    private int numbOffsetY = 90;

    private int moveVelocity = 5;
    private int moveAccel = 9;
    //private int maxBound = 540+blockOffset;
    //private int minBound = blockOffset;
    private boolean moving;
    private TextView numbDisplay;
    private Integer blockVal;
    private Random randomInt = new Random();

    private GameLoopTask.direction blockDir;

    public int getxPos()
    {
        return xPos;
    }
    public int getyPos()
    {
        return yPos;
    }

    public GameBlock(Context c, int x, int y, RelativeLayout rl)
    {

        super(c);
        blockDir = GameLoopTask.direction.NO_MOTION;
        xPos =x;
        yPos =y;
        this.setImageResource(R.drawable.gameblock);
        this.setScaleX(scale);
        this.setScaleY(scale);
        this.setX(xPos);
        this.setY(yPos);
        moving = false;

        blockVal = 2*(randomInt.nextInt(2)+1);
        numbDisplay = new TextView(c);
        numbDisplay.setText(blockVal.toString());
        numbDisplay.setTextColor(getResources().getColor(black));

        numbDisplay.setX(xPos+numbOffsetX);
        numbDisplay.setY(yPos+numbOffsetY);
        numbDisplay.setTextSize(40);




        rl.addView(numbDisplay);
        rl.addView(this);
        numbDisplay.bringToFront();





    }

    public void cleanup(RelativeLayout RL)
    {
        RL.removeView(numbDisplay);
        numbDisplay = null;
    }

    public void setBlockDir(GameLoopTask.direction d)
    {
        if( !moving )blockDir = d;
    }// checks to make sure block has copmleted a motion before it allows the direction to change

    public void setDestination(int d)
    {
        if( !moving )destination = d;
        //Log.d("Destination", ""+ destination);
        //Log.d("Direction", blockDir.toString());
    }

    public int getValue()
    {
        return blockVal;
    }

    public void setValue(int val)
    {
        blockVal = val;
        numbDisplay.setText(blockVal.toString());

    }

    public int getDestination()
    {
        return destination;
    }

    public boolean getMoving()
    {
        return moving;
    }


    public void move()
    {
        if (blockDir == GameLoopTask.direction.DOWN)
        {
            moving = true;//sets block to me in motion
            yPos = yPos + moveVelocity;//increases position based on velocity
            moveVelocity += moveAccel;//increases velocity based on acceleration
            if ( yPos > destination+ blockOffset)//if you are past the boundary
            {
                yPos = destination + blockOffset;//move back to boundary
                moveVelocity = 5;//reset velocity
                moving = false;//no longer in motion
            }
            this.setY(yPos);//set position to ne position
            numbDisplay.setY(yPos+numbOffsetY);
        }
        else if (blockDir == GameLoopTask.direction.UP)//same as above but for up
        {
            moving = true;
            yPos = yPos - moveVelocity;
            moveVelocity += moveAccel;
            if ( yPos < destination+ blockOffset)
            {
                yPos = destination+ blockOffset;
                moveVelocity = 5;
                moving = false;
            }
            this.setY(yPos);
            numbDisplay.setY(yPos+numbOffsetY);

        }
        else if (blockDir == GameLoopTask.direction.LEFT)//same as above but for left
        {
            moving = true;
            xPos = xPos - moveVelocity;
            moveVelocity += moveAccel;
            if ( xPos < destination+ blockOffset)
            {
                xPos = destination+ blockOffset;
                moveVelocity = 5;
                moving = false;
            }
            this.setX(xPos);
            numbDisplay.setX(xPos+numbOffsetX);
        }
        else if (blockDir == GameLoopTask.direction.RIGHT)//same as above but for right
        {
            moving = true;
            xPos = xPos + moveVelocity;
            moveVelocity += moveAccel;
            if ( xPos > destination+ blockOffset)
            {
                xPos = destination+ blockOffset;
                moveVelocity = 5;
                moving = false;
            }
            this.setX(xPos);
            numbDisplay.setX(xPos+numbOffsetX);


        }

    }


}
