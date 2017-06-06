package com.kc.shiptransport.mvp.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kc.shiptransport.mvp.home.HomeFragment;
import com.kc.shiptransport.mvp.mine.MineFragment;
import com.kc.shiptransport.mvp.upcoming.UpcomingFragment;

/**
 * @author qiuyongheng
 * @time 2017/6/6  17:37
 * @desc ${TODD}
 */

public class TabAdapter extends FragmentPagerAdapter{
    private final HomeFragment homeFragment;
    private final UpcomingFragment upcomingFragment;
    private final MineFragment mineFragment;
    private String[] titles = new String[] {"首页", "待办", "我的"};

    public TabAdapter(FragmentManager fm, HomeFragment homeFragment, UpcomingFragment upcomingFragment, MineFragment mineFragment) {
        super(fm);
        this.homeFragment = homeFragment;
        this.upcomingFragment = upcomingFragment;
        this.mineFragment = mineFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return homeFragment;
        } else if (position == 1) {
            return upcomingFragment;
        }
        return mineFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
