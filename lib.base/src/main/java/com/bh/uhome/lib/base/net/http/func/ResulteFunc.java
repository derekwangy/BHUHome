package com.bh.uhome.lib.base.net.http.func;



import com.bh.uhome.lib.base.net.exception.HttpTimeException;

import rx.functions.Func1;


/**
 * 服务器返回数据判断
 *
 * @author derek
 * @date 2017/8/10.
 * @time 16:10.
 * @description Describe
 */
public class ResulteFunc implements Func1<Object,Object>{
    @Override
    public Object call(Object o) {
        if (o == null || "".equals(o.toString())) {
            throw new HttpTimeException("数据错误");
        }
        return o;
    }
}
