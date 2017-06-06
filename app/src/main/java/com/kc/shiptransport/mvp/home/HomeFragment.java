package com.kc.shiptransport.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:37
 * @desc ${TODO}
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    private Integer[] icon = new Integer[] {};
    private String[] tag = new String[] {"电子地图", "供砂管理", "施工记录", "施工照片", "安全管理", "数据分析"
            , "生产指令", "航线管理", "通讯录"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initListener();
        return view;
    }

    private void initView(View view) {
        mRvHome.setHasFixedSize(true);
        mRvHome.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRvHome.setAdapter(new HomeAdapter(getContext(), icon, tag));
    }

    private void initListener() {

    }
}
