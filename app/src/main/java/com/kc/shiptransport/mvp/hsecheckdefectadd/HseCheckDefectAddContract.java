package com.kc.shiptransport.mvp.hsecheckdefectadd;

import com.kc.shiptransport.data.bean.hse.HseDefectAddCommitBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:41
 * @desc ${TODD}
 */

public interface HseCheckDefectAddContract {
    interface View extends BaseView<Presenter> {
        void showCommitResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void commit(HseDefectAddCommitBean bean, List<ImgList> addList, List<ImgList> deleteList);
    }
}
