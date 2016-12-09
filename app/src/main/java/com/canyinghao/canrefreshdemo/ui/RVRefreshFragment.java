package com.canyinghao.canrefreshdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.storehouse.StoreHouseRefreshView;
import com.canyinghao.canrefreshdemo.App;
import com.canyinghao.canrefreshdemo.R;
import com.canyinghao.canrefreshdemo.model.MainBean;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by canyinghao on 16/1/21.
 */
public class RVRefreshFragment extends Fragment implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener {


    public final static String TYPE = "type";

    @BindView(R.id.can_content_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    CanRefreshLayout refresh;
    CanRVAdapter adapter;

    int type;
    LinearLayoutManager mLayoutManager;

    public static RVRefreshFragment newInstance(int type) {
        RVRefreshFragment fragment = new RVRefreshFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(TYPE, 0);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int layoutId;
        switch (type) {
            case 0:
                layoutId = R.layout.fragment_rv_classic;
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                break;

            case 1:
                layoutId = R.layout.fragment_rv_google;
                mLayoutManager = new GridLayoutManager(getContext(), 2);
                break;
            case 2:
                layoutId = R.layout.fragment_rv_yalantis;
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                break;

            case 3:
                layoutId = R.layout.fragment_rv_store;
                mLayoutManager = new GridLayoutManager(getContext(), 3);
                break;

            default:
                layoutId = R.layout.fragment_rv_classic;
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                break;
        }


        View v = inflater.inflate(layoutId, container, false);
        ButterKnife.bind(this, v);


        if (type == 3) {

            StoreHouseRefreshView storeHouseRefreshView = (StoreHouseRefreshView) v.findViewById(R.id.can_refresh_header);

            storeHouseRefreshView.initWithString("Hello World");
        }

        initView();
        return v;


    }


    private void initView() {

        refresh.setOnLoadMoreListener(this);
        refresh.setOnRefreshListener(this);

        refresh.setMaxFooterHeight(300);
        refresh.setStyle(type, type);


        recyclerView.setLayoutManager(mLayoutManager);

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


        adapter.setOnItemListener(new CanOnItemListener() {

            public void onItemChildClick(View view, int position) {

                MainBean bean = (MainBean) adapter.getItem(position);
                switch (view.getId()) {


                    case R.id.tv_title:

                        App.getInstance().show(bean.title);
                        break;

                    case R.id.tv_content:
                        App.getInstance().show(bean.content);
                        break;
                }


            }

        });


        adapter.setList(MainBean.getList());


    }


    @Override
    public void onRefresh() {

        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setList(MainBean.getList());
                refresh.refreshComplete();
            }
        }, 1000);

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
}
