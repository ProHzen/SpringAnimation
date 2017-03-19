package com.bbk.yang.someexample;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yang on 2017/3/19.
 */

public class SpringAnimationActivity extends AppCompatActivity {

    public final static String TAG = SpringAnimationActivity.class.getSimpleName();

    @BindView(R.id.stiffness)
    SeekBar stiffness;
    @BindView(R.id.damping)
    SeekBar damping;
    @BindView(R.id.box)
    View box;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.tv_stiffness)
    TextView tvStiffness;
    @BindView(R.id.tv_damping)
    TextView tvDamping;
    @BindView(R.id.final_position)
    SeekBar finalPosition;
    @BindView(R.id.tv_spring)
    TextView tvSpring;
    @BindView(R.id.tv_reset)
    Button btnReset;

    private VelocityTracker velocityTracker;
    private float downX, downY;
    private SpringAnimation animationX, animationY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);
        ButterKnife.bind(this);

        findViewById(android.R.id.content).setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        velocityTracker = VelocityTracker.obtain();

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = motionEvent.getX();
                        downY = motionEvent.getY();
                        velocityTracker.addMovement(motionEvent);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        if (animationX != null) {
                            animationX.cancel();
                            animationY.cancel();
                        }
                        box.setTranslationX(motionEvent.getX() - downX);
                        box.setTranslationY(motionEvent.getY() - downY);
                        velocityTracker.addMovement(motionEvent);
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        velocityTracker.computeCurrentVelocity(1000);
                        if (box.getTranslationX() != 0) {
                            animationX = new SpringAnimation(box, SpringAnimation.TRANSLATION_X, 0);
                            animationX.getSpring().setStiffness(getStiffness());
                            animationX.getSpring().setDampingRatio(getDamping());
                            animationX.getSpring().setFinalPosition(getFinalPositionX());
                            animationX.setStartVelocity(velocityTracker.getXVelocity());
                            animationX.start();
                        }
                        if (box.getTranslationY() != 0) {
                            animationY = new SpringAnimation(box, SpringAnimation.TRANSLATION_Y, 0);
                            animationY.getSpring().setStiffness(getStiffness());
                            animationY.getSpring().setDampingRatio(getDamping());
//                            animationY.getSpring().setFinalPosition(getFinalPositionY());
                            animationY.setStartVelocity(velocityTracker.getYVelocity());
                            animationY.start();
                        }
                        velocityTracker.clear();
                        Log.d(TAG, " FinalPositionX = " + finalPosition.getProgress());

                        return true;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.stiffness, R.id.damping, R.id.box})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stiffness:
                break;
            case R.id.damping:
                break;
            case R.id.box:
                break;
        }
    }

    private float getStiffness() {
        return Math.max(stiffness.getProgress(), 1f);
    }

    private float getDamping() {
        return damping.getProgress() / 100f;
    }

    private float getFinalPositionX() {
        return finalPosition.getProgress() / 100f * getWindowManager().getDefaultDisplay().getWidth();
    }

    private float getFinalPositionY() {
        return finalPosition.getProgress() / 100f * getWindowManager().getDefaultDisplay().getHeight();
    }

    @OnClick(R.id.tv_reset)
    public void onClick() {
        if (animationX != null) {
            animationX.cancel();
            animationY.cancel();
        }
        damping.setProgress(0);
        stiffness.setProgress(0);
        finalPosition.setProgress(0);
    }
}
