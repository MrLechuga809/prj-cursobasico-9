package com.eleo95.magic8;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sManager;
    private Sensor acclSensor;
    private long lastUpdate = 0;
    private int lastRand = 0; //guarda el random in anterior
    private float x0, y0, z0;
    private static final int SHAKE_THRESHOLD = 250;
    private String[] responses;
    private Animation fadeAnim;
    private TextView texto;
    private EditText question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = findViewById(R.id.hellotext);
        Button cpybtn = findViewById(R.id.copyButton);
        question = findViewById(R.id.editText);
        fadeAnim = AnimationUtils.loadAnimation(this, R.anim.text_fade); //load anim
        //-----------------iniciando el  accelerómetro ------------------
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        responses = getResources().getStringArray(R.array.response_arr);
        if (sManager != null) {
            acclSensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sManager.registerListener(this, acclSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        //-------------------------------------------------------------
            cpybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Si el editText esta vacio, no permite copiar la info
                    if(!question.getText().toString().matches("")){
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData toCopy = ClipData.newPlainText( "Conversation", getResources().getText(R.string.Me)
                                +question.getText().toString()
                                +"\n"+getResources().getText(R.string.app_name)+": "
                                +texto.getText().toString());
                        if(clipboard != null){
                            clipboard.setPrimaryClip(toCopy);
                            Snackbar snack = Snackbar.make(v, R.string.textCopied,Snackbar.LENGTH_LONG);
                            snack.getView().setBackgroundColor(getResources().getColor(R.color.grisOscuro));
                            snack.show();
                            question.setText("");
                        }

                    }else{
                        Toast.makeText(MainActivity.this, R.string.questionFault, Toast.LENGTH_SHORT).show();

                    }

                }
            });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor mySensor = event.sensor;
        fadeAnim.reset();


        if(mySensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            //se determina la velocidad para samplear el sensor
            long cTime = System.currentTimeMillis();

            if((cTime-lastUpdate)>100){
                long periodo = cTime-lastUpdate;
                lastUpdate = cTime;

                float speed = Math.abs((x+y+z-x0-y0-z0)/periodo) * 1000; // v=d/t

                if(speed > SHAKE_THRESHOLD){
                    //Guarda el ultimo num. generado para compararlo con el siguiente
                    int generateRand = generateRandom(lastRand);
                    texto.setText(responses[generateRand]);
                    lastRand=generateRand;
                    texto.clearAnimation();
                    texto.startAnimation(fadeAnim);

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


    //funcion para evitar que random genere el mismo numero pasado
    int generateRandom(int lastRandomNumber){
        Random random = new Random();
      // int randomNumber = random.nextInt(19 - 1) + 1;
        int randomNumber = random.nextInt(19);
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
