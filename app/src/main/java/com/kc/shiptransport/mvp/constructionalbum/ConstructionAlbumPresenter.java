package com.kc.shiptransport.mvp.constructionalbum;

import android.content.Context;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:59
 * @desc ${TODD}
 */

public class ConstructionAlbumPresenter implements ConstructionAlbumContract.Presenter{
    private final Context context;
    private final ConstructionAlbumContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ConstructionAlbumPresenter(Context context, ConstructionAlbumContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 获取相册列表
     */
    @Override
    public void getAlbumList(int pageSize, int pageCount) {
        dataRepository
                .GetConstructionPictureAlbumRecordsData(pageSize, pageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<ConstructionAlbumBean>() {
                    @Override
                    protected void next(ConstructionAlbumBean bean) {
                        view.ShowAlbumList(bean);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                    }

                    @Override
                    protected void complete() {
                        ToastUtil.tip(context, "加载成功");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void updateAlbum(int itemID, final String albumName, final String remark, final int position) {
        view.showLoading(true);
        dataRepository.InsertTable("ConstructionPictureAlbumRecords", itemID, albumName, remark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showInsertResult(aBoolean, albumName, remark, position);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void complete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void insertAlbum(String albumName, String remark) {
        view.showLoading(true);
        dataRepository.InsertTable("ConstructionPictureAlbumRecords", 0, albumName, remark)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showUpdateResult(aBoolean);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void complete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }

    @Override
    public void deleteAlbum(String Table, String ItemID, String SubTable, String AssociatedColumn, final int position) {
        view.showLoading(true);
        dataRepository.DeleteTable(Table, ItemID, SubTable, AssociatedColumn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showDeleteResult(aBoolean, position);
                    }

                    @Override
                    protected void error(String message) {
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void complete() {
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
