package com.kc.shiptransport.mvp.boatinquireadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/29  14:50
 * @desc ${TODD}
 */

public class BoatInquireAddActivity extends BaseActivity{
    public static final String TYPE = "TYPE";
    public static final String ITEMID = "ITEMID";
    public int type;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);
        itemID = bundle.getInt(ITEMID);

        BoatInquireAddFragment fragment = (BoatInquireAddFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new BoatInquireAddFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new BoatInquireAddPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BoatInquireAddActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
