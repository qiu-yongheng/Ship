package com.kc.shiptransport.mvp.voyageinfo;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.voyagedetail.VoyageDetailActivity;
import com.kc.shiptransport.util.SettingUtil;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:40
 * @desc ${TODD}
 */

public class VoyageFragment extends BaseMvpFragment {

    private VoyageInfoActivity activity;

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
        return SettingUtil.TYPE_VOYAGEINFO;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (VoyageInfoActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getRedText() {
        return R.string.voyage_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.voyage_black;
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
        VoyageDetailActivity.startActivity(getActivity(), position);
    }
}
