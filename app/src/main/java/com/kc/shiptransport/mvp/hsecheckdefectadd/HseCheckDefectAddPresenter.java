package com.kc.shiptransport.mvp.hsecheckdefectadd;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseDefectAddCommitBean;
import com.kc.shiptransport.data.bean.hse.HseDefectDetailBean;
import com.kc.shiptransport.data.bean.img.ImgCommitBean;
import com.kc.shiptransport.data.bean.img.ImgCommitResultBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.subscriber.MySubcriber;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:42
 * @desc ${TODD}
 */

public class HseCheckDefectAddPresenter implements HseCheckDefectAddContract.Presenter{

    private final CompositeDisposable compositeDisposable;
    private final Context context;
    private final HseCheckDefectAddContract.View view;
    private final DataRepository dataRepository;

    public HseCheckDefectAddPresenter(Context context, HseCheckDefectAddContract.View view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * 初始化缺陷类别, 缺陷项目, 整改期限数据
     */
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    /**
     * 提交缺陷记录
     * 1. 提交新增图片
     * 2. 提交总json
     * 3. 删除图片
     * @param bean
     * @param addList
     * @param deleteList
     */
    @Override
    public void commit(final HseDefectAddCommitBean bean, final List<ImgList> addList, final List<ImgList> deleteList) {
        view.showProgressDialog("上传图片", addList.size() + deleteList.size() + 1);
        Observable
                .fromIterable(addList)
                .flatMap(new Function<ImgList, ObservableSource<ImgCommitBean>>() {
                    @Override
                    public ObservableSource<ImgCommitBean> apply(ImgList imgList) throws Exception {
                        // 生成待提交图片数据
                        return dataRepository.getImgCommitBean(imgList);
                    }
                })
                .flatMap(new Function<ImgCommitBean, ObservableSource<ImgCommitResultBean>>() {
                    @Override
                    public ObservableSource<ImgCommitResultBean> apply(ImgCommitBean imgCommitBean) throws Exception {
                        // 提交图片
                        return dataRepository.UploadFile(imgCommitBean);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ImgCommitResultBean>() {
                    @Override
                    public void accept(ImgCommitResultBean imgCommitResultBean) throws Exception {
                        if (imgCommitResultBean != null) {
                            view.updateProgressDialog("上传图片", 1);
                        } else {
                            LogUtil.d("没有图片提交");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .toList()
                .toObservable()
                .flatMap(new Function<List<ImgCommitResultBean>, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(List<ImgCommitResultBean> imgCommitResultBeans) throws Exception {
                        LogUtil.t("提交json");
                        // 提交json
                        return dataRepository.InsertSafeDefectRecord(bean, imgCommitResultBeans);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean != null) {
                            view.updateProgressDialog("上传基本数据", 1);
                        } else {
                            LogUtil.d("json提交失败");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Boolean, ObservableSource<ImgList>>() {
                    @Override
                    public ObservableSource<ImgList> apply(Boolean aBoolean) throws Exception {
                        LogUtil.t("删除图片");
                        // 遍历删除图片列表
                        return Observable.fromIterable(deleteList);
                    }
                })
                .flatMap(new Function<ImgList, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(ImgList imgList) throws Exception {
                        // 删除图片
                        return dataRepository.DeleteSafeDefectAttachmentRecordByItemID(imgList.getItemID());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            view.updateProgressDialog("删除图片", 1);
                        } else {
                            LogUtil.d("删除图片失败");
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .toList()
                .toObservable()
                .flatMap(new Function<List<Boolean>, ObservableSource<Boolean>>() {
                    @Override
                    public ObservableSource<Boolean> apply(final List<Boolean> booleans) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                                e.onNext(booleans.size() == deleteList.size());
                                e.onComplete();
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<Boolean>() {
                    @Override
                    protected void _onNext(Boolean booleans) {
                        view.showCommitResult(booleans);
                    }

                    @Override
                    protected void _onError(String message) {
                        view.showError(message);
                        LogUtil.e(message);
                        view.hideProgressDialog();
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

    @Override
    public void getDetailData(int itemID) {
        view.showLoading(true);
        dataRepository
                .GetSafeDefectRecordByItemID(itemID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubcriber<HseDefectDetailBean>() {
                    @Override
                    protected void _onNext(HseDefectDetailBean bean) {
                        view.showDetailData(bean);
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
}
