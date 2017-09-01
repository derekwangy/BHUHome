package com.bh.uhome.bhuhome.db.mockdata;

import com.bh.uhome.bhuhome.entity.BannerInfoBean;

import java.util.ArrayList;

/**
 * @author 凌霄
 * @date 2017/8/31.
 * @time 15:55.
 * @description Describe
 */
public class MallFragmentData {

    public static ArrayList<BannerInfoBean> getBannersData() {
        ArrayList<BannerInfoBean> banners = new ArrayList<>();
        BannerInfoBean bean = new BannerInfoBean();
        bean.setId("1");
        bean.setImgId("http://pic.58pic.com/58pic/13/60/90/58Y58PICdIc_1024.jpg");
        banners.add(bean);
        BannerInfoBean bean1 = new BannerInfoBean();
        bean1.setId("1");
        bean1.setImgId("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg");
        banners.add(bean1);
        BannerInfoBean bean2 = new BannerInfoBean();
        bean2.setId("1");
        bean2.setImgId("http://pic.58pic.com/58pic/13/56/03/48858PICJU6_1024.jpg");
        banners.add(bean2);

        return banners;
    }
}
