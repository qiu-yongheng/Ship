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
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        //LogUtil.e(e);
        _onError(ExceptionHandle.handleException(e));
    }

    @Override
    public void onComplete() {
        _onComplete();
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

    protected abstract void _onComplete();
}
