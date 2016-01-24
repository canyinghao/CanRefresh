package com.canyinghao.canrefresh.google;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.canyinghao.canrefresh.CanRefresh;
import com.canyinghao.canrefresh.R;


public class GoogleCircleHookRefreshView extends FrameLayout implements CanRefresh {
    private GoogleCircleProgressView progressView;



    public GoogleCircleHookRefreshView(Context context) {
        this(context, null);
    }

    public GoogleCircleHookRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GoogleCircleHookRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        View v = LayoutInflater.from(context).inflate(R.layout.layout_google_refresh,null);


        addView(v);

        FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.height = (int) getResources().getDimension(R.dimen.refresh_height_google);
        v.setLayoutParams(params);

    }


    @Override
    protected void onFinishInflate() {

        progressView = (GoogleCircleProgressView) findViewById(R.id.googleProgress);
        progressView.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_red,
                R.color.google_yellow,
                R.color.google_green);
        progressView.setStartEndTrim(0, (float) 0.75);



        super.onFinishInflate();





    }



    @Override
    public void onPrepare() {
        progressView.setStartEndTrim(0, (float) 0.75);
    }



    @Override
    public void onRelease() {
        progressView.start();
    }

    @Override
    public void onComplete() {
        progressView.stop();
    }

    @Override
    public void onPositionChange(float currentPercent) {

        float alpha = currentPercent;
        if (alpha>1){
            alpha =1;
        }
        Log.i("onSwipe", "alpha= " + alpha);
        ViewCompat.setAlpha(progressView, alpha);



        progressView.setProgressRotation(currentPercent);
    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {




    }


    @Override
    public void onReset() {
        ViewCompat.setAlpha(progressView, 1f);
    }

}
