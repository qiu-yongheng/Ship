package com.kc.shiptransport.mvp.recordedsanddetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:28
 * @desc ${TODD}
 */

public class RecordedSandDetailFragment extends Fragment implements RecordedSandDetailContract.View{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedsand_detail, container, false);
        initViews(view);
        initListener();
        // TODO 获取数据
        return view;
    }

    private void initListener() {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(RecordedSandDetailContract.Presenter presenter) {

    }
}
