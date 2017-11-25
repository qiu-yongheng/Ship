package com.kc.shiptransport.mvp.hsecheckdefect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:15
 * @desc ${TODD}
 */

public class HseCheckDefectActivity extends BaseActivity{
    public static final String ITEMID = "ITEMID";
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);

        HseCheckDefectFragment fragment = (HseCheckDefectFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);
        if (fragment == null) {
            fragment = new HseCheckDefectFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new HseCheckDefectPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HseCheckDefectActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
