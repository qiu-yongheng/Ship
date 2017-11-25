package com.kc.shiptransport.mvp.hsecheckadd;

import com.kc.shiptransport.data.bean.hse.HseCheckAddBean;
import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  14:31
 * @desc ${TODD}
 */

public interface HseCheckAddContract {
    interface View extends BaseView<Presenter> {
        void commitResult(boolean isSuccess);
        void showDetail(HseCheckListBean list);
    }

    interface Presenter extends BasePresenter {
        void commit(List<HseCheckAddBean> list);
        void getDetail(int itemID);
    }
}
