package com.kc.shiptransport.mvp.attendanceaudit;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author qiuyongheng
 * @time 2017/6/30  16:22
 * @desc ${TODD}
 */

public class AttendanceAuditPresenter implements AttendanceAuditContract.Presenter{
    private final Context context;
    private final AttendanceAuditContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AttendanceAuditPresenter(Context context, AttendanceAuditContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
