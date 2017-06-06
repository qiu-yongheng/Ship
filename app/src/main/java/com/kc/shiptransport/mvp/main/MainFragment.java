package com.kc.shiptransport.mvp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.home.HomeFragment;
import com.kc.shiptransport.mvp.mine.MineFragment;
import com.kc.shiptransport.mvp.upcoming.UpcomingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/6  17:28
 * @desc ${TODD}
 */

public class MainFragment extends Fragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        return view;
    }

    private void initView(View view) {
        // 绑定view
        viewpager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager(), new HomeFragment(), new UpcomingFragment(), new MineFragment()));
        // 绑定viewpage
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
