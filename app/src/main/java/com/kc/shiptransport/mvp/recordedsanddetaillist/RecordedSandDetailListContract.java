package com.kc.shiptransport.mvp.recordedsanddetaillist;

import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/20  13:18
 * @desc ${TODD}
 */

public interface RecordedSandDetailListContract {
    interface View extends BaseView<Presenter> {
        void showRecordedList(List<RecordedSandShowList> lists);
    }

    interface Presenter extends BasePresenter {
        void getRecordedList(int itemID);
    }
}
