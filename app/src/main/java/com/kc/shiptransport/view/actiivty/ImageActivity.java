package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String imagPath = bundle.getString(IMAGPATH);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        getSupportActionBar().setTitle("图片详情");

        // 启动缩放
        mImg.enable();
        RxGalleryUtil.showImage(this, imagPath, null, null, mImg);

        // 点击图片退出
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, String imgPath) {
        Intent intent = new Intent(context, ImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(IMAGPATH, imgPath);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
