package com.kc.shiptransport.mvp.shipselect;

import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;

import butterknife.ButterKnife;

/**
 * @author qiuyongheng
 * @time 2017/5/18  22:37
 * @desc ${TODD}
 */

public class ShipSelectActivity extends BaseActivity {
    public String currentSelectShipType;
    private ShipSelectFragment shipSelectFragment;
    public String mCurrentSelectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_select);
        ButterKnife.bind(this);

        // 获取传过来的数据
        Bundle bundle = getIntent().getExtras();
        currentSelectShipType = bundle.getString("ShipSelectActivity_type");
        mCurrentSelectDate = bundle.getString("ShipSelectActivity_date");

        if (savedInstanceState != null) {
            shipSelectFragment = (ShipSelectFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ShipSelectFragment");
        } else {
            shipSelectFragment = new ShipSelectFragment();
        }

        if (!shipSelectFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_ship_select, shipSelectFragment)
                    .commit();
        }

        new ShipSelectPresenter(this, shipSelectFragment);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (shipSelectFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ShipSelectFragment", shipSelectFragment);
        }
    }
}
