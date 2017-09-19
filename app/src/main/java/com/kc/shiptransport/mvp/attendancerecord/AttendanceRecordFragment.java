package com.kc.shiptransport.mvp.attendancerecord;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 邱永恒
 * @time 2017/6/28 21:13
 * @desc ${TODO}
 */

public class AttendanceRecordFragment extends Fragment implements AttendanceRecordContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_attendance_time)
    TextView mTextAttendanceTime;
    @BindView(R.id.btn_quiry)
    Button mBtnQuiry;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private AttendanceRecordActivity activity;
    private AttendanceRecordContract.Presenter presenter;
    private AttendanceRecordAdapter adapter;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_record, container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        user = DataSupport.findAll(User.class).get(0);
        presenter.getAttendance(user.getUserID(), CalendarUtil.getCurrentDate("yyyy-MM-dd"), "");
        return view;
    }

    public void initListener() {
        /** 搜索全部 */
        mBtnQuiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getAttendance(user.getUserID(), "", "");
                mTextAttendanceTime.setText("");
            }
        });

        mTextAttendanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CalendarUtil.showDatePickerDialog(getContext(), mTextAttendanceTime, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            // 选择完时间后, 直接搜索
                            presenter.getAttendance(user.getUserID(), str, "");
                        }
                    }, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void initViews(View view) {
        mTextAttendanceTime.setText(CalendarUtil.getCurrentDate("yyyy-MM-dd"));

        setHasOptionsMenu(true);
        activity = (AttendanceRecordActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_attendance);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void setPresenter(AttendanceRecordContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showAttendance(List<AttendanceRecordList> attendanceRecordLists) {
        if (adapter == null) {
            adapter = new AttendanceRecordAdapter(getContext(), attendanceRecordLists);
            mRecyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(attendanceRecordLists);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoadding(boolean isShow) {
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
}
