package com.kc.shiptransport.mvp.attendance;

import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/28  14:32
 * @desc ${TODD}
 */

public interface AttendanceContract {
    interface View extends BaseView<Presenter> {
        void showAttendanceType(List<AttendanceType> list);
        void showLoadding(boolean isShow);
        void showError(String msg);
    }

    interface Presenter extends BasePresenter {

        void getAttendanceTypeList();
    }
}
