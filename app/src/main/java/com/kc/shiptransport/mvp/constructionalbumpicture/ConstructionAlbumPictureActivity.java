package com.kc.shiptransport.mvp.constructionalbumpicture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.mvp.BaseActivity;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:08
 * @desc ${TODD}
 */

public class ConstructionAlbumPictureActivity extends BaseActivity {
    public static final String ALBUM_NAME = "ALBUM_NAME";
    public static final String ALBUM_REMARK = "ALBUM_REMARK";
    public static final String ALBUM_ITEM = "ALBUM_ITEM";
    private ConstructionAlbumPictureFragment fragment;
    public String albumName;
    public String albumRemark;
    public int albumItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            albumName = bundle.getString(ALBUM_NAME);
            albumRemark = bundle.getString(ALBUM_REMARK);
            albumItem = bundle.getInt(ALBUM_ITEM);
        }


        if (savedInstanceState != null) {
            fragment = (ConstructionAlbumPictureFragment) getSupportFragmentManager().getFragment(savedInstanceState, "ConstructionAlbumPictureFragment");
        } else {
            fragment = new ConstructionAlbumPictureFragment();
        }

        if (!fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_repository, fragment)
                    .commit();
        }

        new ConstructionAlbumPicturePresenter(this, fragment, new DataRepository());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "ConstructionAlbumPictureFragment", fragment);
        }
    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent(context, ConstructionAlbumPictureActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
