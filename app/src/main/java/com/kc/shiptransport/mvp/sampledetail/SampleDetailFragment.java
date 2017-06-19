package com.kc.shiptransport.mvp.sampledetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/14  10:59
 * @desc ${TODD}
 */

public class SampleDetailFragment extends Fragment implements SampleDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.text_ship_name)
    TextView textShipName;
    @BindView(R.id.text_ship_num)
    TextView textShipNum;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.view_padding)
    View viewPadding;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    private SampleDetailContract.Presenter presenter;
    private SampleDetailActivity activity;
    private SampleDetailAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        return view;
    }

    private void initListener() {
        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        // 添加取样
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                mAdapter.addData(1);
            }
        });
    }

    @Override
    public void initViews(View view) {
        // 初始化toolbar
        activity = (SampleDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(R.string.sampledetail_title);

        // 初始化recyclerview
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<String> list = new ArrayList<>();
        list.add("gaga");
        list.add("haha");
        list.add("yaya");
        mAdapter = new SampleDetailAdapter(getContext(), list);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(SampleDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showShipNumber(String num) {

    }
}
