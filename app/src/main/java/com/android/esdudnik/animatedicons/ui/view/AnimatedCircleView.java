package com.android.esdudnik.animatedicons.ui.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import com.android.esdudnik.animatedicons.R;

/**
 * author   : Eugene Dudnik
 * date     : 1/9/17
 * e-mail   : esdudnik@gmail.com
 */
public class AnimatedCircleView extends View {

    private static final int DEFAULT_FILL_COLOR = Color.GREEN;
    private static final int DEFAULT_STROKE_WIDTH = 2;
    private static final int DEFAULT_ANIMATION_TIME = 500;
    private static final int START_ANGLE_COUNTER = 0;
    private static final int START_ANGLE_POINT = 270;
    private static final int END_ANGLE_COUNTER = 360;

    private int mAnimationTime;
    private float mStrokeWidth;
    private int mFillColor;

    private Path mCirclePath;
    private Paint mPaint;
    private RectF mRect;

    private boolean mClearCanvas = false;

    ObjectAnimator mCircleAnimator;

    public AnimatedCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init();
    }

    public AnimatedCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void onDraw(Canvas c) {
        if (isInEditMode()) return;
        super.onDraw(c);
        if (mClearCanvas) {
            mClearCanvas = false;
            mCirclePath.reset();
        }
        c.drawPath(mCirclePath, mPaint);
    }

    //is called by mCircleAnimator object
    public void setSweepAngle(float angle) {
        mCirclePath.rewind();
        mCirclePath.addArc(mRect, START_ANGLE_POINT, angle);
        invalidate();
    }

    public void startAnimator() {
        clearCanvas();
        setCirclePath();
        mCircleAnimator.start();
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedIconView);
        mFillColor = a.getColor(R.styleable.AnimatedIconView_fillMarkColor, DEFAULT_FILL_COLOR);
        mStrokeWidth = a.getDimension(R.styleable.AnimatedIconView_fillMarkWidth, DEFAULT_STROKE_WIDTH);
        mAnimationTime = a.getInt(R.styleable.AnimatedIconView_animationTime, DEFAULT_ANIMATION_TIME);
        a.recycle();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mFillColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirclePath = new Path();

        mCircleAnimator = ObjectAnimator.ofFloat(this, "sweepAngle", START_ANGLE_COUNTER, END_ANGLE_COUNTER);
        mCircleAnimator.setDuration(mAnimationTime);
    }

    private void setCirclePath() {
        int height = getHeight();
        int width = getWidth();
        int mCenterX = width / 2;
        int mCenterY = height / 2;
        float radius = Math.min(width, height) / 2 - (int) mStrokeWidth;
        mRect = new RectF(mCenterX - radius, mCenterY - radius, mCenterX + radius, mCenterY + radius);
    }

    private void clearCanvas() {
        mClearCanvas = true;
        invalidate();
    }

}
