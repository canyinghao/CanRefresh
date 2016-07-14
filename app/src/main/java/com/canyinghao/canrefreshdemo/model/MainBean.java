package com.canyinghao.canrefreshdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by canyinghao on 16/1/21.
 */
public class MainBean {


    public String title ;
    public String content ;


    public static List<MainBean> getList(){

        List<MainBean>  list = new ArrayList<>();


        for (int i = 0; i < 50; i++) {

            MainBean  bean =   new MainBean();

            bean.title = "this is a canadapter title "+i;
            bean.content = "this is a canadapter content "+i;

            list.add(bean);

        }

        return  list;

    }

    public static List<MainBean> getMainList(){

        List<MainBean>  list = new ArrayList<>();


        MainBean  bean1 =   new MainBean();
        bean1.title = "不可滚动的视图";
        bean1.content = "TextView ImageView FrameLayout 等";

        MainBean  bean2 =   new MainBean();
        bean2.title = "可滚动的视图";
        bean2.content = "ListView ScrollView GridView 等";

        MainBean  bean3 =   new MainBean();
        bean3.title = "RecyclerView";
        bean3.content = "RecyclerView的各种刷新方式";

        MainBean  bean4 =   new MainBean();
        bean4.title = "CoordinatorLayout";
        bean4.content = "支持CoordinatorLayout";


        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);


        return  list;

    }

}
