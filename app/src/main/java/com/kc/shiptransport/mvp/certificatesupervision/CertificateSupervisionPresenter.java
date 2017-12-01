package com.kc.shiptransport.mvp.certificatesupervision;

import android.content.Context;

import com.kc.shiptransport.data.bean.certificatesupervision.CertificateBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/30  17:32
 * @desc ${TODD}
 */

public class CertificateSupervisionPresenter implements CertificateSupervisionContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final CertificateSupervisionContract.View view;
    private final DataRepository dataRepository;

    public CertificateSupervisionPresenter(Context context, CertificateSupervisionContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        compositeDisposable = new CompositeDisposable();
        view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void getDatas() {
        view.showLoading(true);
        dataRepository
                .GetSafeCertificateSupervision(10000, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<CertificateBean>>() {
                    @Override
                    protected void _onNext(List<CertificateBean> list) {
                        view.showDatas(list);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void _onComplete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void search(String msg, final boolean isSearchAll) {
        dataRepository
                .searchCertificate(msg, isSearchAll)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<List<CertificateBean>>() {
                    @Override
                    protected void _onNext(List<CertificateBean> list) {
                        view.showDatas(list);
                        if (!list.isEmpty() && !isSearchAll) {
                            view.showError("搜索到" + list.size() + "条记录");
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void _onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
