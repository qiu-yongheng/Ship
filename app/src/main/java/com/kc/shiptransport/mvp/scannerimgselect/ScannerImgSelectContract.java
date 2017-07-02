package com.kc.shiptransport.mvp.scannerimgselect;

import com.kc.shiptransport.data.bean.ScanCommitBean;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

/**
 * @author 邱永恒
 * @time 2017/7/1 11:18
 * @desc ${TODO}
 */

public interface ScannerImgSelectContract {
    interface View extends BaseView<Presenter> {
        void showImgList(List<ScannerImgListByTypeBean> scannerImgListByTypeBeen);
        void showProgress(int max);
        void updateProgress();
        void hideProgress();
        void showDeleteResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void getImgList(int subID, int typeID);
        void commit(ImageMultipleResultEvent imageMultipleResultEvent, int subID, int typeID, String shipAccount);
        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent);
        void commitImg(ScanCommitBean bean);
        void deleteImg(int itemID);
    }
}
