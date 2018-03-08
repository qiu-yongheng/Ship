package com.kc.shiptransport.mvp.constructionalbumpicture;

import android.content.Context;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:10
 * @desc ${TODD}
 */

public class ConstructionAlbumPicturePresenter implements ConstructionAlbumPictureContract.Presenter{
    private final Context context;
    private final ConstructionAlbumPictureContract.View view;
    private final DataRepository dataReository;
    private final CompositeDisposable compositeDisposable;

    public ConstructionAlbumPicturePresenter(Context context, ConstructionAlbumPictureContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataReository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getImgList(int albumItemID) {
        dataReository
                .GetConstructionPictureAttachmentRecordsData(1000, 1, albumItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<ConstructionAlbumPictureBean>() {
                    @Override
                    protected void next(ConstructionAlbumPictureBean bean) {
                        view.showImgList(bean);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void complete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
