package com.kc.shiptransport.mvp.bcf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2017/9/25  9:27
 * @desc ${TODD}
 */

public class BCFActivity extends BaseActivity{

    private static final String ITEMID = "ITEMID";
    private static final String ISALLOW = "ISALLOW";
    private BCFFragment fragment;
    public int itemID;
    public boolean isAllow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        itemID = bundle.getInt(ITEMID);
        isAllow = bundle.getBoolean(ISALLOW);

        if (savedInstanceState != null) {
            fragment = (BCFFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BCFFragment");
        } else {
            fragment = new BCFFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new BCFPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            if (fragment != null && fragment.isAdded()) {
                getSupportFragmentManager().putFragment(outState, "BCFFragment", fragment);
            }
        }
    }

    public static void startActivity(Context context, int itemID, boolean isAllowEdit) {
        Intent intent = new Intent(context, BCFActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(ITEMID, itemID);
        bundle.putBoolean(ISALLOW, isAllowEdit);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
