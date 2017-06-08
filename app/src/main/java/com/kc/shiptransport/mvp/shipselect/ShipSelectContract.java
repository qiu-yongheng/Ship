package com.kc.shiptransport.mvp.shipselect;

import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:47
 * @desc ${TODD}
 */

public interface ShipSelectContract {
    interface View extends BaseView<Presenter> {
        void showShip(List<Ship> value);
        void cancle();
        void commit();
        void showLoading(boolean active);
        void showError();
        void showSuccess();
        void changeDailogInfo();
        void navigateToPlanSet();
        void showCommitSuccess();
    }
    interface Presenter extends BasePresenter {
        void getShip(String type);
        void doCancle(String currentSelectShipType, String date);
        void doCommit(String currentSelectShipType, String currentSelectDate);
        // 刷新数据
        void doRefresh(int jumpWeek);
    }
}
