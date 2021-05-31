package com.canyinghao.canrefreshdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.canyinghao.canadapter.CanHolderHelper;
import com.canyinghao.canadapter.CanOnItemListener;
import com.canyinghao.canadapter.CanRVAdapter;
import com.canyinghao.canrefreshdemo.R;
import com.canyinghao.canrefreshdemo.model.MainBean;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by canyinghao on 16/1/24.
 */
public class MainActivity extends AppCompatActivity {



    Toolbar toolbar;


    RecyclerView viewPager;

    View cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        toolbar =  findViewById(R.id.toolbar);
        viewPager =  findViewById(R.id.viewPager);
        cd =  findViewById(R.id.cd);

        toolbar.setTitle("CanRefreshDemo");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        final CanRVAdapter adapter = new CanRVAdapter<MainBean>(viewPager, R.layout.item_main) {


            @Override
            protected void setView(CanHolderHelper helper, int position, MainBean model) {
                helper.setText(R.id.tv_title, model.title);
                helper.setText(R.id.tv_content, model.content);

            }

            @Override
            protected void setItemListener(CanHolderHelper helper) {

                helper.setItemChildClickListener(R.id.list_item);

            }
        };

        viewPager.setLayoutManager(mLayoutManager);
        viewPager.setAdapter(adapter);





        adapter.setList(MainBean.getMainList());





        adapter.setOnItemListener(new CanOnItemListener(){

            public void onItemChildClick(View view, int position){
                switch (position){

                    case  0:
                        startActivity(new Intent(MainActivity.this,ViewActivity.class));
                        break;
                    case  1:
                        startActivity(new Intent(MainActivity.this,ScrollActivity.class));
                        break;
                    case  2:

                        startActivity(new Intent(MainActivity.this,RvActivity.class));

                        break;

                    case  3:
                        startActivity(new Intent(MainActivity.this,CooActivity.class));
                        break;
                }



            }


        });
    }
}
