package com.kc.shiptransport.mvp.devicerepair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.mvp.hsecheck.HseCheckActivity;
import com.kc.shiptransport.mvp.hsecheck.HseCheckFragment;
import com.kc.shiptransport.mvp.hsecheck.HseCheckPresenter;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2018/6/16  12:16
 * @desc 设备维修
 */

public class DeviceRepairActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        DeviceRepairFragment fragment = (DeviceRepairFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new DeviceRepairFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DeviceRepairActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
