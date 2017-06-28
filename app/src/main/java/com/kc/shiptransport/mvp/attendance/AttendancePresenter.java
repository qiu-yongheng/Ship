package com.kc.shiptransport.mvp.attendance;

import android.content.Context;
import android.widget.Toast;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.util.CalendarUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/6/28  14:34
 * @desc ${TODD}
 */

public class AttendancePresenter implements AttendanceContract.Presenter{
    private final Context context;
    private final AttendanceContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public AttendancePresenter(Context context, AttendanceContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        getCreate();
        getTime();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getAttendanceTypeList() {
        view.showLoadding(true);
        dataRepository
                .GetAttendanceTypeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AttendanceType>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull List<AttendanceType> list) {
                        Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();
                        view.showAttendanceType(list);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }

    @Override
    public void getCreate() {
        dataRepository
                .getCurrentSubcontractor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Subcontractor>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Subcontractor subcontractor) {
                        view.showCreate(subcontractor);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getTime() {
        view.showTime(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"));
    }

    @Override
    public void commit(int itemID, String creator, String remark) {
        view.showLoadding(true);
        dataRepository
                .InsertAttendanceRecord("", itemID, creator, remark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showResult(aBoolean);
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
