package com.kc.shiptransport.mvp.recordedsand;


import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.recordedsanddetaillist.RecordedSandDetailListActivity;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

/**
 * @author qiuyongheng
 * @time 2017/6/16  13:30
 * @desc ${TODD}
 */

public class RecordedSandFragment extends BaseMvpFragment {

    private RecordedSandActivity activity;

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog(getResources().getString(R.string.dialog_loading), getResources().getString(R.string.dialog_loading), new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }

    /**
     * 　初始化数据
     */
    @Override
    protected void initData() {
        presenter.subscribe();
        // 显示所有供应商
        presenter.getTime(jumpWeek);
        presenter.getSubcontractorList();
    }

    /**
     * TODO
     *
     * @return
     */
    @Override
    protected int getType() {
        return SettingUtil.TYPE_RECORDEDSAND;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (RecordedSandActivity) getActivity();
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
        return R.string.recorded_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.recorded_black;
    }

    @Override
    protected String abs_showTitleOtherInfo(String data) {
        return data;
    }

    @Override
    protected String abs_showStayInfo(String data) {
        return "未过砂航次: " + data;
    }

    /**
     * 点击跳转到过砂记录界面
     *
     * @param view
     * @param position
     */
    @Override
    protected void abs_onItemClick(View view, int position) {
        RecordList recordList = DataSupport.where("position = ?", String.valueOf(position)).find(RecordList.class).get(0);
        if (recordList.getIsFinish() == 1) {
            // 跳转到查看界面
            //RecordedSandShowActivity.startActivity(getActivity(), recordList.getItemID()); // 以toolbar分页的方式显示数据
            // 跳转到列表界面, 只读
            RecordedSandDetailListActivity.startActivity(getContext(), recordList.getItemID(), true);
        } else {
            // 跳转到填写界面
            //RecordedSandDetailActivity.startActivity(getActivity(), position);
            RecordedSandDetailListActivity.startActivity(getContext(), recordList.getItemID(), false);
        }
    }
}
