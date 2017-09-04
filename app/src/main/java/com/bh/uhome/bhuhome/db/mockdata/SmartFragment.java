package com.bh.uhome.bhuhome.db.mockdata;

import com.bh.uhome.bhuhome.entity.HomeMenuInfo;

import java.util.ArrayList;

/**
 * @author 凌霄
 * @date 2017/8/31.
 * @time 14:41.
 * @description Describe
 */
public class SmartFragment {

    public static ArrayList<HomeMenuInfo> getHomeMenuData(){
        ArrayList<HomeMenuInfo> list = new ArrayList<>();
        HomeMenuInfo info = new HomeMenuInfo();
        info.setName("回家");
        info.setImgUrl("1");
        list.add(info);

        HomeMenuInfo info1 = new HomeMenuInfo();
        info1.setName("离家");
        info1.setImgUrl("2");
        list.add(info1);

        HomeMenuInfo info2 = new HomeMenuInfo();
        info2.setName("晚安");
        info2.setImgUrl("3");
        list.add(info2);

        HomeMenuInfo info3 = new HomeMenuInfo();
        info3.setName("预约");
        info3.setImgUrl("4");
        list.add(info3);

        return list;
    }


    public static ArrayList<HomeMenuInfo> getChildHomeMenuData(){
        ArrayList<HomeMenuInfo> list = new ArrayList<>();
        HomeMenuInfo info = new HomeMenuInfo();
        info.setName("客厅");
        info.setImgUrl("1");
        list.add(info);

        HomeMenuInfo info1 = new HomeMenuInfo();
        info1.setName("主卧");
        info1.setImgUrl("2");
        list.add(info1);

        HomeMenuInfo info2 = new HomeMenuInfo();
        info2.setName("次卧");
        info2.setImgUrl("3");
        list.add(info2);

        HomeMenuInfo info3 = new HomeMenuInfo();
        info3.setName("厨房");
        info3.setImgUrl("4");
        list.add(info3);

        HomeMenuInfo info4 = new HomeMenuInfo();
        info4.setName("洗手间");
        info4.setImgUrl("4");
        list.add(info4);

        HomeMenuInfo info5 = new HomeMenuInfo();
        info5.setName("书房");
        info5.setImgUrl("4");
        list.add(info5);

        HomeMenuInfo info6 = new HomeMenuInfo();
        info6.setName("阳台");
        info6.setImgUrl("4");
        list.add(info6);

        return list;
    }
}
