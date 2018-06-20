package com.kc.shiptransport.mvp.constructionalbumpicture;

import android.content.Context;

import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.album.CommitBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.util.JsonUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:10
 * @desc ${TODD}
 */

public class ConstructionAlbumPicturePresenter implements ConstructionAlbumPictureContract.Presenter {
    private final Context context;
    private final ConstructionAlbumPictureContract.View view;
    private final DataRepository dataRepository;
    private final CompositeDisposable compositeDisposable;

    public ConstructionAlbumPicturePresenter(Context context, ConstructionAlbumPictureContract.View view, DataRepository dataRepository) {
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

    @Override
    public void getImgList(int pageSize, final int pageCount, int albumItemID) {
        Observable<Boolean> isConstructionAdmin = dataRepository.IsConstructionPictureAdmin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<ConstructionAlbumPictureBean> pictureObservae = dataRepository
                .GetConstructionPictureAttachmentRecordsData(pageSize, pageCount, albumItemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observable.zip(isConstructionAdmin, pictureObservae, new BiFunction<Boolean, ConstructionAlbumPictureBean, ConstructionAlbumPictureBean>() {
            @Override
            public ConstructionAlbumPictureBean apply(Boolean aBoolean, ConstructionAlbumPictureBean bean) throws Exception {
                return bean;
            }
        }).subscribe(new MySubcriber<ConstructionAlbumPictureBean>() {
            @Override
            protected void next(ConstructionAlbumPictureBean bean) {
                if (pageCount == 1) {
                    view.showImgList(bean);
                } else if (pageCount > 1) {
                    view.showImgMore(bean);
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
    public void deleteImg(String Table, String albumCreator, List<ConstructionAlbumPictureBean.DataBean> datas, final Set<Integer> positionSet) {
        User user = DataSupport.findFirst(User.class);
        if (!"1".equals(user.getIsConstructionPictureAdmin()) && !albumCreator.equals(user.getUserID())) {
            // 不是管理员 and 不是自己创建的相册
            view.showError("您没有权限删除相片");
            return;
        }
        view.showLoading(true);
        dataRepository
                .DeleteItems(Table, datas, positionSet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        view.showDeleteResult(aBoolean, positionSet);
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
    public void commitPicture(final int albumID, List<CommitPictureBean> list) {
        view.showProgress(list.size());
        Observable
                .fromIterable(list)
                .flatMap(new Function<CommitPictureBean, ObservableSource<CommitBean>>() {
                    @Override
                    public ObservableSource<CommitBean> apply(final CommitPictureBean bean) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<CommitBean>() {
                            @Override
                            public void subscribe(ObservableEmitter<CommitBean> e) throws Exception {
                                bean.setAlbumID(albumID);
                                String json = JsonUtil.getAlbumPictureCommitJson(bean);
                                String imgBase64 = RxGalleryUtil.getImgBase64(bean.getFilePath());
                                CommitBean commitBean = new CommitBean();
                                commitBean.setJson(json);
                                commitBean.setByteDataStr(imgBase64);
                                e.onNext(commitBean);
                                e.onComplete();
                                LogUtil.d("===============onComplete()===============");
                            }
                        });
                    }
                })
                .flatMap(new Function<CommitBean, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(CommitBean commitBean) throws Exception {
                        return dataRepository.InsertConstructionPictureAttachment(commitBean.getJson(), commitBean.getByteDataStr());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void next(Boolean aBoolean) {
                        if (aBoolean) {
                            view.updateProgress();
                        } else {
                            // 提交失败, 保存
                            view.showError("图片提交失败");
                        }
                    }

                    @Override
                    protected void error(String message) {
                        LogUtil.d("error: " + message);
                        view.showError(message);
                        view.showLoading(false);
                    }

                    @Override
                    protected void complete() {
                        LogUtil.d("上传图片: complete()");
                        view.showLoading(false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }
                });
    }
}
