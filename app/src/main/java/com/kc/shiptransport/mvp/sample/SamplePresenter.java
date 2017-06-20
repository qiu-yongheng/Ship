package com.kc.shiptransport.mvp.sample;

import android.content.Context;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.basemvp.BaseMvpContract;
import com.kc.shiptransport.mvp.basemvp.BaseMvpPresenter;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:43
 * @desc ${TODD}
 */

public class SamplePresenter extends BaseMvpPresenter{

    public SamplePresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        super(context, view, dataRepository);
    }

    @Override
    protected String getTit() {
        return context.getResources().getString(R.string.title_sample);
    }
}
