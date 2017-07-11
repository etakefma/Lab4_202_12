package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.util.Log;
import android.widget.TextView;

/**
 * Created by Evan on 6/2/2017.
 */

public class MotionFSM {

    enum States{ Waiting, Rising, Stable, Determined };
    private States signalState;


    enum direction{ Left, Right,Up, Down, Undetermined};
    private direction dirs;


    private final float[] threshHolds = {0.5f, 1f, 0.4f};

    private int countDefault = 30;

    private int count;
    private float[] oldVal= {0,0,0};

    private GameLoopTask gameLoop;


    private TextView directionTV;

    public MotionFSM(TextView t, GameLoopTask gl)
    {
        count = countDefault;
        signalState = States.Waiting;
        dirs = direction.Undetermined;
        directionTV = t;
        gameLoop = gl;
    }

    public void resetFSM()
    {
        count = countDefault;
        signalState = States.Waiting;
        dirs = direction.Undetermined;
    }

    public void runFSM(float[] accVals)
    {

        float slopeX = accVals[0] - oldVal[0];
        float slopeY = accVals[1] - oldVal[1];
        switch (signalState)
        {
            case Waiting:
                //Log.d("FSM Says:", String.format("Waiting, Slope: %f", slopeX));
                if( accVals[0] > threshHolds[0])
                {
                    dirs = direction.Right;
                    signalState = States.Rising;
                }
                else if( accVals[0] < -threshHolds[0])
                {
                    dirs = direction.Left;
                    signalState = States.Rising;
                }
                else if( accVals[1] > threshHolds[0])
                {
                    dirs = direction.Up;
                    signalState = States.Rising;
                }
                else if( accVals[1] < -threshHolds[0])
                {
                    dirs = direction.Down;
                    signalState = States.Rising;
                }
                break;


            case Determined:
                Log.d("FSM Says:", "Determined");
                directionTV.setText(dirs.toString());
                switch (dirs)
                {
                    case Left:
                        gameLoop.setCurrentDir(GameLoopTask.direction.LEFT);
                        break;
                    case Right:
                        gameLoop.setCurrentDir(GameLoopTask.direction.RIGHT);
                        break;
                    case Up:
                        gameLoop.setCurrentDir(GameLoopTask.direction.UP);
                        break;
                    case Down:
                        gameLoop.setCurrentDir(GameLoopTask.direction.DOWN);
                        break;
                    default:
                        gameLoop.setCurrentDir(GameLoopTask.direction.NO_MOTION);
                        break;
                }
                resetFSM();
                break;


            case Rising:
                Log.d("FSM Says:", "Rising");
                if (dirs == direction.Right)
                {
                    if (slopeX <= 0)
                    {
                        if(accVals[0] > threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == direction.Left)
                {
                    if (slopeX >= 0)
                    {
                        if(accVals[0] < -threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == direction.Up)
                {
                    if (slopeY <= 0)
                    {
                        if(accVals[1] > threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }
                else if (dirs == direction.Down)
                {
                    if (slopeY >= 0)
                    {
                        if(accVals[1] < -threshHolds[1])
                        {
                            signalState = States.Stable;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }


                break;

            case Stable:
                Log.d("FSM Says:", "Falling" + count);
                count --;
                if( count == 0)
                {
                    Log.d("IN", "IN");
                    if ( dirs == direction.Right)
                    {
                        if (accVals[0] < threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == direction.Left)
                    {
                        if (accVals[0] > -threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == direction.Up)
                    {
                        if (accVals[1] < threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                    else if ( dirs == direction.Down)
                    {
                        if (accVals[1] > -threshHolds[2]) {
                            signalState = States.Determined;
                        }
                        else
                        {
                            dirs = direction.Undetermined;
                            signalState = States.Determined;
                        }
                    }
                }

                break;



            default:
                resetFSM();
                break;
        }
        oldVal[0] = accVals[0];
        oldVal[1] = accVals[1];

    }



}
