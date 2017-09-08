package com.kc.shiptransport.mvp.upcominglist;

import android.support.v4.app.Fragment;
import android.view.View;

import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.mvp.upcoming.UpcomingContract;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/8  18:16
 * @desc ${TODD}
 */

public class UpcomingListFragment extends Fragment implements UpcomingContract.View {
    @Override
    public void initViews(View view) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(UpcomingContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showPending(List<BackLog> list) {

    }
}
