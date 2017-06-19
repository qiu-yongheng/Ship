package com.kc.shiptransport.mvp.voyageinfo;

import android.content.Context;

import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.basemvp.BaseMvpContract;
import com.kc.shiptransport.mvp.basemvp.BaseMvpPresenter;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:43
 * @desc ${TODD}
 */

public class VoyagePresenter extends BaseMvpPresenter{

    public VoyagePresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        super(context, view, dataRepository);
    }

    @Override
    protected String getTit() {
        return "航次信息完善";
    }
}
