package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.RotateRefreshView;
import com.canyinghao.canrefresh.shapeloading.ShapeLoadingRefreshView;
import com.canyinghao.canrefreshdemo.R;
import com.canyinghao.canrefreshdemo.model.MainBean;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by canyinghao on 16/6/29.
 */
public class CooActivity extends AppCompatActivity implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {



    RotateRefreshView canRefreshFooter;

    ImageView header;

    Toolbar toolbar;

    CollapsingToolbarLayout toolbarlayout;

    AppBarLayout appbar;

    RecyclerView recyclerView;

    CoordinatorLayout canContentView;

    CanRefreshLayout refresh;
    CanRVAdapter adapter;

    ShapeLoadingRefreshView canRefreshHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coo);
        canRefreshFooter = findViewById(R.id.can_refresh_footer);
        header = findViewById(R.id.header);
        toolbar = findViewById(R.id.toolbar);
        toolbarlayout = findViewById(R.id.toolbarlayout);
        appbar = findViewById(R.id.appbar);
        recyclerView = findViewById(R.id.can_scroll_view);
        canContentView = findViewById(R.id.can_content_view);
        refresh = findViewById(R.id.refresh);
        canRefreshHeader = findViewById(R.id.can_refresh_header);


        refresh.setOnLoadMoreListener(this);
        refresh.setOnRefreshListener(this);

        refresh.setRefreshBackgroundResource(R.color.google_yellow);
        refresh.setLoadMoreBackgroundResource(R.color.window_background);

        canRefreshHeader.setColors(getResources().getColor(R.color.color_button), getResources().getColor(R.color.color_text), getResources().getColor(R.color.color_red));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CanRVAdapter<MainBean>(recyclerView, R.layout.item_main) {


            @Override
            protected void setView(CanHolderHelper helper, int position, MainBean model) {
                helper.setText(R.id.tv_title, model.title);
                helper.setText(R.id.tv_content, model.content);

            }

            @Override
            protected void setItemListener(CanHolderHelper helper) {

                helper.setItemChildClickListener(R.id.tv_title);
                helper.setItemChildClickListener(R.id.tv_content);

            }
        };

        recyclerView.setAdapter(adapter);

        adapter.setList(MainBean.getList());

    }


    @Override
    public void onLoadMore() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter.addMoreList(MainBean.getList());
                refresh.loadMoreComplete();
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setList(MainBean.getList());
                refresh.refreshComplete();
            }
        }, 1500);
    }
}
