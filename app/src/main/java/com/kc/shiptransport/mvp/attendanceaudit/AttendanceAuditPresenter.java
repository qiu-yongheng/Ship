package com.kc.shiptransport.mvp.attendanceaudit;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.AttendanceRecordList;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/30  16:22
 * @desc ${TODD}
 */

public class AttendanceAuditPresenter implements AttendanceAuditContract.Presenter {
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
        compositeDisposable.clear();
    }

    /**
     * 获取考勤列表
     */
    @Override
    public void getAttendance() {
        view.showLoading(true);
        dataRepository
                .GetAttendanceRecords(0, "", "", "")
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<AttendanceRecordList>, ObservableSource<AttendanceRecordList>>() {
                    @Override
                    public ObservableSource<AttendanceRecordList> apply(@NonNull List<AttendanceRecordList> list) throws Exception {
                        return Observable.fromIterable(list);
                    }
                })
                .filter(new Predicate<AttendanceRecordList>() {
                    @Override
                    public boolean test(@NonNull AttendanceRecordList attendanceRecordList) throws Exception {
                        if (attendanceRecordList.getIsCheck() == 0) {
                            // 未审核
                            return true;
                        } else {
                            // 已审核(1通过, -1未通过)
                            return false;
                        }
                    }
                })
                .toList()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AttendanceRecordList>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AttendanceRecordList> list) {
                        if (list.isEmpty()) {
                            view.showError("没有考勤数据需要审核");
                            view.showAttendance(list);
                        } else {
                            view.showAttendance(list);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }

    /**
     * 提交考勤结果
     */
    @Override
    public void commitAudit(List<AttendanceRecordList> list, final int position, final int type) {
        view.showLoading(true);
        dataRepository
                .InsertAttendanceCheckRecord(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        if (type == 1) {
                            // 批量提交
                            view.showMutiResult(aBoolean);
                        } else {
                            // 单次提交
                            view.showResult(aBoolean, position);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoading(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoading(false);
                    }
                });
    }
}
