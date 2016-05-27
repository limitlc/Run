package com.paxw.run;

/**
 * 
 * @author Byronlee
 * 
 */

public class Footboard {

	private int mX;
	private int mVirtualY;
	private int mWidth;
	private int mHeith;
	private int mType;
	private int mFrameAmount;
	private int mFrameDelay;
	private int mFrameCounter;
	private int mUnstableBoardDelay;

	public int getVirtualY() {
		return mVirtualY;
	}

	public int getMinX() {
		return mX;
	}

	public int getMaxX() {
		return mX + mWidth;
	}

	public int getMinY() {
		return mVirtualY / GameUi.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
	}

	public int getMaxY() {
		return mVirtualY / GameUi.GAME_ATTRIBUTE_PIXEL_DENSITY_Y + mHeith;
	}

	public int getType() {
		return mType;
	}

	public void addY(int virtualPixel) {
		mVirtualY += virtualPixel;
	}

	public boolean isBoardBreak() {
		return --mUnstableBoardDelay == 0;
	}

	public boolean isMarked() {
		return mUnstableBoardDelay != GameUi.BOARD_ATTRIBUTE_UNSTABLE_DELAY_FACTOR;
	}

	public int nextFrame() {
		try {
			return mFrameCounter / mFrameDelay;
		} finally {
			mFrameCounter++;
			if (mFrameCounter == mFrameAmount * mFrameDelay) {
				mFrameCounter = 0;
			}
		}
	}

    /**
     *
     * @param x 位置
     * @param y
     * @param width
     * @param heith
     * @param type
     * @param frameAmount
     * @param frameDelay
     */
	public Footboard(int x, int y, int width, int heith, int type,
					 int frameAmount, int frameDelay) {
		mX = x;
		mVirtualY = y * GameUi.GAME_ATTRIBUTE_PIXEL_DENSITY_Y;
		mWidth = width;
		mHeith = heith;
		mType = type;
		mFrameAmount = frameAmount;
		mFrameDelay = frameDelay;
		mUnstableBoardDelay = GameUi.BOARD_ATTRIBUTE_UNSTABLE_DELAY_FACTOR;
		mFrameCounter = 0;
	}

}
