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
        void showCommitPDFResult(boolean isSuccess);
        void showPDF(String url);
    }

    interface Presenter extends BasePresenter {
        void getImgList(int subID, int typeID, int activiyID);
        void commit(ImageMultipleResultEvent imageMultipleResultEvent, int subID, int typeID, String shipAccount, int activity_type);
        void getCommitImgList(ImageMultipleResultEvent imageMultipleResultEvent);
        void commitImg(ScanCommitBean bean, int activity_type);
        void deleteImg(int itemID, int activity_type);
        void commitPDF(String path, int subID, int typeID, String shipAccount);
        void downloadPDF(String url);
    }
}
