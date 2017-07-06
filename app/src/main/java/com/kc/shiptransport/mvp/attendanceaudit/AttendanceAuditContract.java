package com.kc.shiptransport.mvp.attendanceaudit;

import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/30  16:18
 * @desc ${TODD}
 */

public interface AttendanceAuditContract {
    interface View extends BaseView<Presenter> {
        void showAttendance(List<AttendanceRecordList> list);
        void showResult(boolean isSuccess, int position);
    }

    interface Presenter extends BasePresenter {
        void getAttendance();
        void commitAudit(int ItemID, int AttendanceID, String Creator, String Remark, int IsCheck, int position);
    }
}
