package com.kc.shiptransport.mvp.hsecheckdefectadd;

import android.content.Context;

import com.kc.shiptransport.data.bean.hse.HseDefectAddCommitBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.data.source.DataRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

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
     * 1. 提交图片
     * 2. 提交总json
     * 3. 删除图片
     * @param bean
     * @param addList
     * @param deleteList
     */
    @Override
    public void commit(HseDefectAddCommitBean bean, List<ImgList> addList, List<ImgList> deleteList) {

    }
}
