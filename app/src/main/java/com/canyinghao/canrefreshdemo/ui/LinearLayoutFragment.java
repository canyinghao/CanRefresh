package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefreshdemo.R;

import androidx.fragment.app.Fragment;


/**
 * Created by canyinghao on 16/1/24.
 */
public class LinearLayoutFragment extends Fragment implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {

   

    CanRefreshLayout refresh;


  


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_linear, container, false);
        refresh = v.findViewById(R.id.refresh);
        initView(v);
        return v;
    }

    private void initView(View v) {






        refresh.setOnLoadMoreListener(this);
        refresh.setOnRefreshListener(this);
    }



    @Override
    public void onLoadMore() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.loadMoreComplete();
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.refreshComplete();
            }
        }, 2000);
    }


}
