package com.paxw.run;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

/**
 * Created by lichuang on 2016/5/25.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Context mContext;

    // TODO: 2016/5/25 这些是不是可以直接用线画
    private Bitmap mFootboardNormalImage;
    private Bitmap mFootboardUnstableImage1;
    private Bitmap mFootboardUnstableImage2;
    private Bitmap mFootboardSpringImage;
    private Bitmap mFootboardSpikedImage;
    private Bitmap mFootboardMovingLeftImage1;
    private Bitmap mFootboardMovingLeftImage2;
    private Bitmap mFootboardMovingRightImage1;
    private Bitmap mFootboardMovingRightImage2;
    private Bitmap mBackgroundImage;
    private Bitmap mRoleStandImage;
    private Bitmap mRoleDeadmanImage;
    private Bitmap mRoleFreefallImage1;
    private Bitmap mRoleFreefallImage2;
    private Bitmap mRoleFreefallImage3;
    private Bitmap mRoleFreefallImage4;
    private Bitmap mRoleMovingLeftImage1;
    private Bitmap mRoleMovingLeftImage2;
    private Bitmap mRoleMovingLeftImage3;
    private Bitmap mRoleMovingLeftImage4;
    private Bitmap mRoleMovingRightImage1;
    private Bitmap mRoleMovingRightImage2;
    private Bitmap mRoleMovingRightImage3;
    private Bitmap mRoleMovingRightImage4;
    private MyGameThread gameThread ;
    private SurfaceHolder surfaceHolder;
    private ScreenAttribute mScreenAttribute;
    private GameUi mGameUi;


    public MySurfaceView(Context context) {
        super(context);
        mContext = context;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        initRes();
        setFocusable(true);
    }

    /**
     * 初始化资源
     */
    private void initRes(){
        Point point = new Point();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getSize(point);
        mScreenAttribute = new ScreenAttribute(0,0,point.x,point.y);
        Resources res = mContext.getResources();
        mFootboardNormalImage = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_normal),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardUnstableImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_unstable1),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardUnstableImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_unstable2),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardSpringImage = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_spring),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardSpikedImage = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_spiked),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardMovingLeftImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_moving_left1),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardMovingLeftImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_moving_left2),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardMovingRightImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_moving_right1),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mFootboardMovingRightImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.footboard_moving_right2),
                GameUi.BORDER_ATTRIBUTE_IMAGE_WIDTH,
                GameUi.BORDER_ATTRIBUTE_IMAGE_HEITH, true);
        mRoleDeadmanImage = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_deadman),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleStandImage = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_standing),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleFreefallImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_freefall1),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleFreefallImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_freefall2),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleFreefallImage3 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_freefall3),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleFreefallImage4 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_freefall4),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingLeftImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_left1),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingLeftImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_left2),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingLeftImage3 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_left3),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingLeftImage4 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_left4),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingRightImage1 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_right1),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingRightImage2 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_right2),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingRightImage3 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_right3),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mRoleMovingRightImage4 = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeResource(res, R.drawable.role_moving_right4),
                GameUi.ROLE_ATTRIBUTE_WIDTH, GameUi.ROLE_ATTRIBUTE_HEITH,
                true);
        mBackgroundImage = BitmapFactory
                .decodeResource(res, R.drawable.b_bg);

        mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage,
                point.x,point.y, true);
        mGameUi = new GameUi(mScreenAttribute);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {}





    /**
     *
     *
     *
     *
     *
     * 保存游戏数据应该是Gameui所以gameui应该是成员变量而不是线程的成员变量
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("李闯", "surfaceChanged: ");

        gameThread = new MyGameThread(surfaceHolder,mContext);
        gameThread.setIsRun(true);

        gameThread.start();


    }




    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setIsRun(false);
    }

    public class MyGameThread extends Thread {
        private String TAG = "lichuang" ;
        private Context mContext ;
        private SurfaceHolder mSurfaceHolder;
        private boolean isRun = true;

        public MyGameThread(SurfaceHolder mSurfaceHolder ,Context mContext ) {
            this.mContext = mContext;
            this.mSurfaceHolder = mSurfaceHolder;
        }

        @Override
        public void run() {
            while (isRun){
                Canvas canvas = null;

                long time = System.currentTimeMillis();
                mGameUi.updateGameUi();
                try {
                    canvas = mSurfaceHolder.lockCanvas(null);
                    synchronized (mSurfaceHolder) {
                        doDrawCanvas(canvas);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (canvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }
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





        private void doDrawCanvas(Canvas canvas){


            canvas.drawBitmap(mBackgroundImage, 0, 0, new Paint());
            drawFootboards(canvas);
            drawRole(canvas);
        }

        /**
         * 画踏板
         * @param canvas
         */
        private void drawFootboards(Canvas canvas){
            Bitmap tempBitmap = null;
            List<Footboard> footboards = mGameUi.getFootboardUIObjects();
            for (Footboard footboard : footboards) {
                switch (footboard.getType()) {
                    case GameUi.FOOTBOARD_TYPE_UNSTABLE:
                        if (!footboard.isMarked()) {
                            tempBitmap = mFootboardUnstableImage1;
                        } else {
                            tempBitmap = mFootboardUnstableImage2;
                        }
                        break;
                    case GameUi.FOOTBOARD_TYPE_SPRING:
                        tempBitmap = mFootboardSpringImage;
                        break;
                    case GameUi.FOOTBOARD_TYPE_SPIKED:
                        tempBitmap = mFootboardSpikedImage;
                        break;
                    case GameUi.FOOTBOARD_TYPE_MOVING_LEFT:
                        if (footboard.nextFrame() == 0) {
                            tempBitmap = mFootboardMovingLeftImage1;
                        } else {
                            tempBitmap = mFootboardMovingLeftImage2;
                        }
                        break;
                    case GameUi.FOOTBOARD_TYPE_MOVING_RIGHT:
                        if (footboard.nextFrame() == 0) {
                            tempBitmap = mFootboardMovingRightImage1;
                        } else {
                            tempBitmap = mFootboardMovingRightImage2;
                        }
                        break;
                    default:
                        tempBitmap = mFootboardNormalImage;
                }
                canvas.drawBitmap(tempBitmap, footboard.getMinX(), footboard
                        .getMinY(), null);
            }

        }

        /**
         * 画小人
         * @param canvas
         */
        private void drawRole(Canvas canvas){
            Bitmap tempBitmap = null;
            Role role = mGameUi.getRoleUIObject();
//            if (mGameUi.mGameStatus == GameUi.GAME_STATUS_GAMEOVER) {
//                canvas.drawBitmap(mRoleDeadmanImage, role.getMinX(), role
//                        .getMinY(), null);
//            } else {
                switch (role.getRoleSharp()) {
                    case GameUi.ROLE_SHARP_FREEFALL_NO1:
                        tempBitmap = mRoleFreefallImage1;
                        break;
                    case GameUi.ROLE_SHARP_FREEFALL_NO2:
                        tempBitmap = mRoleFreefallImage2;
                        break;
                    case GameUi.ROLE_SHARP_FREEFALL_NO3:
                        tempBitmap = mRoleFreefallImage3;
                        break;
                    case GameUi.ROLE_SHARP_FREEFALL_NO4:
                        tempBitmap = mRoleFreefallImage4;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_LEFT_NO1:
                        tempBitmap = mRoleMovingLeftImage1;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_LEFT_NO2:
                        tempBitmap = mRoleMovingLeftImage2;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_LEFT_NO3:
                        tempBitmap = mRoleMovingLeftImage3;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_LEFT_NO4:
                        tempBitmap = mRoleMovingLeftImage4;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_RIGHT_NO1:
                        tempBitmap = mRoleMovingRightImage1;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_RIGHT_NO2:
                        tempBitmap = mRoleMovingRightImage2;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_RIGHT_NO3:
                        tempBitmap = mRoleMovingRightImage3;
                        break;
                    case GameUi.ROLE_SHARP_MOVE_RIGHT_NO4:
                        tempBitmap = mRoleMovingRightImage4;
                        break;
                    default:
                        tempBitmap = mRoleStandImage;
                }
                canvas.drawBitmap(tempBitmap, role.getMinX(), role.getMinY(),
                        null);
//            }

        }



        public void setIsRun(boolean isRun){
            this.isRun = isRun;
        }
    }

    public void handleMoving(float data_x_value) {
        if (mGameUi != null) {
            mGameUi.handleMoving(data_x_value);
        }
    }





}
