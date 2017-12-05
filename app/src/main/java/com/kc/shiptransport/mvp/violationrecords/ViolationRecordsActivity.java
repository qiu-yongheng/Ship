package com.kc.shiptransport.mvp.violationrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/12/4  16:15
 * @desc ${TODD}
 */

public class ViolationRecordsActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        ViolationRecordsFragment fragment = (ViolationRecordsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new ViolationRecordsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new ViolationRecordsPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ViolationRecordsActivity.class);
        context.startActivity(intent);
    }
}
