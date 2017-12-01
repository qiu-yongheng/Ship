package com.kc.shiptransport.mvp.certificatesupervision;

import com.kc.shiptransport.data.bean.certificatesupervision.CertificateBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/30  17:29
 * @desc ${TODD}
 */

public interface CertificateSupervisionContract {
    interface View extends BaseView<Presenter> {
        void showDatas(List<CertificateBean> list);
    }

    interface Presenter extends BasePresenter {
        void getDatas();
        void search(String msg, boolean isSearchAll);
    }
}
