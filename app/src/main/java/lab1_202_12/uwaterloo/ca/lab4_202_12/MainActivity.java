package lab1_202_12.uwaterloo.ca.lab4_202_12;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Timer;

import static android.R.color.black;

public class MainActivity extends AppCompatActivity {
    //  Make the line graph, sensor event listeners, and array for accelerometer data global variables
    AccelerometerSensorEventListener aSel;

    private int GAMEBOARD_DIMENSION  = 720; //width, height to be chnged to phone


    float accData[][] = new float[100][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab1_202_12);

        RelativeLayout l = (RelativeLayout) findViewById(R.id.relativeLayout);
        //l.setOrientation(RelativeLayout.VERTICAL); <- don't need?
        l.getLayoutParams().width = GAMEBOARD_DIMENSION;
        l.getLayoutParams().height = GAMEBOARD_DIMENSION; //set layout dimensions
        l.setBackgroundResource(R.drawable.gameboard);
        GameLoopTask myGameLoopTask = new GameLoopTask(this, MainActivity.this.getApplicationContext(),l);
        Timer myGameLoop = new Timer();

        myGameLoop.schedule(myGameLoopTask, 50,50);


        // TextViews 1 - 4

        TextView tv2 = new TextView(getApplicationContext());
        tv2.setTextColor(getResources().getColor(black));
        l.addView(tv2);


        // Declare a Sensor Manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //  Declaration of sensor event listeners
        MotionFSM accelFSM = new MotionFSM( tv2, myGameLoopTask);
        // Acceleration Sensor Event Listener
        Sensor accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        aSel = new AccelerometerSensorEventListener(accData, accelFSM);
        sensorManager.registerListener(aSel, accSensor, sensorManager.SENSOR_DELAY_GAME);

    }
}


