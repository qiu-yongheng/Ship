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
    }
    interface Presenter extends BasePresenter {
        void getShip(String itemID);
        void doCancle(String itemID);
        void doCommit();
    }
}
