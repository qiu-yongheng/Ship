package com.kc.shiptransport.mvp.scanner;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.scannerdetail.ScannerDetailActivity;
import com.kc.shiptransport.util.SettingUtil;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:40
 * @desc ${TODD}
 */

public class ScannerFragment extends BaseMvpFragment {

    private ScannerActivity activity;

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog(getResources().getString(R.string.dialog_loading), getResources().getString(R.string.dialog_loading), new OnDailogCancleClickListener() {
                @Override
                public void onCancle(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    protected void initData() {
        presenter.subscribe();
        presenter.start(jumpWeek, TYPE, subcontractorAccount);
    }

    @Override
    protected int getType() {
        return SettingUtil.TYPE_SCANNER;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (ScannerActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getRedText() {
        return R.string.scanner_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.scanner_black;
    }

    @Override
    protected String abs_showTitleOtherInfo(String data) {
        return "分包商: " + data;
    }

    @Override
    protected String abs_showStayInfo(String data) {
        return "待完善船次: " + data;
    }

    @Override
    protected void abs_onItemClick(View view, int position) {
        ScannerDetailActivity.startActivity(getActivity(), position);
    }
}
