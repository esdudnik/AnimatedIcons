package com.android.esdudnik.animatedicons.ui.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.android.esdudnik.animatedicons.R;

/**
 * author   : Eugene Dudnik
 * date     : 1/9/17
 * e-mail   : esdudnik@gmail.com
 */
public class AnimatedCheckMarkView extends View {

    private static final int DEFAULT_FILL_COLOR = Color.GREEN;
    private static final int DEFAULT_STROKE_WIDTH = 2;
    private static final int DEFAULT_ANIMATION_TIME = 1000;

    private int mAnimationTime;
    private float mStrokeWidth;
    private int mFillColor;

    private Path mCheckMarkPath;
    private Paint mPaint;
    private float mLength;

    private boolean mClearCanvas = false;

    ObjectAnimator mCheckMarkAnimator;

    private onAnimatorListener mOnAnimatorListener;

    public AnimatedCheckMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init();
    }

    public AnimatedCheckMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init();
    }

    public void setOnAnimatorListener(onAnimatorListener onAnimatorListener) {
        this.mOnAnimatorListener = onAnimatorListener;
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
            mCheckMarkPath.reset();
        }
        c.drawPath(mCheckMarkPath, mPaint);
    }

    //is called by mCheckMarkAnimator object
    public void setPhase(float phase) {
        mPaint.setPathEffect(createPathEffect(mLength, phase, 0.0f));
        invalidate();
    }

    public void startAnimator() {
        clearCanvas();
        setCheckMarkPath();
        mCheckMarkAnimator.start();
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

        mCheckMarkPath = new Path();

        mCheckMarkAnimator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f);
        mCheckMarkAnimator.setDuration(mAnimationTime);
        mCheckMarkAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mOnAnimatorListener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void setCheckMarkPath() {
        int height = getHeight();
        int width = getWidth();
        int centerX = width / 2;
        int centerY = height / 2;
        mCheckMarkPath.moveTo(centerX - centerX / 2, centerY + centerY / 4);
        mCheckMarkPath.lineTo(centerX - centerX / 4, centerY + centerY / 2);
        mCheckMarkPath.lineTo(centerX + centerX / 2, centerY - centerY / 3);

        // Measure the path
        PathMeasure measure = new PathMeasure(mCheckMarkPath, false);
        mLength = measure.getLength();
    }

    private static PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                Math.max(phase * pathLength, offset));
    }

    private void clearCanvas() {
        mClearCanvas = true;
        invalidate();
    }

    interface onAnimatorListener {
        void onAnimationEnd();
    }

}
