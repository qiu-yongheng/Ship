package com.kc.shiptransport.mvp;

import android.view.View;

/**
 * @author qiuyongheng
 * @time 2017/5/16  9:00
 * @desc ${TODD}
 */

public interface BaseView<T> {
    void initViews(View view);

    void initListener();

    void setPresenter(T presenter);

    void showLoading(boolean isShow);

    void showError(String msg);
}
