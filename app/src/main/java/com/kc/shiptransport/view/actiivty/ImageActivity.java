package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.bm.library.PhotoView;
import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.RxGalleryUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/7/1 20:14
 * @desc ${TODO}
 */

public class ImageActivity extends BaseActivity {
    private static final String IMAGPATH = "IMAGPATH";
    @BindView(R.id.img)
    PhotoView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String imagPath = bundle.getString(IMAGPATH);

        // 启动缩放
        mImg.enable();
        RxGalleryUtil.showImage(this, imagPath, null, null, mImg);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, String imgPath) {
        Intent intent = new Intent(context, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(IMAGPATH, imgPath);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
