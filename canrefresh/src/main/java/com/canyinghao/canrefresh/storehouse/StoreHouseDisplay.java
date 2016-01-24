package com.canyinghao.canrefresh.storehouse;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class StoreHouseDisplay {

    public int SCREEN_WIDTH_PIXELS;
    public int SCREEN_HEIGHT_PIXELS;
    public float SCREEN_DENSITY;
    public int SCREEN_WIDTH_DP;
    public int SCREEN_HEIGHT_DP;


    private static StoreHouseDisplay instance;

    private StoreHouseDisplay(Context context) {


        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
    }

    public static StoreHouseDisplay getInstance(Context context) {
        if (instance == null) {
            instance = new StoreHouseDisplay(context);
        }


        return instance;
    }

    public int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

    public int designedDP2px(float designedDp) {
        if (SCREEN_WIDTH_DP != 320) {
            designedDp = designedDp * SCREEN_WIDTH_DP / 320f;
        }
        return dp2px(designedDp);
    }

    public void setPadding(final View view, float left, float top, float right, float bottom) {
        view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right), dp2px(bottom));
    }
}
