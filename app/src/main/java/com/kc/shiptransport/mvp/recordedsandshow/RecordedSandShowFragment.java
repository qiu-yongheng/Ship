package com.kc.shiptransport.mvp.recordedsandshow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:28
 * @desc ${TODD}
 */

public class RecordedSandShowFragment extends Fragment implements RecordedSandShowContract.View{
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;
    private RecordedSandShowContract.Presenter presenter;
    private RecordedSandShowActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedshow, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 获取数据
        presenter.getRecordShowList(activity.itemID);
        return view;
    }

    private void initListener() {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void initViews(View view) {
        activity = (RecordedSandShowActivity) getActivity();
    }

    @Override
    public void setPresenter(RecordedSandShowContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoadding(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
                @Override
                public void onCancle(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDatas(List<RecordedSandShowList> lists) {
        viewPager.setAdapter(new RecordedTabAdapter(getChildFragmentManager(), lists));
        tabLayout.setupWithViewPager(viewPager);
    }
}
