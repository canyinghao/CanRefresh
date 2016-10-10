package com.canyinghao.canrefresh.shapeloading;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.canyinghao.canrefresh.CanRefresh;
import com.canyinghao.canrefresh.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by canyinghao on 16/6/29.
 */
public class ShapeLoadingRefreshView extends FrameLayout implements CanRefresh {


    private static final int ANIMATION_DURATION = 500;


    private int MAX_DISTANCE = dip2px(55);

    private ShapeLoadingView mShapeLoadingView;

    private ImageView mIndicationIm;


    private boolean isStop;

    public float factor = 1.2f;


    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public ShapeLoadingRefreshView(Context context) {
        super(context);
    }

    public ShapeLoadingRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeLoadingRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_shape_refresh, null);


        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height = MAX_DISTANCE * 2;

        mShapeLoadingView = (ShapeLoadingView) view.findViewById(R.id.shapeLoadingView);

        mIndicationIm = (ImageView) view.findViewById(R.id.indication);


        addView(view, layoutParams);


    }


    /**
     * 上抛
     */
    public void upThrow() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "translationY", MAX_DISTANCE, 0);
        ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm, "scaleX", 0.2f, 1);


        ObjectAnimator objectAnimator1 = null;
        switch (mShapeLoadingView.getShape()) {
            case SHAPE_RECT:


                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView, "rotation", 0, -120);

                break;
            case SHAPE_CIRCLE:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView, "rotation", 0, 180);

                break;
            case SHAPE_TRIANGLE:

                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView, "rotation", 0, 180);

                break;
        }


        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator1.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new DecelerateInterpolator(factor));
        objectAnimator1.setInterpolator(new DecelerateInterpolator(factor));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.playTogether(objectAnimator, objectAnimator1, scaleIndication);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (isStop) {
                    return;
                }
                freeFall();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();


    }


    /**
     * 下落
     */
    public void freeFall() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mShapeLoadingView, "translationY", 0, MAX_DISTANCE);
        ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm, "scaleX", 1, 0.2f);


        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator(factor));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.playTogether(objectAnimator, scaleIndication);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if (isStop) {
                    return;
                }


                mShapeLoadingView.changeShape();
                upThrow();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();


    }


    public void startAnimation() {
        isStop = false;

        freeFall();


    }

    public void stopAnimation() {
        isStop = true;

    }

    public void setColors(int... colors) {

        mShapeLoadingView.setColors(colors);
    }


    Runnable restRun = new Runnable() {
        @Override
        public void run() {
            mShapeLoadingView.reset();
        }
    };

    @Override
    public void onReset() {

        mShapeLoadingView.removeCallbacks(restRun);
        mShapeLoadingView.postDelayed(restRun, 300);
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onRelease() {

        startAnimation();
    }

    @Override
    public void onComplete() {

        stopAnimation();

        mShapeLoadingView.removeCallbacks(restRun);
        mShapeLoadingView.postDelayed(restRun, 300);
    }


    @Override
    public void onPositionChange(float currentPercent) {


        float rotation = currentPercent * 360;
        if (rotation >= 360) {

            rotation = 360;
        }

        float distance = currentPercent * MAX_DISTANCE;
        if (distance >= MAX_DISTANCE) {

            distance = MAX_DISTANCE;
        }

        ViewCompat.setRotation(mShapeLoadingView, rotation);
        ViewCompat.setTranslationY(mShapeLoadingView, distance);


    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {

    }
}
