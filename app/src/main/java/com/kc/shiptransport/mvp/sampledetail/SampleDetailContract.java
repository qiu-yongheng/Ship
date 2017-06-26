package com.kc.shiptransport.mvp.sampledetail;

import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public interface SampleDetailContract {
    interface View extends BaseView<Presenter> {
        void showTime(String time);
        void showShipName(String name);
        void showShipNumber(String num);
        void showError(String msg);
        void showItemID(SandSample sandSample);
        void showLoading(boolean isShow);
    }

    interface Presenter extends BasePresenter {
        void getShipInfo(int position);
        void start(int position);
        //void upImage(Byte[] ByteData, String FileName, );
        void commit(SandSample sandSample);
    }
}
