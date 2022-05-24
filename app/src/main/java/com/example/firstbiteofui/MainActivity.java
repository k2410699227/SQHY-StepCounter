package com.example.firstbiteofui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private SpeedDisplay stepView;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MySensorEventListener mySensorEventListener;
    private Button button;
    private int stepNum=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = findViewById(R.id.speedDisplay);
        button = findViewById(R.id.button);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //获取传感器，在计步器中需要使用的是加速度传感器
        mySensorEventListener = new MySensorEventListener();
        sensorManager.registerListener(mySensorEventListener, sensor, sensorManager.SENSOR_DELAY_UI);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(mySensorEventListener);
    }

    public void modifyStep(int step) {
        Animator animator = ObjectAnimator.ofInt(stepView, "step", step, step+1);
        animator.setDuration(50);
        animator.start();
//        stepView.step=step+1;
//        stepView.invalidate();
//        stepView.postInvalidate();
        //Snackbar.make(button,Integer.toString(step), Snackbar.LENGTH_LONG).show();
    }

    class MySensorEventListener implements SensorEventListener {

        private double original_value=0;
        private double last_value=0;
        private double current_value=0;
        private boolean motionState=true; //是否处于运动状态

        @Override
        public void onSensorChanged(SensorEvent event) {
            double range=5; //设置一个精度范围
            float[] value=event.values;
            current_value =magnitude(value[0],value[1],value[2]); //计算当前的模
            //Snackbar.make(button,"读数变化", Snackbar.LENGTH_LONG).show();
            //向上加速的状态
            if(motionState==true){
                if (current_value >= last_value)
                    last_value = current_value;
                else {
                    //检测到一次峰值
                    if(Math.abs(current_value-last_value)>range){
                        original_value=current_value;
                        motionState=false;
                    }
                }
            }
            //向下加速的状态
            if(motionState==false){
                if (current_value <= last_value)
                    last_value = current_value;
                else {
                    //检测到一次峰值
                    if(Math.abs(current_value-last_value)>range){
                        original_value=current_value;
                        modifyStep(stepNum);
                        stepNum++;
                        //Snackbar.make(button,"加一步", Snackbar.LENGTH_LONG).show();
                        motionState=true;
                    }
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
        private double magnitude(float x, float y, float z) {
            double m=0;
            m=Math.sqrt(x*x+y*y+z*z);
            return m;
        }

    }
}