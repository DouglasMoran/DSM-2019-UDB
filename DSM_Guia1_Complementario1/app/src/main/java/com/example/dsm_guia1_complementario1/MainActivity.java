package com.example.dsm_guia1_complementario1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SensorEventListener sensorEventListener;
    SensorManager sensorManager;
    Sensor sensor;
    int song = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        if(sensor == null){
            finish();
        }


        sensorEventListener = new SensorEventListener() {

            public void onSensorChanged(SensorEvent sensorevent) {


                double x = sensorevent.values[0];
                double y = sensorevent.values[1];
                double z = sensorevent.values[2];


                System.out.println("Valor GiroX"  + x);
                if (x<-5 && song ==0){
                    getWindow().getDecorView().setBackground(getDrawable(R.drawable.nervous));
                    song++;
                } else if(x>-5 && song ==1) {
                    song++;
                    getWindow().getDecorView().setBackground(getDrawable(R.drawable.good));

                }

                if(song ==2){
                    sound();
                    song =0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        Start();
    }


    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this, R.raw.good);
        mediaPlayer.start();

    }

    private void Start(){
        sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void Stop(){

        sensorManager.unregisterListener(sensorEventListener);

    }


    protected void onResume() {
        super.onResume();
        Start();
    }

    protected void onPause() {
        super.onPause();
        Stop();
    }

}