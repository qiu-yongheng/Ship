package com.kc.shiptransport.mvp.homedetail;

import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/30 21:45
 * @desc ${TODO}
 */

public interface HomeDetailContract {
    interface View extends BaseView<Presenter> {
        void showAppList(List<AppList> lists);
    }

    interface Presenter extends BasePresenter {
        void getAppList(int appPID);
    }
}
