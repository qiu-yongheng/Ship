package com.kc.shiptransport.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.badge.BadgeIntentService;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.backlog.ListBean;
import com.kc.shiptransport.mvp.home.HomeFragment;
import com.kc.shiptransport.mvp.mine.MineFragment;
import com.kc.shiptransport.mvp.upcoming.UpcomingFragment;
import com.kc.shiptransport.mvp.upcoming.UpcomingPresenter;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.MIUIUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.leolin.shortcutbadger.ShortcutBadger;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

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
    private MineFragment mineFragment;
    private HomeFragment homeFragment;
    private UpcomingFragment upcomingFragment;
    private Badge badge;
    private static View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 初始化fragment */
        if (savedInstanceState != null) {
            //创建管理器, 获取Fragment
            FragmentManager manager = getChildFragmentManager();
            homeFragment = (HomeFragment) manager.getFragment(savedInstanceState, "HomeFragment");
            upcomingFragment = (UpcomingFragment) manager.getFragment(savedInstanceState, "UpcomingFragment");
            mineFragment = (MineFragment) manager.getFragment(savedInstanceState, "MineFragment");
        } else {
            homeFragment = new HomeFragment();
            upcomingFragment = new UpcomingFragment();
            mineFragment = new MineFragment();
        }

        /** 初始化presenter */
        if (upcomingFragment != null) {
            new UpcomingPresenter(getContext(), upcomingFragment, new DataRepository());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView(view);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 更新角标
        List<ListBean> all = DataSupport.findAll(ListBean.class);
        if (badge == null) {
            badge = new QBadgeView(getContext()).bindTarget(((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1)).setBadgeNumber(all.size());
        } else {
            badge.hide(false);
            badge.setBadgeNumber(all.size());
        }

        badge.setGravityOffset(20, 0, true);

        // 更新桌面角标
        ShortcutBadger.applyCount(getContext(), all.size());

        LogUtil.d("当前手机型号: " + MIUIUtils.getMobileVersion());

        // 小米添加桌面角标
        if (MIUIUtils.isMIUI()) {
            LogUtil.d("发送通知");
            getActivity().startService(
                    new Intent(getActivity(), BadgeIntentService.class).putExtra("badgeCount", all.size())
            );
        }

        view = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(1);
    }

    public static View getBadge() {
        return view;
    }

    private void initView(View view) {
        // 绑定view
        viewpager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager(), homeFragment, upcomingFragment, mineFragment));
        // 缓存3个标签的数据, 不重复加载数据
        viewpager.setOffscreenPageLimit(3);
        // 绑定viewpage
        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setIcon(R.mipmap.home);
        tabLayout.getTabAt(1).setIcon(R.mipmap.un_upcoming);
        tabLayout.getTabAt(2).setIcon(R.mipmap.un_mine);

    }

    public void initListener() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetTab();
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.mipmap.home);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.mipmap.upcoming);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.mipmap.mine);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void resetTab() {
        tabLayout.getTabAt(0).setIcon(R.mipmap.un_home);
        tabLayout.getTabAt(1).setIcon(R.mipmap.un_upcoming);
        tabLayout.getTabAt(2).setIcon(R.mipmap.un_mine);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mineFragment.onActivityResult(requestCode, resultCode, data);
    }
}
