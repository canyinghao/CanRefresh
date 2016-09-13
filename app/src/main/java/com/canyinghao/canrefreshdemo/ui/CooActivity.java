package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.RotateRefreshView;
import com.canyinghao.canrefresh.shapeloading.ShapeLoadingRefreshView;
import com.canyinghao.canrefreshdemo.R;
import com.canyinghao.canrefreshdemo.model.MainBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by canyinghao on 16/6/29.
 */
public class CooActivity extends AppCompatActivity implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {


    @BindView(R.id.can_refresh_footer)
    RotateRefreshView canRefreshFooter;
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbarlayout)
    CollapsingToolbarLayout toolbarlayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.can_scroll_view)
    RecyclerView recyclerView;
    @BindView(R.id.can_content_view)
    CoordinatorLayout canContentView;
    @BindView(R.id.refresh)
    CanRefreshLayout refresh;
    CanRVAdapter adapter;
    @BindView(R.id.can_refresh_header)
    ShapeLoadingRefreshView canRefreshHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coo);
        ButterKnife.bind(this);


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
