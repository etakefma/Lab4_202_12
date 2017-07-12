package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Evan on 6/22/2017.
 */

public class GameLoopTask extends TimerTask {

    private Activity myActivity;
    private Context myContext;
    private RelativeLayout myRL;
    private int blockOffset = -40;//offset to correct block being on screen properly after scaling
    public enum direction {UP, DOWN, LEFT, RIGHT, NO_MOTION};
    private direction currentDir = direction.NO_MOTION;
    private LinkedList<GameBlock> gbList = new LinkedList();
    private LinkedList<GameBlock> removeList = new LinkedList();
    private Random randomInt = new Random();
    private static int squareDiv = 180;//distance between square corners to divide board
    private boolean inMotion = true;
    private boolean blockAdded = true;



    // pos range 0-540

    GameLoopTask(Activity a, Context c, RelativeLayout r)
    {
         myActivity = a;
        myContext = c;
        myRL =r;
        gbList.add(createBlock());
        gbList.add(createBlock());


    }

    public void setCurrentDir(direction cD)
    {
        currentDir = cD;//sets current direction of the gameloop
        for (int i = 0; i < gbList.size(); i++ )
        {
            gbList.get(i).setBlockDir(cD);
        }
        //sets the direction of the moving block
        //Log.d("Direction:", currentDir.toString());
    }

    private GameBlock createBlock()
    {
        int randX = squareDiv*randomInt.nextInt(4);
        int randY = squareDiv*randomInt.nextInt(4);
        for (int i = 0; i < gbList.size(); i++ ) {

            if((gbList.get(i).getyPos() == randY+blockOffset) &&(gbList.get(i).getxPos() == randX+blockOffset) )
            {
                Log.d("MATCH", ""+randX+","+randY);
                int NewRandomX = squareDiv*randomInt.nextInt(4);
                int NewRandomY = squareDiv*randomInt.nextInt(4);
                i = 0;
                randX = NewRandomX;
                randY = NewRandomY;

            }
                Log.d("XPOS, YPOS LOOP", ""+randX+","+randY+","+i);
            }
            Log.d("XPOS, YPOS Final", ""+randX+","+randY);
            GameBlock newGameBlock = new GameBlock(myContext, randX+blockOffset,randY+blockOffset, myRL);//creates game block and centers it at top left
        //myRL.addView(newGameBlock);//moved to block contructor.
        return newGameBlock;
    }
/*
    private GameBlock createBlock(int x, int y)
    {
        GameBlock newGameBlock = new GameBlock(myContext,x+blockOffset ,y+blockOffset, myRL);//creates game block and centers it at top left
        //myRL.addView(newGameBlock);//moved to block contructor.
        return newGameBlock;
    }
*/
    private int getNumberBlocksInfront(int posX, int posY, int destination)/////MAKE SURE SAME AXIS DUMB ASS
    {
        int count = 0;//to account for the block in question being counted
        if (currentDir == direction.UP)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {


                if ((gbList.get(i).getyPos() < posY)&&(gbList.get(i).getxPos() == posX)) count ++;
            }
            return (180*count);
        }
        if (currentDir == direction.LEFT)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getxPos() < posX)&&(gbList.get(i).getyPos() == posY)) count ++;
            }
            return (180*count);
        }
        if (currentDir == direction.DOWN)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getyPos() > posY)&&(gbList.get(i).getxPos() == posX)) count ++;
            }
            return (540-180*count);
        }
        if (currentDir == direction.RIGHT)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getxPos() > posX)&&(gbList.get(i).getyPos() == posY)) count ++;
            }
            return (540-180*count);
        }
        else return destination;

    }

    private void mergeBlock(GameBlock thisBlock)
    {

        int count = 0;
        boolean split = false;
        GameBlock holdBlock = null;
        if (currentDir == direction.UP)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {


                if ((gbList.get(i).getyPos() < thisBlock.getyPos())&&(gbList.get(i).getxPos() == thisBlock.getxPos())&&(gbList.get(i).getValue() == thisBlock.getValue()))
                {
                    count ++;
                    if(gbList.get(i).getyPos() == thisBlock.getyPos() - 180)
                    {
                        holdBlock = gbList.get(i);
                    }
                    if(gbList.get(i).getyPos() == thisBlock.getyPos() - 3*180)
                    {
                        split = true;
                    }
                }

            }

        }
        if (currentDir == direction.LEFT)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getxPos() < thisBlock.getxPos())&&(gbList.get(i).getyPos() == thisBlock.getyPos())&&(gbList.get(i).getValue() == thisBlock.getValue()))
                {
                    count ++;
                    if(gbList.get(i).getxPos() == thisBlock.getxPos() - 180)
                    {
                        holdBlock = gbList.get(i);
                    }
                    if(gbList.get(i).getxPos() == thisBlock.getxPos() - 3*180)
                    {
                        split = true;
                    }
                }

            }

        }
        if (currentDir == direction.DOWN)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getyPos() > thisBlock.getyPos())&&(gbList.get(i).getxPos() == thisBlock.getxPos())&&(gbList.get(i).getValue() == thisBlock.getValue()))
                {
                    count ++;
                    if(gbList.get(i).getyPos() == thisBlock.getyPos() + 180)
                    {
                        holdBlock = gbList.get(i);
                    }
                    if(gbList.get(i).getyPos() == thisBlock.getyPos() + 3*180)
                    {
                        split = true;
                    }
                }

            }

        }
        if (currentDir == direction.RIGHT)
        {
            for (int i = 0; i < gbList.size(); i++ )
            {

                if ((gbList.get(i).getxPos() > thisBlock.getxPos())&&(gbList.get(i).getyPos() == thisBlock.getyPos())&&(gbList.get(i).getValue() == thisBlock.getValue()))
                {
                    count ++;
                    if(gbList.get(i).getxPos() == thisBlock.getxPos() + 180)
                    {
                        holdBlock = gbList.get(i);
                    }
                    if(gbList.get(i).getxPos() == thisBlock.getxPos() + 3*180)
                    {
                        split = true;
                    }

                }

            }

        }
        if((holdBlock != null) && (split == true || count == 1)) {


            holdBlock.setValue(holdBlock.getValue() * 2);
            /*thisBlock.cleanup(myRL);
            myRL.removeView(thisBlock);
            gbList.remove(thisBlock);

            thisBlock = null;
            */
            removeList.add(thisBlock);
        }
        return;



    }

    synchronized public void run()
    {

        myActivity.runOnUiThread(new Runnable() {
            public void run() {//Insert your Periodic Tasks Here!}})
                //Log.d("Start", ""+inMotion+","+blockAdded);
                                for (int i = 0; i < gbList.size(); i++ )
                                {

                                    if((currentDir == direction.DOWN )|| (currentDir == direction.UP))
                                    {
                                        gbList.get(i).setDestination( getNumberBlocksInfront(gbList.get(i).getxPos(),gbList.get(i).getyPos(), gbList.get(i).getDestination()));
                                    }
                                    if((currentDir == direction.LEFT )|| (currentDir == direction.RIGHT))
                                    {
                                        gbList.get(i).setDestination( getNumberBlocksInfront(gbList.get(i).getxPos(),gbList.get(i).getyPos(),gbList.get(i).getDestination()));
                                    }
                                    gbList.get(i).move();
                                    //mergeBlock(gbList.get(i));
                                    if (gbList.get(i).getMoving() == false && i == 0) {
                                        //Log.d("STOPED", "STOP");
                                        //mergeBlock(gbList.get(i));
                                        inMotion = false;
                                    }
                                    else if (gbList.get(i).getMoving() == false && !inMotion) {
                                        //Log.d("STOPED", "STOP");
                                        //mergeBlock(gbList.get(i));
                                        inMotion = false;
                                    } else {
                                        //Log.d("MOVING", "MOVING");
                                        inMotion = true;
                                        blockAdded = false;

                                    }



                                }
                                for(GameBlock temp : gbList)
                                {

                                    mergeBlock(temp);
                                }
                                for(GameBlock temp : removeList)
                                {
                                    temp.cleanup(myRL);
                                    myRL.removeView(temp);
                                    gbList.remove(temp);
                                    temp = null;
                                }
                                removeList.clear();


                               // Log.d("INMOTION,BLOCK ADD", ""+inMotion+","+blockAdded);
                                if (!inMotion && !blockAdded)
                                {
                                    //Log.d("BLOCK ADDED", "BLOCK ADDED");
                                    gbList.add(createBlock());
                                    blockAdded = true;
                                    //inMotion = true;
                                }


            }
        });
    }


}
