package com.kc.shiptransport.mvp.hsecheckdefectadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;
import com.kc.shiptransport.util.fragmentback.BackHandlerHelper;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:40
 * @desc ${TODD}
 */

public class HseCheckDefectAddActivity extends BaseActivity {
    private HseCheckDefectAddFragment fragment;
    public static final String TYPE = "TYPE";
    public static final String CHECKRECORDID = "CHECKRECORDID";
    public static final String ITEMID = "ITEMID";
    public int type;
    public int checkRecordID;
    public int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);
        checkRecordID = bundle.getInt(CHECKRECORDID);
        itemID = bundle.getInt(ITEMID);

        fragment = (HseCheckDefectAddFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new HseCheckDefectAddFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new HseCheckDefectAddPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HseCheckDefectAddActivity.class);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }
}
