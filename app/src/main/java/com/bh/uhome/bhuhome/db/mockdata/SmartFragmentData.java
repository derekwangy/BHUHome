package com.bh.uhome.bhuhome.db.mockdata;

import android.util.Log;
import com.bh.uhome.bhuhome.entity.HomeMenuInfo;
import com.bh.uhome.bhuhome.entity.VersionInfo;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 凌霄
 * @date 2017/8/31.
 * @time 14:41.
 * @description Describe
 */
public class SmartFragmentData {

    public static VersionInfo getVersionData(){
        VersionInfo  version = new VersionInfo();
        version.setVersionName("1.0.1");
        version.setVersionCode(2);
        version.setUpdateType("0");
        version.setDownloadUrl("http://139.224.116.55:8080/webside/resources/apk/yijia.apk");
        return version;
    }

    public static ArrayList<HomeMenuInfo> getHomeMenuData(){
        ArrayList<HomeMenuInfo> list = new ArrayList<>();
        HomeMenuInfo info = new HomeMenuInfo();
        info.setName("回家");
        info.setId("101");
        info.setState("0");
        info.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info);

        HomeMenuInfo info1 = new HomeMenuInfo();
        info1.setName("离家");
        info1.setId("102");
        info1.setState("0");
        info1.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info1);

        HomeMenuInfo info2 = new HomeMenuInfo();
        info2.setName("晚安");
        info2.setId("103");
        info2.setState("1");
        info2.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info2);

        HomeMenuInfo info3 = new HomeMenuInfo();
        info3.setName("预约");
        info3.setId("104");
        info3.setState("0");
        info3.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info3);

        return list;
    }


    public static ArrayList<HomeMenuInfo> getChildHomeMenuData(){
        ArrayList<HomeMenuInfo> list = new ArrayList<>();
        HomeMenuInfo info = new HomeMenuInfo();
        info.setName("客厅");
        info.setId("1001");
        info.setState("0");
        info.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info);

        HomeMenuInfo info1 = new HomeMenuInfo();
        info1.setName("主卧");
        info1.setId("1002");
        info1.setState("0");
        info1.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info1);

        HomeMenuInfo info2 = new HomeMenuInfo();
        info2.setName("次卧");
        info2.setId("1003");
        info2.setState("1");
        info2.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info2);

        HomeMenuInfo info3 = new HomeMenuInfo();
        info3.setName("厨房");
        info3.setId("1004");
        info3.setState("0");
        info3.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info3);

        HomeMenuInfo info4 = new HomeMenuInfo();
        info4.setName("洗手间");
        info4.setId("1005");
        info4.setState("0");
        info4.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info4);

        HomeMenuInfo info5 = new HomeMenuInfo();
        info5.setName("书房");
        info5.setId("1006");
        info5.setState("1");
        info5.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info5);

        HomeMenuInfo info6 = new HomeMenuInfo();
        info6.setName("阳台");
        info6.setId("1007");
        info6.setState("0");
        info6.setImgUrl("http://cdn.duitang.com/uploads/item/201205/16/20120516145225_zhNj5.jpeg");
        list.add(info6);
        return list;
    }


    public static void getData(){
        try {
            JSONObject jsonObject = null;
            for (int i = 0;i<getHomeMenuData().size(); i++){
                jsonObject = new JSONObject();
                jsonObject.put("id",getHomeMenuData().get(i).getId());
                jsonObject.put("name",getHomeMenuData().get(i).getName());
                jsonObject.put("state",getHomeMenuData().get(i).getState());
                jsonObject.put("imgUrl",getHomeMenuData().get(i).getImgUrl());
                Log.i("Data:",jsonObject.toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
