package com.kc.shiptransport.view.actiivty;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.kc.shiptransport.adapter.ImgViewPageAdapter;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.view.viewpage.HackyViewPager;

import java.util.ArrayList;

/**
 * @author 邱永恒
 * @time 2017/7/1 20:14
 * @desc ${TODO}
 */

public class ImgViewPageActivity extends BaseActivity {
    private static final String IMGLIST = "IMGLIST";
    private static final String POSITION = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        ArrayList<ScannerImgListByTypeBean> imgList = bundle.getParcelableArrayList(IMGLIST);
        int position = bundle.getInt(POSITION);


        HackyViewPager viewPager = new HackyViewPager(this);
        setContentView(viewPager);
        ImgViewPageAdapter adapter = new ImgViewPageAdapter(this, imgList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void startActivity(Context context, ArrayList<ScannerImgListByTypeBean> list, int position) {
        Intent intent = new Intent(context, ImgViewPageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IMGLIST, list);
        bundle.putInt(POSITION, position);
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }
}
