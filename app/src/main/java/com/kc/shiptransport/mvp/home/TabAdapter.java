package com.kc.shiptransport.mvp.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author qiuyongheng
 * @time 2017/6/6  17:37
 * @desc ${TODD}
 */

public class TabAdapter extends FragmentPagerAdapter{
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
