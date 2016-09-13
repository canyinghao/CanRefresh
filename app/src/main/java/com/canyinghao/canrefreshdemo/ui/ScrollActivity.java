package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.canyinghao.canrefreshdemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by canyinghao on 16/1/21.
 */
public class ScrollActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    Adapter adapter;
    @BindView(R.id.cd)
    View cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pager2);
        ButterKnife.bind(this);

        toolbar.setTitle(R.string.app_name);

        if (viewPager != null) {
            setupViewPager(viewPager);
        }


        tabLayout.setupWithViewPager(viewPager);


        tabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
             int fragment  =    cd.getHeight() -toolbar.getHeight() - tabLayout.getHeight();

                Log.e("Main","fragment "+fragment);

            }
        }, 2000);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getSupportFragmentManager());

        String[] strs = getResources().getStringArray(R.array.array_list2);





        adapter.addFragment(new ListViewFragment(), strs[0]);
        adapter.addFragment(new ScrollViewFragment(), strs[1]);


        viewPager.setAdapter(adapter);

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

}
