package com.kc.shiptransport.mvp.certificatesupervision;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/30  17:27
 * @desc ${TODD}
 */

public class CertificateSupervisionActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        CertificateSupervisionFragment fragment = (CertificateSupervisionFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new CertificateSupervisionFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new CertificateSupervisionPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CertificateSupervisionActivity.class);
        context.startActivity(intent);
    }
}
