package com.kc.shiptransport.mvp.exitassessor;

import android.content.Context;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.basemvp.BaseMvpContract;
import com.kc.shiptransport.mvp.basemvp.BaseMvpPresenter;

/**
 * @author 邱永恒
 * @time 2017/9/6  15:32
 * @desc ${TODD}
 */

public class ExitAssessorPresenter extends BaseMvpPresenter{
    public ExitAssessorPresenter(Context context, BaseMvpContract.View view, DataRepository dataRepository) {
        super(context, view, dataRepository);
    }

    @Override
    protected String getTit() {
        return context.getResources().getString(R.string.title_exit_assessor);
    }
}
