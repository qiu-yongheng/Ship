package com.kc.shiptransport.mvp.devicerepairdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.device.repair.DeviceRepairDetailBean;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2018/6/17  12:35
 * @desc
 */

public class DeviceRepairDetailActivity extends BaseActivity {
    public static final String REPAIR_DETAIL = "repair_detail";
    public static final String TYPE = "type";
    public DeviceRepairDetailBean repair_bean;
    public int type;

    public static void startActivity(Context context, DeviceRepairDetailBean bean, int type) {
        Intent intent = new Intent(context, DeviceRepairDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(REPAIR_DETAIL, bean);
        bundle.putInt(TYPE, type);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        repair_bean = bundle.getParcelable(REPAIR_DETAIL);
        type = bundle.getInt(TYPE);

        DeviceRepairDetailFragment fragment = (DeviceRepairDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new DeviceRepairDetailFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }
    }
}
