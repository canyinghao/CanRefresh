package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;

import com.canyinghao.canrefreshdemo.R;

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

    }

    @Override
    public void initListener(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
