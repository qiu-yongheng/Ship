package com.kc.shiptransport.mvp.attendancerecord;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.AttendanceRecordList;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/6/28 21:13
 * @desc ${TODO}
 */

public class AttendanceRecordPresenter implements AttendanceRecordContract.Presenter {

    private final Context context;
    private final AttendanceRecordContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable mCompositeDisposable;

    public AttendanceRecordPresenter(Context context, AttendanceRecordContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        this.dataRepository = dataRepository;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getAttendance(String subcontractorAccount, String time) {
        view.showLoadding(true);
        dataRepository
                .GetAttendanceRecords(0, subcontractorAccount, time, time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AttendanceRecordList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AttendanceRecordList> attendanceRecordLists) {
                        Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
                        view.showAttendance(attendanceRecordLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }
}
