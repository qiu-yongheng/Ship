package com.kc.shiptransport.mvp.devicemaintenance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.devicerepair.DeviceRepairActivity;
import com.kc.shiptransport.mvp.devicerepair.DeviceRepairFragment;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2018/6/20  22:04
 * @desc 维修保养
 */

public class DeviceMaintenanceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        DeviceMaintenanceFragment fragment = (DeviceMaintenanceFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new DeviceMaintenanceFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeviceMaintenanceActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
