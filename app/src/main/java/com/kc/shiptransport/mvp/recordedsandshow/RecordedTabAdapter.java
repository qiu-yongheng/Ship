package com.kc.shiptransport.mvp.recordedsandshow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kc.shiptransport.db.RecordedSandShowList;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/28  13:24
 * @desc ${TODD}
 */

public class RecordedTabAdapter extends FragmentPagerAdapter{
    private final List<RecordedSandShowList> lists;


    public RecordedTabAdapter(FragmentManager fm, List<RecordedSandShowList> lists) {
        super(fm);
        this.lists = lists;
    }

    @Override
    public Fragment getItem(int position) {
        return RecordedSandTabFragment.newInstance(position, lists);
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "施工船:" + (lists.get(position).getConstructionShipName());
    }
}
