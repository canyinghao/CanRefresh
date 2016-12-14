package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canrefreshdemo.R;

import butterknife.ButterKnife;

/**
 * Created by canyinghao on 2016/12/6.
 */

public class TestFragment extends BaseFragment {


    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();

        return fragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {

        setContentView(R.layout.fragment_test);
        ButterKnife.bind(this,rootView);
    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
