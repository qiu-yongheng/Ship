package com.kc.shiptransport.mvp.hsecheck;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/22  10:00
 * @desc ${TODD}
 */

public class HseCheckActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        HseCheckFragment hseCheckFragment = (HseCheckFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (hseCheckFragment == null) {
            hseCheckFragment = new HseCheckFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), hseCheckFragment, R.id.fragment_repository);
        }

        new HseCheckPresenter(this, hseCheckFragment, new DataRepository());
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HseCheckActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
