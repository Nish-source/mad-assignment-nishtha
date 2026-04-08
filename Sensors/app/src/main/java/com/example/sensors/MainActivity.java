package com.example.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    Sensor accelerometer, light, proximity;

    TextView accData, lightData, proximityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accData = findViewById(R.id.accData);
        lightData = findViewById(R.id.lightData);
        proximityData = findViewById(R.id.proximityData);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accelerometer != null)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (light != null)
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);

        if (proximity != null)
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accData.setText("X: " + x + " Y: " + y + " Z: " + z);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float value = event.values[0];
            lightData.setText("Light: " + value);
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float value = event.values[0];
            proximityData.setText("Proximity: " + value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed
    }
}