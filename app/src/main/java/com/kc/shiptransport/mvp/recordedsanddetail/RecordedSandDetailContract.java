package com.kc.shiptransport.mvp.recordedsanddetail;

import com.kc.shiptransport.data.bean.RecordedSandUpdataBean;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:29
 * @desc ${TODD}
 */

public interface RecordedSandDetailContract {
    interface View extends BaseView<Presenter> {
        void showSpinner(List<String> list);
        void showShip(RecordList recordList);
        void showLoadding(boolean isShow);
        void showError(String msg);
        void showResult(Boolean isSuccess);

        void showDetail(RecordedSandShowList bean);
    }

    interface Presenter extends BasePresenter {
        void getSpinner();
        void getShip(int itemID);
        void commit(RecordedSandUpdataBean bean);

        void getDetail(int itemID);
    }
}
