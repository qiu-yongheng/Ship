package com.kc.shiptransport.mvp.recordedsandshow;

import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:29
 * @desc ${TODD}
 */

public interface RecordedSandShowContract {
    interface View extends BaseView<Presenter> {
        void showLoadding(boolean isShow);
        void showError(String msg);
        void showDatas(List<RecordedSandShowList> lists);
    }

    interface Presenter extends BasePresenter {
        void getRecordShowList(int itemID);
    }
}
