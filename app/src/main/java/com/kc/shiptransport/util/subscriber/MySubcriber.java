package com.kc.shiptransport.util.subscriber;


import com.kc.shiptransport.util.exception.ExceptionHandle;

import io.reactivex.Observer;

/**
 * @author 邱永恒
 * @time 2017/11/22  8:46
 * @desc 自定义订阅者
 */

public abstract class MySubcriber<T> implements Observer<T> {
    @Override
    public void onNext(T t) {
        next(t);
    }

    @Override
    public void onError(Throwable e) {
        //LogUtil.e(e);
        error(ExceptionHandle.handleException(e));
    }

    @Override
    public void onComplete() {
        complete();
    }

    protected abstract void next(T t);

    protected abstract void error(String message);

    protected abstract void complete();
}
