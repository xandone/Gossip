package com.example.xandone.gossip;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * author: xandone
 * created on: 2017/7/21 16:57
 */

public class GossipView extends View {
    private int mCircleL;
    private int mCircleM_white;
    private int mCircleM_black;
    private int mCircleS_white;
    private int mCircleS_black;

    private int mWidth;
    private int mHeight;

    private int mRadius_value;
    private boolean isSwitch;

    private Paint mPaint;
    private RectF mRect;

    private ValueAnimator mValueAnimator;
    private RotateAnimation mRotateAnimation;

    public static final int DURATION = 2000;


    public GossipView(Context context) {
        this(context, null);
    }

    public GossipView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GossipView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(DURATION);
        mRotateAnimation.setFillAfter(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircleL(canvas);
        drawCircleMB(canvas);
        drawCircleMW(canvas);
    }

    public void drawCircleL(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawArc(mRect, 0, 180, true, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(mRect, 0, -180, true, mPaint);
    }

    public void drawCircleMB(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mCircleM_black, mCircleL, mCircleM_black, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCircleM_black, mCircleL, mCircleS_black, mPaint);
    }

    public void drawCircleMW(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCircleL * 2 - mCircleM_white, mCircleL, mCircleM_white, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mCircleL * 2 - mCircleM_white, mCircleL, mCircleS_white, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        mCircleL = w > h ? h / 2 : w / 2;

        mCircleM_white = mCircleL / 2;
        mCircleS_white = mCircleM_white / 2;

        mCircleM_black = mCircleL / 2;
        mCircleS_black = mCircleM_black / 2;

        mRect = new RectF(0, 0, mCircleL * 2, mCircleL * 2);

        mValueAnimator = ValueAnimator.ofInt(0, mCircleL / 6);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(DURATION);
    }

    /**
     * 开启旋转动画
     */
    public void startRotate() {
        if (mRotateAnimation != null && mValueAnimator != null) {
            startAnimation(mRotateAnimation);
            mValueAnimator.start();
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRadius_value = (int) animation.getAnimatedValue();
                    if (mRadius_value == 0) {
                        isSwitch = !isSwitch;
                    }
                    if (isSwitch) {
                        mCircleM_white = mCircleL / 2 + mRadius_value;
                        mCircleM_black = mCircleL / 2 - mRadius_value;
                    } else {
                        mCircleM_white = mCircleL / 2 + mRadius_value;
                        mCircleM_black = mCircleL / 2 - mRadius_value;
                    }

                    Log.d("xandone", " w:" + mCircleM_white + "       b:" + mCircleM_black);
                    invalidate();
                }
            });
        }
    }

    /**
     * 停止旋转动画
     */
    public void stopRoate() {
        if (mRotateAnimation != null) {
            mRotateAnimation.cancel();
        }
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

    public void changeSize() {

    }

}
