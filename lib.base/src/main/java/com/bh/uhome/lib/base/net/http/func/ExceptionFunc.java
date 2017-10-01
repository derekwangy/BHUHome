package com.bh.uhome.lib.base.net.http.func;

import android.util.Log;


import com.bh.uhome.lib.base.net.exception.FactoryException;

import rx.Observable;
import rx.functions.Func1;


/**
 *异常处理
 *
 * @author derek
 * @date 2017/8/10.
 * @time 16:10.
 * @description Describe
 */
public class ExceptionFunc implements Func1<Throwable, Observable> {
    @Override
    public Observable call(Throwable throwable) {
        Log.e("Tag","-------->"+throwable.getMessage());
        return Observable.error(FactoryException.analysisExcetpion(throwable));
    }
}
