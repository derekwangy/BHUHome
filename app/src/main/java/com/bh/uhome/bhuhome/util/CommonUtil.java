package com.bh.uhome.bhuhome.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @author 凌霄
 * @date 2017/6/29.
 * @time 11:14.
 * @description Describe
 */
public class CommonUtil {
    private static long exitTime = 0;

    public static void exit(Context mContext) {
        try {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(mContext, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                SysActivityManage.getInstance().exit();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
