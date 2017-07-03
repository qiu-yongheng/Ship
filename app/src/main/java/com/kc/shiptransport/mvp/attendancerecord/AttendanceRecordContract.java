package com.kc.shiptransport.mvp.attendancerecord;

import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/28 21:12
 * @desc ${TODO}
 */

public interface AttendanceRecordContract {
    interface View extends BaseView<Presenter> {
        void showAttendance(List<AttendanceRecordList> attendanceRecordLists);
        void showLoadding(boolean isShow);
        void showError(String msg);
    }

    interface Presenter extends BasePresenter {
        void getAttendance(String subcontractorAccount, String time);
    }
}
