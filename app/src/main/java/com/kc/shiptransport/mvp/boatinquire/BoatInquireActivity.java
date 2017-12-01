package com.kc.shiptransport.mvp.boatinquire;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/29  10:03
 * @desc ${TODD}
 */

public class BoatInquireActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        BoatInquireFragment fragment = (BoatInquireFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new BoatInquireFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new BoatInquirePresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, BoatInquireActivity.class);
        context.startActivity(intent);
    }
}
