package com.paxw.run;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by lichuang on 2016/5/25.
 */
public class GameUi {
    // TODO: 2016/5/25 长度是否合适是根据图片还是根据屏幕才是合适的

    /**
     * 踏板高
     */
    public static final int BORDER_ATTRIBUTE_IMAGE_HEITH = 20;
    /**
     * 踏板宽
     */
    public static final int BORDER_ATTRIBUTE_IMAGE_WIDTH = 100;

    // 踏板类别
    // FIXME: 2016/5/26 是不是可以用枚举emnu
    /**
     * 正常踏板
     */
    public static final int FOOTBOARD_TYPE_NORMAL = 0;  //正常，标准，normal
    public static final int FOOTBOARD_TYPE_UNSTABLE = 1;  //不稳定的，动荡的，unstable
    public static final int FOOTBOARD_TYPE_SPRING = 2;     //弹跳的  spring
    public static final int FOOTBOARD_TYPE_SPIKED = 3;   //尖的，有锯齿状的，spiked
    public static final int FOOTBOARD_TYPE_MOVING_LEFT = 4;   //移动的，moving
    public static final int FOOTBOARD_TYPE_MOVING_RIGHT = 5;

    // 帧刷新间隔(单位微妙)
    public static final int GAME_FRAME_DTIME = 30;


    /**
     * 踏板移动的速度
     */
    private int mFootboartVelocity = -4 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;


    /**
     *  游戏活动对象Y方向的像素密度(将1个单位像素拆分为更小单元)
      */
    public static final int GAME_ATTRIBUTE_PIXEL_DENSITY_Y = 10;

    /**
     * 不稳定踏板滞留因数(可滞留时间=滞留因数*帧刷新间隔)
     */
    public static final int BOARD_ATTRIBUTE_UNSTABLE_DELAY_FACTOR = 10;


    /**
     * 主角状态
     */
    public static final int ROLE_STATUS_ON_FOOTBOARD = 0;
    public static final int ROLE_STATUS_ON_FOOTBOARD_LEFT = 1;  //footboard  踏足板；（床架底部的）竖板
    public static final int ROLE_STATUS_ON_FOOTBOARD_RIGHT = 2;
    public static final int ROLE_STATUS_FREEFALL = 3;   //freefall  自由下坠，自由下落
    public static final int ROLE_STATUS_FREEFALL_LEFT = 4;
    public static final int ROLE_STATUS_FREEFALL_RIGHT = 5;

    // 主角帧
    public static final int ROLE_SHARP_STANDING = 0;
    public static final int ROLE_SHARP_FREEFALL_NO1 = 1;   //freefall  自由下坠，自由下落
    public static final int ROLE_SHARP_FREEFALL_NO2 = 2;
    public static final int ROLE_SHARP_FREEFALL_NO3 = 3;
    public static final int ROLE_SHARP_FREEFALL_NO4 = 4;
    public static final int ROLE_SHARP_MOVE_LEFT_NO1 = 5;
    public static final int ROLE_SHARP_MOVE_LEFT_NO2 = 6;
    public static final int ROLE_SHARP_MOVE_LEFT_NO3 = 7;
    public static final int ROLE_SHARP_MOVE_LEFT_NO4 = 8;
    public static final int ROLE_SHARP_MOVE_RIGHT_NO1 = 9;
    public static final int ROLE_SHARP_MOVE_RIGHT_NO2 = 10;
    public static final int ROLE_SHARP_MOVE_RIGHT_NO3 = 11;
    public static final int ROLE_SHARP_MOVE_RIGHT_NO4 = 12;
    //velocity  速率；迅速；周转率

    public static final int BOARD_ATTRIBUTE_LEFT_VELOCITY = -8;
    public static final int BOARD_ATTRIBUTE_RIGHT_VELOCITY = 8;

    /**
     * 主角属性常量
     */
    // FIXME: 2016/5/25 猪脚的常量是不是要根据屏幕来设置
    public static final int ROLE_ATTRIBUTE_WIDTH = 32;
    public static final int ROLE_ATTRIBUTE_HEITH = 48;
    /**
     * 主角刷新间隔
     */
    public static final int ROLE_ATTRIBUTE_FRAME_DELAY = 2;


    /**
     * 主角X方向移动速度
     */
    private int mRoleVelocityX;

    /**
     * 主角Y方向移动速度
     */
    private int mRoleVelocityY;
    /**
     * 重力速度(即主角离开踏板后的y方向速度)
     */
    public static final int GAME_ATTRIBUTE_GRAVITY_VELOCITY = 7 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y;


    private LinkedList<Footboard> mFootboardList;
    private ScreenAttribute screenAttribute;

    public GameUi(ScreenAttribute screenAttribute) {
        this.screenAttribute = screenAttribute;
       initList();
        mRole = new Role((screenAttribute.maxX - ROLE_ATTRIBUTE_WIDTH) / 2,

                screenAttribute.maxY * 3 / 4,

                ROLE_ATTRIBUTE_WIDTH,

                ROLE_ATTRIBUTE_HEITH,

                ROLE_ATTRIBUTE_FRAME_DELAY
        );

        mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;

    }

    /**
     * 初始化踏板集合并添加一个踏板
     *
     */
    private void initList(){
        mFootboardList = new LinkedList<Footboard>();
        mFootboardList.add(new Footboard(
                (screenAttribute.maxX - BORDER_ATTRIBUTE_IMAGE_WIDTH) / 2,
                screenAttribute.maxY, BORDER_ATTRIBUTE_IMAGE_WIDTH,
                BORDER_ATTRIBUTE_IMAGE_HEITH,
                FOOTBOARD_TYPE_NORMAL,
                1,
                1));
    }

    public List<Footboard> getFootboardUIObjects(){
        return mFootboardList;
    }

    /**
     * 更新UI模型
     */
    public void updateGameUi() {
        updateFootBoardsUi();
        updateRoleUi();
        handleBorder();
        handleRoleAction();

    }

    /**
     * 更新主角ui
     */
    private void updateRoleUi() {
        /**
         *
         * //主角mY轴上的速度RoleVelocityY  =重力速度   此类中预定义 的 为50
         * mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
         *
         */
        mRole.addX(mRoleVelocityX);
        mRole.addY(mRoleVelocityY);
    }
    /**
     * 处理边界
     * 对一个时刻的 游戏数据和状态的判断和处理
     */
    private void handleBorder() {

        if (mFootboardList.size() > 0
                && mFootboardList.getFirst().getMaxY() <= screenAttribute.minY) {
            //如果跳板的在Y轴上超过了 上边界，就移除
            mFootboardList.remove();
        }

        if (mRole.getMinY() <= screenAttribute.minY) {


        }

        //如果主角顶部超过了底边界，生命值HP， 结束生命，改变游戏状态
        if (mRole.getMinY() > screenAttribute.maxY) {

        }

        //处理游戏左边界
        if (mRole.getMinX() < screenAttribute.minX) {
            mRoleVelocityX = 0;
            mRole.setX(0);
            return;
        }
        //处理游戏右边界
        if (mRole.getMaxX() > screenAttribute.maxX) {
            mRoleVelocityX = 0;
            mRole.setX(screenAttribute.maxX - ROLE_ATTRIBUTE_WIDTH);
            return;
        }
    }

    /**
     * 处理主角在踏板上的活动
     */
    private void handleRoleAction() {
        Role role = mRole;
        for (Footboard footboard : mFootboardList) {

            if ((role.getMaxY() >= footboard.getMinY() && role.getMaxY() < footboard.getMaxY())
                    && (role.getMaxX() > footboard.getMinX() && role.getMinX() < footboard
                    .getMaxX())) {   // 判断是在跳板上
                if (role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD
                        || role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_RIGHT
                        || role.getRoleStatus() == ROLE_STATUS_ON_FOOTBOARD_LEFT) {

                    if (footboard.getType() == FOOTBOARD_TYPE_SPRING) {
                        mRoleVelocityY = mFootboartVelocity
                                - GAME_ATTRIBUTE_GRAVITY_VELOCITY;
                        role.addY(-1 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
                        updateRoleStatus(ROLE_STATUS_FREEFALL);
                        return;
                    }
                    if (footboard.getType() == FOOTBOARD_TYPE_MOVING_LEFT) {
                        role.addX(BOARD_ATTRIBUTE_LEFT_VELOCITY);
                    } else if (footboard.getType() == FOOTBOARD_TYPE_MOVING_RIGHT) {
                        role.addX(BOARD_ATTRIBUTE_RIGHT_VELOCITY);
                    } else if (footboard.getType() == FOOTBOARD_TYPE_UNSTABLE
                            && footboard.isBoardBreak()) {
                        mFootboardList.remove(footboard);
                    }
                    updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
                } else {
                    // 主角第一次触板
//                    mScore += mLevel;
                    mRoleVelocityY = mFootboartVelocity;
                    role.setVirtualY(footboard.getVirtualY()
                            - ROLE_ATTRIBUTE_HEITH
                            * GAME_ATTRIBUTE_PIXEL_DENSITY_Y);
                    if (footboard.getType() == FOOTBOARD_TYPE_SPIKED) {
//                        mHP -= 3;
                    }
//                    else if (mHP < ROLE_ATTRIBUTE_HP_MAX) {
//                        mHP += 1;
//                    }
//                    if (mHP <= 0) {
//                        mGameStatus = GAME_STATUS_GAMEOVER;
//                    }
                    updateRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
                    switch (footboard.getType()) {
                        case FOOTBOARD_TYPE_UNSTABLE:
//                            mEffectFlag = EFFECT_FLAG_UNSTABLE;
                            break;
                        case FOOTBOARD_TYPE_SPRING:
//                            mEffectFlag = EFFECT_FLAG_SPRING;
                            break;
                        case FOOTBOARD_TYPE_SPIKED:
//                            mEffectFlag = EFFECT_FLAG_SPIKED;
                            break;
                        case FOOTBOARD_TYPE_MOVING_LEFT:
                        case FOOTBOARD_TYPE_MOVING_RIGHT:
//                            mEffectFlag = EFFECT_FLAG_MOVING;
                            break;
                        default:
//                            mEffectFlag = EFFECT_FLAG_NORMAL;
                    }
                }
                return;
            }
        }
        if (mRoleVelocityY < mFootboartVelocity) {
            mRoleVelocityY += 3;
        } else {
            mRoleVelocityY = GAME_ATTRIBUTE_GRAVITY_VELOCITY;
        }
        updateRoleStatus(ROLE_STATUS_FREEFALL);
    }


    // 附加速度(用于控制速度,在选项面板里设定)
    private int mAddVelocity = 0;

    /**
     * 处理主角移动
     *
     * @powered by Byronlee
     * mAddVelocity 额外加的速度，在设置里面设置
     */
    public void handleMoving(float angleValue) {
        if (angleValue < -5) {
            mRoleVelocityX = 10 + mAddVelocity;
        } else if (angleValue >= -5 && angleValue < -4) {
            mRoleVelocityX = 8 + mAddVelocity;
        } else if (angleValue >= -4 && angleValue < -3) {
            mRoleVelocityX = 6 + mAddVelocity;
        } else if (angleValue >= -3 && angleValue < -2) {
            mRoleVelocityX = 5 + mAddVelocity;
        } else if (angleValue >= -2 && angleValue < -1.5) {
            mRoleVelocityX = 4 + mAddVelocity;
        } else if (angleValue >= -1.5 && angleValue < 1.5) {
            mRoleVelocityX = 0;
        } else if (angleValue >= 1.5 && angleValue < 2) {
            mRoleVelocityX = -4 - mAddVelocity;
        } else if (angleValue >= 2 && angleValue < 3) {
            mRoleVelocityX = -5 - mAddVelocity;
        } else if (angleValue >= 3 && angleValue < 4) {
            mRoleVelocityX = -6 - mAddVelocity;
        } else if (angleValue >= 4 && angleValue < 5) {
            mRoleVelocityX = -8 - mAddVelocity;
        } else if (angleValue > 5) {
            mRoleVelocityX = -10 - mAddVelocity;
        }
    }

    /**
     * 垂直方向的距离
     */
    private int mFootboardSpaceFactor = 120 * GAME_ATTRIBUTE_PIXEL_DENSITY_Y; //10

    private int mFootboardSpaceCounter = 0;

    /**
     * 更新一下踏板的ui模型   并随机生成踏板
     */
    private void updateFootBoardsUi() {
        for (Footboard footboard : mFootboardList) {
            footboard.addY(mFootboartVelocity);
        }
        //达到要添加踏板的条件

        mFootboardSpaceCounter = mFootboardSpaceCounter - mFootboartVelocity;
        if (mFootboardSpaceCounter >= mFootboardSpaceFactor) {
            mFootboardSpaceCounter -= mFootboardSpaceFactor;
            generateFootboard();
        }
    }

    // 随机数生成器
    private Random mRan = new Random();

    /**
     *
     * 随机生成踏板,生成后，就加入到了 跳板 集合链表里面的了
     */
    private void generateFootboard() {
        int frameAmount = 1;
        int frameDelay = 1;
        int frameType = FOOTBOARD_TYPE_NORMAL;
        switch (mRan.nextInt(20)) {
            case 0:
            case 1:
            case 2:
                frameType = FOOTBOARD_TYPE_UNSTABLE;
                break;
            case 3:
            case 4:
            case 5:
                frameType = FOOTBOARD_TYPE_SPRING;
                break;
            case 6:
            case 7:
            case 8:
                frameType = FOOTBOARD_TYPE_SPIKED;
                break;
            case 9:
            case 10:
                frameType = FOOTBOARD_TYPE_MOVING_LEFT;
                frameAmount = 2;
                frameDelay = 15;
                break;
            case 11:
            case 12:
                frameType = FOOTBOARD_TYPE_MOVING_RIGHT;
                frameAmount = 2;
                frameDelay = 15;
                break;
            default:
                frameType = FOOTBOARD_TYPE_NORMAL;
        }
        mFootboardList.add(new Footboard(
                mRan.nextInt(screenAttribute.maxX- BORDER_ATTRIBUTE_IMAGE_WIDTH),
                screenAttribute.maxY,
                BORDER_ATTRIBUTE_IMAGE_WIDTH,
                BORDER_ATTRIBUTE_IMAGE_HEITH,
                frameType,
                frameAmount,
                frameDelay));
    }


    //更新主角的状态，在主角是下降的过程中，判断是向左下降还是向右下降
    //在跳板上，判断是向做运动还是向右运动
    private void updateRoleStatus(int status) {
        if (status == ROLE_STATUS_FREEFALL) {
            if (mRoleVelocityX > 0) {
                mRole.setRoleStatus(ROLE_STATUS_FREEFALL_RIGHT);
            } else if (mRoleVelocityX < 0) {
                mRole.setRoleStatus(ROLE_STATUS_FREEFALL_LEFT);
            } else {
                mRole.setRoleStatus(ROLE_STATUS_FREEFALL);
            }
        } else {
            if (mRoleVelocityX > 0) {
                mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_RIGHT);
            } else if (mRoleVelocityX < 0) {
                mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD_LEFT);
            } else {
                mRole.setRoleStatus(ROLE_STATUS_ON_FOOTBOARD);
            }
        }
    }

    /**
     * 清除操作
     */
    public void destroy() {
        screenAttribute = null;
//        mRole = null;
        mRan = null;
        mFootboardList.clear();
        mFootboardList = null;
    }

    private Role mRole;

    /**
     *
     * @return 主角对象
     */
    public Role getRoleUIObject() {
        return mRole;
    }
}
