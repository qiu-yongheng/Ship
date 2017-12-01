package com.kc.shiptransport.mvp.hserectification;

import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/28  10:10
 * @desc ${TODD}
 */

public interface HseRectificationContract {
    interface View extends BaseView<Presenter> {
        void showDefects(List<HseDefectListBean> list, boolean isFirst);
        void showDeleteResult(boolean isSuccess);
        void showSyncResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDefects(int pageSize, int pageCount, HseDefectListBean bean, String startDate, String endDate, boolean isShow);
        void deleteForItem(int itemID);
    }
}
