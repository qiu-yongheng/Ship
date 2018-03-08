package com.kc.shiptransport.view.actiivty.select;

import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/12/8  13:47
 * @desc ${TODD}
 */

public interface SelectContract {
    interface View extends BaseView<Presenter> {
        void showHseShipData(List<HseCheckShip> checkShips);
    }

    interface Presenter extends BasePresenter {
        void search(String msg, boolean isSearchAll);
        void getHseShipData();
    }
}
