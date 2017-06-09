package com.kc.shiptransport.mvp.login;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/5/16  10:03
 * @desc ${TODD}
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showloading(boolean active);
        void showNetworkError();
        void showCheckRememberError();
        void showCheckAutoError();
        void showResult(boolean result);
        void navigateToMain();
        void changeDailogInfo(String title, String msg);
        void showSyncError();
    }

    interface Presenter extends BasePresenter {
        void loadData(String username);
        // 登录
        void login(String username, String password);
        // 根据用户名, 获取分包商信息
        void getSubcontractor();
        // 根据用户名, 获取船信息
        void getShip();
    }
}
