package com.kc.shiptransport.mvp.attendanceaudit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/30  16:18
 * @desc ${TODD}
 */

public class AttendanceAuditFragment extends Fragment implements AttendanceAuditContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    private AttendanceAuditContract.Presenter presenter;
    private AttendanceAuditActivity activity;
    private AttendanceAuditAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_audit, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // 获取考勤审核列表
        presenter.getAttendance();
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AttendanceAuditActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.attendance_audit);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(AttendanceAuditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showAttendance(final List<AttendanceRecordList> list) {
        if (adapter == null) {
            adapter = new AttendanceAuditAdapter(getContext(), list);
            adapter.setOnRecyclerViewItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    AttendanceRecordList recordList = list.get(position);
                    switch (type[0]) {
                        case 1:
                            // TODO 审核通过
                            presenter.commitAudit(recordList.getItemID(), recordList.getAttendanceTypeID(), recordList.getCreator(), recordList.getRemarkForCheck(), 1);
                            break;
                        case -1:
                            // TODO 审核不通过
                            presenter.commitAudit(recordList.getItemID(), recordList.getAttendanceTypeID(), recordList.getCreator(), recordList.getRemarkForCheck(), -1);
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
        }
    }
}
