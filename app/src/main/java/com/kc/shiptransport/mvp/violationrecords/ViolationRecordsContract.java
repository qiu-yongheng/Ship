package com.kc.shiptransport.mvp.violationrecords;

import com.kc.shiptransport.data.bean.violationrecord.ViolationRecordBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/12/4  16:16
 * @desc ${TODD}
 */

public interface ViolationRecordsContract {
    interface View extends BaseView<Presenter> {
        void showDatas(List<ViolationRecordBean> list, boolean isFirst);
    }

    interface Presenter extends BasePresenter {
        void getDatas(int pageSize, int pageCount);
    }
}
