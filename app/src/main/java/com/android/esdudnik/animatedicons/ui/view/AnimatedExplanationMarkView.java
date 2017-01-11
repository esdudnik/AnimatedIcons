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
import android.graphics.PorterDuff;
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
public class AnimatedExplanationMarkView extends View {

    private static final int DEFAULT_FILL_COLOR = Color.GREEN;
    private static final int DEFAULT_STROKE_WIDTH = 2;
    private static final int DEFAULT_ANIMATION_TIME = 500;

    private int mAnimationTime;
    private float mStrokeWidth;
    private int mFillColor;

    private Path mExplanationMarkPath;
    private Paint mPaint;
    private Paint mFillPaint;
    private float mLength;
    private float mCircleCenterX;
    private float mCircleCenterY;

    private boolean mClearCanvas = false;

    ObjectAnimator mExplanationMarkAnimator;

    private onAnimatorListener mOnAnimatorListener;

    public AnimatedExplanationMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
        init();
    }

    public AnimatedExplanationMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
        init();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    public void setOnAnimatorListener(onAnimatorListener onAnimatorListener) {
        this.mOnAnimatorListener = onAnimatorListener;
    }

    @Override
    public void onDraw(Canvas c) {
        if (isInEditMode()) return;
        super.onDraw(c);
        if (mClearCanvas) {
            mClearCanvas = false;
            c.drawColor(0, PorterDuff.Mode.CLEAR);
            mExplanationMarkPath.reset();
        }
        c.drawCircle(mCircleCenterX, mCircleCenterY, mStrokeWidth * 2 / 3, mFillPaint);
        c.drawPath(mExplanationMarkPath, mPaint);
    }

    public void startAnimator() {
        clearCanvas();
        setCheckMarkPath();
        mExplanationMarkAnimator.start();
    }

    //is called by mCheckMarkAnimator object
    public void setPhase(float phase) {
        mPaint.setPathEffect(createPathEffect(mLength, phase, 0.0f));
        invalidate();
    }

    private void getAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedIconView);
        mFillColor = a.getColor(R.styleable.AnimatedIconView_fillMarkColor, DEFAULT_FILL_COLOR);
        mStrokeWidth = a.getDimension(R.styleable.AnimatedIconView_fillMarkWidth, DEFAULT_STROKE_WIDTH);
        mAnimationTime = a.getInt(R.styleable.AnimatedIconView_animationTime, DEFAULT_ANIMATION_TIME);
        a.recycle();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        mPaint.setColor(mFillColor);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mFillPaint = new Paint();
        mFillPaint.setColor(mFillColor);
        mFillPaint.setStrokeWidth(mStrokeWidth);
        mFillPaint.setStyle(Paint.Style.FILL);

        mExplanationMarkPath = new Path();

        mExplanationMarkAnimator = ObjectAnimator.ofFloat(this, "phase", 1.0f, 0.0f);
        mExplanationMarkAnimator.setDuration(mAnimationTime);
        mExplanationMarkAnimator.addListener(new Animator.AnimatorListener() {
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

        mCircleCenterX = width / 2;
        mCircleCenterY = 3 * height / 4 + mStrokeWidth;

        mExplanationMarkPath.moveTo(width / 2, 5 * height / 8 + mStrokeWidth);
        mExplanationMarkPath.lineTo(width / 2, height / 4);

        // Measure the path
        PathMeasure measure = new PathMeasure(mExplanationMarkPath, false);
        mLength = measure.getLength();
    }

    private PathEffect createPathEffect(float pathLength, float phase, float offset) {
        return new DashPathEffect(new float[]{pathLength, pathLength},
                Math.max(phase * mLength, offset));
    }

    private void clearCanvas() {
        mClearCanvas = true;
        invalidate();
    }

    interface onAnimatorListener {
        void onAnimationEnd();
    }

}

