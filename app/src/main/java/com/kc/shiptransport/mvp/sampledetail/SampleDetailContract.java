package com.kc.shiptransport.mvp.sampledetail;

import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.db.SampleImageList;
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
        void showProgress(int max);
        void updateProgress();
        // 获取数据, 显示列表
        void showDetailList(SampleShowDatesBean bean);
        void showImageUpdataResult();
        void showDeleteResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        // 根据position获取进场数据
        void getShipInfo(int position);
        void start(int position);
        // 单张提交图片, 成功后提交全部json
        void commit(SandSample sandSample);
        // 提交图片
        void upImage(SampleImageList sampleCommitList);
        // 查看图片
        void getDates(int position);
        void commitJson(SampleShowDatesBean sampleShowDates);
        void deleteItem(int ItemID);
    }
}
