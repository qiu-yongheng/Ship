package com.kc.shiptransport.mvp.recordedsand;

import android.content.Context;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.basemvp.BaseMvpContract;
import com.kc.shiptransport.mvp.basemvp.BaseMvpPresenter;

/**
 * @author qiuyongheng
 * @time 2017/6/16  14:02
 * @desc ${TODD}
 */

public class RecordedSandPresenter extends BaseMvpPresenter{
    public RecordedSandPresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        super(context, view, dataRepository);
    }

    @Override
    protected String getTit() {
        return context.getResources().getString(R.string.recorded_title);
    }
}
