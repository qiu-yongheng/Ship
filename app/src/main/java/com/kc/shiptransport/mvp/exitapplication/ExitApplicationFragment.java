package com.kc.shiptransport.mvp.exitapplication;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.exitapplicationdetail.ExitApplicationDetailActivity;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/12  11:45
 * @desc ${TODD}
 */

public class ExitApplicationFragment extends BaseMvpFragment {

    private ExitApplicationActivity activity;

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
        return SettingUtil.TYPE_EXIT_APPLICATION;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (ExitApplicationActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getRedText() {
        return R.string.exit_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.exit_black;
    }

    @Override
    protected String abs_showTitleOtherInfo(String data) {
        return data;
    }

    @Override
    protected String abs_showStayInfo(String data) {
        return "未退场航次: " + data;
    }

    @Override
    protected void abs_onItemClick(View view, int position) {
        // 点击查看详情, 传递itemID
        List<ExitAssessor> exitLists = DataSupport.where("position = ?", String.valueOf(position)).find(ExitAssessor.class);

        if (!exitLists.isEmpty()) {
            ExitApplicationDetailActivity.startActivity(getContext(), exitLists.get(0).getSubcontractorInterimApproachPlanID(), exitLists.get(0).getIsExit());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            presenter.doRefresh(jumpWeek, TYPE, subcontractorAccount);
        }
    }
}
