package com.canyinghao.canrefreshdemo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.RotateRefreshView;
import com.canyinghao.canrefresh.shapeloading.ShapeLoadingRefreshView;
import com.canyinghao.canrefreshdemo.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by canyinghao on 16/6/29.
 */
public class CooActivity2 extends AppCompatActivity implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {



    RotateRefreshView canRefreshFooter;

    ImageView header;

    Toolbar toolbar;

    CollapsingToolbarLayout toolbarlayout;

    AppBarLayout appbar;

    ViewPager viewPager;

    CoordinatorLayout canContentView;

    CanRefreshLayout refresh;


    ShapeLoadingRefreshView canRefreshHeader;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coo2);

        canRefreshFooter = findViewById(R.id.can_refresh_footer);
        header = findViewById(R.id.header);
        toolbar = findViewById(R.id.toolbar);
        toolbarlayout = findViewById(R.id.toolbarlayout);
        appbar = findViewById(R.id.appbar);
        viewPager = findViewById(R.id.can_scroll_view);
        canContentView = findViewById(R.id.can_content_view);
        refresh = findViewById(R.id.refresh);
        canRefreshHeader = findViewById(R.id.can_refresh_header);


        refresh.setOnLoadMoreListener(this);
        refresh.setOnRefreshListener(this);

        refresh.setRefreshBackgroundResource(R.color.google_yellow);
        refresh.setLoadMoreBackgroundResource(R.color.window_background);

        canRefreshHeader.setColors(getResources().getColor(R.color.color_button), getResources().getColor(R.color.color_text), getResources().getColor(R.color.color_red));





        Adapter  adapter = new Adapter(getSupportFragmentManager());

        String[] strs = getResources().getStringArray(R.array.array_list3);



        adapter.addFragment(TestFragment.newInstance(), strs[0]);
        adapter.addFragment(TestFragment1.newInstance(), strs[1]);
        adapter.addFragment(TestFragment.newInstance(), strs[2]);


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);


        viewPager.setCurrentItem(1);


    }



    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    @Override
    public void onLoadMore() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {


                refresh.loadMoreComplete();
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {

                refresh.refreshComplete();
            }
        }, 1500);
    }
}
