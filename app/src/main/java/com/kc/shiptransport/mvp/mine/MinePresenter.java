package com.kc.shiptransport.mvp.mine;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.userinfo.UserInfo;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author qiuyongheng
 * @time 2017/7/19  13:57
 * @desc ${TODD}
 */

public class MinePresenter {

    private final Context context;
    private final MineFragment view;
    private final DataRepository dataRepository;

    public MinePresenter(Context context, MineFragment view, DataRepository dataRepository) {
        this.context = context;
        this.view = view;
        this.dataRepository = dataRepository;
        view.setPresenter(this);
    }

    /**
     * 修改用户信息
     */
    public void changeInfo(UserInfo userInfo) {
        view.showLoadding(true);
        dataRepository.ChangeUserData(userInfo.getLoginName(), userInfo.getDisplayName(), userInfo.getDepartmentID(), userInfo.getEmail(), userInfo.getTitle(), userInfo.getMobile(), userInfo.getTelephoneNumber(), userInfo.getSex())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        view.showResult(aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showLoadding(false);
                        view.showResult(false);
                    }

                    @Override
                    public void onComplete() {
                        view.showLoadding(false);
                    }
                });
    }
}
