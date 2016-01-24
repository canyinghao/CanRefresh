package com.canyinghao.canrefresh.yalantis;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.canyinghao.canrefresh.CanRefresh;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.R;

/**
 * Created by Aspsine on 2015/11/5.
 */
public class YalantisPhoenixRefreshView extends FrameLayout implements CanRefresh {

    private ImageView ivRefresh;

    private BaseRefreshDrawable mDrawable;

    private int mTriggerOffset;

    View v;

    public YalantisPhoenixRefreshView(Context context) {
        this(context, null);
    }

    public YalantisPhoenixRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YalantisPhoenixRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTriggerOffset = getResources().getDimensionPixelOffset(R.dimen.refresh_height_yalantis);

         v = LayoutInflater.from(context).inflate(R.layout.layout_yalantis_refresh, null);


        addView(v);

        FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.height = mTriggerOffset;
        v.setLayoutParams(params);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);
        mDrawable = new SunRefreshDrawable(getContext(), this, mTriggerOffset, getScreenWidth(getContext()));
        ivRefresh.setBackgroundDrawable(mDrawable);


    }


    @Override
    public void onPrepare() {

    }

    private int mOldY = 0;


    @Override
    public void onRelease() {
        if (!mDrawable.isRunning()) {
            mDrawable.start();
        }


    }

    @Override
    public void onComplete() {


    }

    @Override
    public void onPositionChange(float currentPercent) {


        int delta = (int) (mTriggerOffset * currentPercent - mOldY);
        mDrawable.offsetTopAndBottom(delta);
        mDrawable.setPercent(currentPercent, true);
        mOldY = (int) (mTriggerOffset * currentPercent);

        if (currentPercent>1){

            FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
            params.height = (int) (mTriggerOffset*currentPercent);
            v.setLayoutParams(params);

            invalidate();
        }

    }

    @Override
    public void setIsHeaderOrFooter(boolean isHeader) {

        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup!=null&&viewGroup instanceof CanRefreshLayout){

            CanRefreshLayout canRefreshLayout = (CanRefreshLayout) viewGroup;

            if (isHeader){
                canRefreshLayout.setHeaderHeight(mTriggerOffset);
            }else{
                canRefreshLayout.setFooterHeight(mTriggerOffset);
            }
        }
    }


    @Override
    public void onReset() {


        FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
        params.height = mTriggerOffset;
        params.topMargin = 0;
        params.bottomMargin = 0;
        v.setLayoutParams(params);

        invalidate();

        mDrawable.stop();

    }

    private int getScreenWidth(Context context) {

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;


    }
}
