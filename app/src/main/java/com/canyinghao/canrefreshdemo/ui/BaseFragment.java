package com.canyinghao.canrefreshdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public abstract class BaseFragment extends Fragment {

    public Activity context;

    //    加载在fragment中的根View
    public View rootView;

    public LayoutInflater mInflater;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (Activity) getActivity();
    }


    public abstract void initView(Bundle savedInstanceState);

    public abstract void initListener(Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = (Activity) getActivity();


        if (rootView == null) {
            this.mInflater = inflater;
            initView(savedInstanceState);
            initListener(savedInstanceState);
            initData(savedInstanceState);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);

        }


        return rootView;

    }


    /**
     * 设置fagment视图和activity用法一样必须在initView()方法中调用
     */
    public void setContentView(int layoutId) {
        rootView = mInflater.inflate(layoutId, null);
    }

    /**
     * 设置fagment视图和activity用法一样必须在initView()方法中调用
     */
    public void setContentView(View view) {
        rootView = view;
    }

    /**
     * 找到子视图
     */
    public View findViewById(int id) {
        return rootView.findViewById(id);

    }

    /**
     * 得到根视图
     */
    public View getRootView() {
        return rootView;
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        context = null;
        mInflater = null;
        rootView = null;


    }


}
