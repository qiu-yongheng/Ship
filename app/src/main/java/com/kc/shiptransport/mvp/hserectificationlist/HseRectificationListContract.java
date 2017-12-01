package com.kc.shiptransport.mvp.hserectificationlist;

import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/29  8:37
 * @desc ${TODD}
 */

public interface HseRectificationListContract {
     interface View extends BaseView<Presenter> {
          void showDefects(List<HseDefectListBean> list, boolean isFirst);
     }

     interface Presenter extends BasePresenter {
          void getDefects(int pageSize, int pageCount, HseDefectListBean bean, String startDate, String endDate, boolean isShow);
     }
}
