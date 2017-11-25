package com.kc.shiptransport.mvp;

import android.support.v7.app.AppCompatActivity;

import com.kc.shiptransport.util.fragmentback.BackHandlerHelper;
import com.kc.shiptransport.util.fragmentback.FragmentBackHandler;

/**
 * @author 邱永恒
 * @time 2017/11/22  11:06
 * @desc 需要处理back键需求的Fragment实现
 */

public abstract class BaseFragmentBack<V extends AppCompatActivity> extends BaseFragment<V> implements FragmentBackHandler{
    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }
}
