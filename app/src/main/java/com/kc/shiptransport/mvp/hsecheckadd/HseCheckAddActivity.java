package com.kc.shiptransport.mvp.hsecheckadd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.view.actiivty.select.SelectFragment;

/**
 * @author 邱永恒
 * @time 2017/11/22  14:30
 * @desc ${TODD}
 */

public class HseCheckAddActivity extends BaseActivity{
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

        HseCheckAddFragment fragment = (HseCheckAddFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new HseCheckAddFragment();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new HseCheckAddPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HseCheckAddActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
