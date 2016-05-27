package com.paxw.run;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    private MySurfaceView mySurfaceView;
    private String TAG = "lichuang";
    //手机传感器的运用
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorEventListener mSensorEventListener;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
        Log.d(TAG, "onCreate: game  开始了");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //获取众多服务中的重力感应服务
        mSensorEventListener = new SensorEventListener() {

            //这里随着重力感应的变化，一直持续更改，主角x轴方向的速度
            public void onSensorChanged(SensorEvent e) {
                mySurfaceView.handleMoving(e.values[SensorManager.DATA_X]);
            }

            public void onAccuracyChanged(Sensor s, int accuracy) {
            }
        };
        // 注册重力感应监听
        mSensorManager.registerListener(mSensorEventListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }
}
