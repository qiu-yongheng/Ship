package com.kc.shiptransport.mvp.constructionalbum;

import android.content.Context;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import org.litepal.crud.DataSupport;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:59
 * @desc ${TODD}
 */

public class ConstructionAlbumPresenter implements ConstructionAlbumContract.Presenter {
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
        compositeDisposable.clear();
    }

    /**
     * 获取相册列表
     */
    @Override
    public void getAlbumList(int pageSize, final int pageCount) {
        Observable<Boolean> isConstructionAdmin = dataRepository.IsConstructionPictureAdmin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ConstructionAlbumBean> getConstructionAlbum = dataRepository
                .GetConstructionPictureAlbumRecordsData(pageSize, pageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.zip(isConstructionAdmin, getConstructionAlbum, new BiFunction<Boolean, ConstructionAlbumBean, ConstructionAlbumBean>() {
            @Override
            public ConstructionAlbumBean apply(Boolean aBoolean, ConstructionAlbumBean bean) throws Exception {
                return bean;
            }
        }).subscribe(new MySubcriber<ConstructionAlbumBean>() {
            @Override
            protected void next(ConstructionAlbumBean bean) {
                if (pageCount == 1) {
                    view.showAlbumList(bean);
                } else if (pageCount > 1) {
                    view.showAlbumMore(bean);
                }
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

    @Override
    public void updateAlbum(int itemID, String creator, final String albumName, final String remark, final int position) {
        User user = DataSupport.findFirst(User.class);
        if (!"1".equals(user.getIsConstructionPictureAdmin()) && !creator.equals(user.getUserID())) {
            // 不是管理员 and 不是自己创建的相册
            view.showError("您没有权限修改相册");
            return;
        }

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
    public void deleteAlbum(String Table, String ItemID, String SubTable, String AssociatedColumn, String creator, final int position) {
        User user = DataSupport.findFirst(User.class);
        if (!"1".equals(user.getIsConstructionPictureAdmin()) && !creator.equals(user.getUserID())) {
            // 不是管理员 and 不是自己创建的相册
            view.showError("您没有权限修改相册");
            return;
        }

        view.showLoading(true);
        dataRepository.DeleteItem(Table, ItemID)
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
