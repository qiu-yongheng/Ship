package com.kc.shiptransport.mvp.sample;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.sampledetail.SampleDetailActivity;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/13  15:40
 * @desc ${TODD}
 */

public class SampleFragment extends BaseMvpFragment{

    private SampleActivity activity;

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog(getResources().getString(R.string.dialog_loading),
                    getResources().getString(R.string.dialog_loading),
                    new OnDailogCancleClickListener() {
                        @Override
                        public void onCancel(ProgressDialog dialog) {
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
        // 显示所有供应商
        presenter.getTime(jumpWeek);
        presenter.getSubcontractorList();
    }

    @Override
    protected int getType() {
        return SettingUtil.TYPE_SAMPLE;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (SampleActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getRedText() {
        return R.string.sample_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.sample_black;
    }

    @Override
    protected String abs_showTitleOtherInfo(String data) {
        return "当前用户: " + data;
    }

    @Override
    protected String abs_showStayInfo(String data) {
        return "未取样航次: " + data;
    }

    @Override
    protected void abs_onItemClick(View view, int position) {
        int itemID = 0;
        List<SandSample> sandSamples = DataSupport.where("position = ?", String.valueOf(position)).find(SandSample.class);
        if (!sandSamples.isEmpty()) {
            itemID = sandSamples.get(0).getItemID();
        }
        SampleDetailActivity.startActivity(getActivity(), itemID);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            presenter.doRefresh(jumpWeek, TYPE, subcontractorAccount);
        }
    }
}
