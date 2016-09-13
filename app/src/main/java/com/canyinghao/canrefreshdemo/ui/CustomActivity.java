package com.canyinghao.canrefreshdemo.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.canyinghao.canrefresh.CanRefreshLayout;
import com.canyinghao.canrefresh.classic.ClassicRefreshView;
import com.canyinghao.canrefresh.classic.RotateRefreshView;
import com.canyinghao.canrefresh.google.GoogleCircleHookRefreshView;
import com.canyinghao.canrefresh.storehouse.StoreHouseRefreshView;
import com.canyinghao.canrefresh.yalantis.YalantisPhoenixRefreshView;
import com.canyinghao.canrefreshdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by canyinghao on 16/1/24.
 */
public class CustomActivity extends AppCompatActivity implements CanRefreshLayout.OnRefreshListener, CanRefreshLayout.OnLoadMoreListener{

    @BindView(R.id.refresh)
    CanRefreshLayout refresh;
    
    int headStyle;
    int headRefresh;
    int footStyle;
    int footRefresh;

    Dialog dialog;

    
    Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_linear);

        ButterKnife.bind(this);

        context =this;
        initView();
    }

  

    private void initView() {

        

        refresh.setOnLoadMoreListener(this);
        refresh.setOnRefreshListener(this);

//        showConfigDialog();
    }


    @OnClick({R.id.iv})
    public  void click(View v){

        showConfigDialog();


    }


    private void showConfigDialog() {
        if (dialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_config, null);
            initDialogView(view);
            dialog = new AlertDialog.Builder(this)
                    .setView(view)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            View header = findViewById(R.id.can_refresh_header);

                            refresh.removeView(header);


                            switch (headRefresh) {
                                case 0:
                                    header =    new ClassicRefreshView(context);

                                    break;
                                case 1:
                                    header =    new RotateRefreshView(context);
                                    break;

                                case 2:
                                    header =    new GoogleCircleHookRefreshView(context);
                                    break;
                                case 3:
                                    header =    new StoreHouseRefreshView(context);
                                    break;
                                case 4:
                                    header =    new YalantisPhoenixRefreshView(context);
                                    break;


                            }

                            header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            header.setId(R.id.can_refresh_header);
                            refresh.addView(header);

                            View footer = findViewById(R.id.can_refresh_footer);
                            refresh.removeView(footer);


                            switch (footRefresh) {
                                case 0:
                                    footer =    new ClassicRefreshView(context);

                                    break;
                                case 1:
                                    footer =    new RotateRefreshView(context);
                                    break;

                                case 2:
                                    footer =    new GoogleCircleHookRefreshView(context);
                                    break;
                                case 3:
                                    footer =    new StoreHouseRefreshView(context);
                                    break;
                                case 4:
                                    footer =    new YalantisPhoenixRefreshView(context);
                                    break;


                            }
                            footer.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

                            footer.setId(R.id.can_refresh_footer);
                            refresh.addView(footer);


                            refresh.invalidate();
                            refresh.requestLayout();
                            refresh.postInvalidate();

                            refresh.setStyle(headStyle,footStyle);



                        }
                    }).create();
        }
        dialog.show();
    }

    private void initDialogView(View view) {
        final RadioGroup rgHeaderStyle = (RadioGroup) view.findViewById(R.id.rgHeaderStyle);
        final RadioGroup rgHeaderRefresh = (RadioGroup) view.findViewById(R.id.rgHeaderRefresh);
        final RadioGroup rgFooterStyle = (RadioGroup) view.findViewById(R.id.rgFooterStyle);
        final RadioGroup rgFooterRefresh = (RadioGroup) view.findViewById(R.id.rgFooterRefresh);

        rgHeaderStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgHeaderStyle.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgHeaderStyle.getChildAt(i);
                    if (rb.isChecked()) {
                       
                        headStyle = i;
                        break;
                    }
                }
            }
        });

        rgHeaderRefresh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgHeaderRefresh.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgHeaderRefresh.getChildAt(i);
                    if (rb.isChecked()) {
                       headRefresh = i;
                        break;
                    }
                }
            }
        });


        rgFooterStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgFooterStyle.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgFooterStyle.getChildAt(i);
                    if (rb.isChecked()) {

                        footStyle = i;
                        break;
                    }
                }
            }
        });

        rgFooterRefresh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rgFooterRefresh.getChildCount(); i++) {
                    RadioButton rb = (RadioButton) rgFooterRefresh.getChildAt(i);
                    if (rb.isChecked()) {
                        footRefresh = i;
                        break;
                    }
                }
            }
        });

        

        ((RadioButton) rgHeaderStyle.getChildAt(headStyle)).setChecked(true);
        ((RadioButton) rgHeaderRefresh.getChildAt(headRefresh)).setChecked(true);
        ((RadioButton) rgFooterStyle.getChildAt(footStyle)).setChecked(true);
        ((RadioButton) rgFooterRefresh.getChildAt(footRefresh)).setChecked(true);
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
