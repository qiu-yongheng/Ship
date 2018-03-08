package com.kc.shiptransport.mvp.constructionalbum;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:54
 * @desc ${TODD}
 */

public class ConstructionAlbumActivity extends BaseActivity{

    private ConstructionAlbumFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        if (savedInstanceState != null) {
            fragment = (ConstructionAlbumFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ConstructionAlbumFragment");
        } else {
            fragment = new ConstructionAlbumFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ConstructionAlbumPresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ConstructionAlbumFragment", fragment);
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ConstructionAlbumActivity.class);
        context.startActivity(intent);
    }
}
