/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.canyinghao.canrefresh.classic;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.canyinghao.canrefresh.CanRefresh;
import com.canyinghao.canrefresh.R;



public class RotateRefreshView extends FrameLayout implements CanRefresh {


    private final int ROTATION_ANIMATION_DURATION = 1200;

    private final Animation mRotateAnimation;


    private ImageView ivRotate;

    private  int rotateHight ;


    public RotateRefreshView(Context context) {
        this(context, null);
    }

    public RotateRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotateHight=(int) getResources().getDimension(R.dimen.refresh_height_rotate);

       View v=  LayoutInflater.from(context).inflate(R.layout.layout_rotate_refresh,null);

        addView(v);



        FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.height = rotateHight;
        v.setLayoutParams(params);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ivRotate = (ImageView) findViewById(R.id.ivRotate);


        ivRotate.setScaleType(ScaleType.MATRIX);


        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

    }

    @Override
    public void onPrepare() {

    }


  

  

    @Override
    public void onRelease() {
        ivRotate.startAnimation(mRotateAnimation);
    }

    @Override
    public void onComplete() {
        
    }

    @Override
    public void onPositionChange(float currentPercent) {



        ViewCompat.setRotation(ivRotate,currentPercent*rotateHight);
        
    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {

    }

  

    @Override
    public void onReset() {
        resetImageRotation();
    }


    private void resetImageRotation() {

        ivRotate.clearAnimation();

    }

}
