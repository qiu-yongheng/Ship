package com.kc.shiptransport.mvp.hserectificationupdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectListBean;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/11/28  14:17
 * @desc ${TODD}
 */

public class HseRectificationUpdateActivity extends BaseActivity{
    public static final String DATA = "DATA";
    public static final String TYPE = "TYPE";
    public HseDefectListBean mData;
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        mData = (HseDefectListBean) bundle.getSerializable(DATA);
        type = bundle.getInt(TYPE);

        HseRectificationUpdateFragment fragment = (HseRectificationUpdateFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);

        if (fragment == null) {
            fragment = new HseRectificationUpdateFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new HseRectificationUpdatePresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, HseRectificationUpdateActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
