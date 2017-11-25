package com.kc.shiptransport.mvp.hsecheckdefect;

import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:16
 * @desc ${TODD}
 */

public interface HseCheckDefectContract {
    interface View extends BaseView<Presenter> {
        void showDefects(List<HseDefectListBean> list);
        void showSyncResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getDefects(int pageSize, int pageCount, HseDefectListBean bean);
    }
}
