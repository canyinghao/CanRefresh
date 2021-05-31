package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.canyinghao.canrefreshdemo.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;


/**
 * Created by canyinghao on 16/1/21.
 */
public class RvActivity extends AppCompatActivity {



    Toolbar toolbar;

    TabLayout tabLayout;

    ViewPager viewPager;


    AppBarLayout appbar;

    Adapter adapter;

    View cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appbar = findViewById(R.id.appbar);
        cd = findViewById(R.id.cd);

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

        String[] strs = getResources().getStringArray(R.array.array_list3);



        adapter.addFragment(RVRefreshFragment1.newInstance(0), strs[0]);
        adapter.addFragment(RVRefreshFragment.newInstance(1), strs[1]);
        adapter.addFragment(RVRefreshFragment.newInstance(2), strs[2]);
        adapter.addFragment(RVRefreshFragment.newInstance(3), strs[3]);

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
