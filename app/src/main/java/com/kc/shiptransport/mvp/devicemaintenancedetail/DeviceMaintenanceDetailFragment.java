package com.kc.shiptransport.mvp.devicemaintenancedetail;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.mvp.basemvp.BaseListFragment;

/**
 * @author 邱永恒
 * @time 2018/6/20  22:37
 * @desc
 */

public class DeviceMaintenanceDetailFragment extends BaseFragment<DeviceMaintenanceDetailActivity>{
    @Override
    public int setView() {
        return R.layout.fragment_device_maintenance;
    }

    @Override
    public int setTitle() {
        return R.string.title_device_maintenance;
    }
}
