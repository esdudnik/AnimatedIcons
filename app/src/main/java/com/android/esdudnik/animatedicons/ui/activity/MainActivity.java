package com.android.esdudnik.animatedicons.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.esdudnik.animatedicons.R;
import com.android.esdudnik.animatedicons.ui.view.AnimatedCheckMarkView;
import com.android.esdudnik.animatedicons.ui.view.AnimatedCircleCheckMarkView;
import com.android.esdudnik.animatedicons.ui.view.AnimatedCircleExplanationMarkView;
import com.android.esdudnik.animatedicons.ui.view.AnimatedCircleView;
import com.android.esdudnik.animatedicons.ui.view.AnimatedExplanationMarkView;

public class MainActivity extends AppCompatActivity {

    private AnimatedCircleCheckMarkView mAnimatedCircleCheckMarkView;
    private AnimatedCircleExplanationMarkView mAnimatedCircleExplanationMarkView;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnimatedCircleCheckMarkView = (AnimatedCircleCheckMarkView) findViewById(R.id.animatedCircleCheckMarkView);
        mAnimatedCircleExplanationMarkView = (AnimatedCircleExplanationMarkView) findViewById(R.id.animatedCircleExplanationMarkView);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimatedCircleCheckMarkView.startAnimator();
                mAnimatedCircleExplanationMarkView.startAnimator();
            }
        });
    }
}
