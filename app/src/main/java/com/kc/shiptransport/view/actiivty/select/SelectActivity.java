package com.kc.shiptransport.view.actiivty.select;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.ActivityUtils;

/**
 * @author 邱永恒
 * @time 2017/12/8  13:44
 * @desc 搜索
 */

public class SelectActivity extends BaseActivity{
    public static final String TYPE = "TYPE";
    public int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE);

        SelectFragment fragment = (SelectFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_repository);
        if (fragment == null) {
            fragment = new SelectFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_repository);
        }

        new SelectPresenter(this, fragment, new DataRepository());
    }

    public static void startActivity(Fragment fragment, Bundle bundle) {
        Intent intent = new Intent(fragment.getContext(), SelectActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        fragment.startActivityForResult(intent, 0);
    }
}
