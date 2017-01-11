package com.android.esdudnik.animatedicons.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.android.esdudnik.animatedicons.R;

/**
 * author   : Eugene Dudnik
 * date     : 1/9/17
 * e-mail   : esdudnik@gmail.com
 */
public class AnimatedCircleExplanationMarkView extends RelativeLayout {

    private AnimatedExplanationMarkView mAnimatedExplanationMarkView;
    private AnimatedCircleView mAnimatedCircleView;

    public AnimatedCircleExplanationMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimatedCircleExplanationMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.animated_circle_explanation_mark, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedIconView);
        boolean forceAnimate = a.getBoolean(R.styleable.AnimatedIconView_animated, false);
        float widthLine = a.getDimension(R.styleable.AnimatedIconView_fillMarkWidth, 1);
        mAnimatedExplanationMarkView = (AnimatedExplanationMarkView) findViewById(R.id.animated_explanation_mark);
        mAnimatedExplanationMarkView.setStrokeWidth(widthLine);
        mAnimatedCircleView = (AnimatedCircleView) findViewById(R.id.animated_circle);
        mAnimatedCircleView.setStrokeWidth(widthLine);
        mAnimatedExplanationMarkView.setOnAnimatorListener(new AnimatedExplanationMarkView.onAnimatorListener() {
            @Override
            public void onAnimationEnd() {
                mAnimatedCircleView.startAnimator();
            }
        });
        if (forceAnimate) {
            this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    startAnimator();
                }
            });
        }
    }

    public void startAnimator() {
        mAnimatedExplanationMarkView.startAnimator();
    }
}
