package com.kc.shiptransport.mvp.exitapplication;

import android.content.Context;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.basemvp.BaseMvpContract;
import com.kc.shiptransport.mvp.basemvp.BaseMvpPresenter;

/**
 * @author qiuyongheng
 * @time 2017/7/12  11:45
 * @desc ${TODD}
 */

public class ExitApplicationPresenter extends BaseMvpPresenter{
    public ExitApplicationPresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        super(context, view, dataRepository);
    }

    @Override
    protected String getTit() {
        return context.getResources().getString(R.string.title_exit_application);
    }
}
