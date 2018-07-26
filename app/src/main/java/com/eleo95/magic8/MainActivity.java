package com.eleo95.magic8;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sManager;
    private Sensor acclSensor;
    private long lastUpdate = 0;
    private float x0, y0, z0;
    private static final int SHAKE_THRESHOLD = 250;
    private String[] responses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        responses = getResources().getStringArray(R.array.response_arr);
        if (sManager != null) {
            acclSensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        Random rand = new Random();
        TextView texto = findViewById(R.id.hellotext);

        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //sampleando el sensor a gusto
            long cTime = System.currentTimeMillis();

            if((cTime-lastUpdate)>100){
                long periodo = cTime-lastUpdate;
                lastUpdate = cTime;

                float speed = Math.abs((x+y+z-x0-y0-z0)/periodo) * 1000;

                if(speed > SHAKE_THRESHOLD){
                    texto.setText(responses[generateRandom(rand.nextInt(19))]);

                }
                x0 = x;
                y0 = y;
                z0 = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPause() {
        super.onPause();
        sManager.unregisterListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        sManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    int generateRandom(int lastRandomNumber){
        Random random = new Random(); //test
        int randomNumber = random.nextInt(19 - 1) + 1;
        if(randomNumber == lastRandomNumber)
        {
            if(randomNumber==0){
                randomNumber++;
            }else{
                randomNumber--;
            }

        }
        return randomNumber;
    }
}
