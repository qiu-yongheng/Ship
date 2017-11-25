package com.kc.shiptransport.mvp.sampledetail;

import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.sample.SampleData;
import com.kc.shiptransport.db.sample.SampleImageList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

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
        void showDetailList(SampleData bean);
        void showImageUpdataResult(boolean isCommit);
        void showCommitReturn();
        void startCommit();
        void showDeleteNumForItemID(boolean isSuccess, int p_position);
        void showDeleteImgResult(boolean isSuccess, int p_position, int position);
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
        void getDates(int itemID);
        void commitJson(int itemID, boolean isCommit);
        void deleteNumForItemID(int ItemID, int p_position);
        void deleteImgForItemID(int itemID, int p_position, int position);

        // 提交图片列表
        void commitImgList(ImageMultipleResultEvent imageMultipleResultEvent, int SandSamplingID, int SandSamplingNumID, String ConstructionBoatAccount, int p_position);
    }
}
