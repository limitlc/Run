package com.paxw.run;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by lichuang on 2016/5/25.
 */
public class MyGameThread extends Thread {
    private String TAG = "lichuang" ;
    private Context mContext ;
    private SurfaceHolder mSurfaceHolder;
    private boolean isRun = true;
    public MyGameThread(Context mContext , SurfaceHolder mSurfaceHolder) {
        this.mContext = mContext;
        this.mSurfaceHolder = mSurfaceHolder;
    }

    @Override
    public void run() {
        while (isRun){
            Canvas canvas = null;
            canvas = mSurfaceHolder.lockCanvas();
            long time = System.currentTimeMillis();
            synchronized (mSurfaceHolder) {
                doDrawCanvas(canvas);
            }
            long dTime  = System.currentTimeMillis() - time;
            if (dTime < GameUi.GAME_FRAME_DTIME){
                try {
                    Thread.sleep(GameUi.GAME_FRAME_DTIME - dTime);
                } catch (InterruptedException e) {
                    Log.e(TAG, "run:  线程spleep被打断" );
                }
            }

        }



    }


    private Bitmap mBackgroundImage;
    private void doDrawCanvas(Canvas canvas){
        Bitmap tempBitmap = null;
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
    }


    public void setIsRun(boolean isRun){
        this.isRun = isRun;
    }
}
