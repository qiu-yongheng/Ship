package com.kc.shiptransport.mvp.exitassessor;

import android.app.ProgressDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.basemvp.BaseMvpFragment;
import com.kc.shiptransport.mvp.exitapplicationassessor.ExitApplicationAssessorActivity;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/6  15:32
 * @desc ${TODD}
 */

public class ExitAssessorFragment extends BaseMvpFragment{

    private ExitAssessorActivity activity;

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
        return SettingUtil.TYPE_EXIT_ASSESSOR;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        activity = (ExitAssessorActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
    }

    @Override
    protected int getRedText() {
        return R.string.exit_assessor_red;
    }

    @Override
    protected int getBlackText() {
        return R.string.exit_assessor_black;
    }

    @Override
    protected String abs_showTitleOtherInfo(String data) {
        return data;
    }

    @Override
    protected String abs_showStayInfo(String data) {
        return "未退场审核航次: " + data;
    }

    @Override
    protected void abs_onItemClick(View view, int position) {
        List<ExitAssessor> list = DataSupport.where("position = ?", String.valueOf(position)).find(ExitAssessor.class);
        if (!list.isEmpty()) {
            ExitApplicationAssessorActivity.startActivity(getContext(), list.get(0).getSubcontractorInterimApproachPlanID(), (list.get(0).getIsExit() == 1));
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
