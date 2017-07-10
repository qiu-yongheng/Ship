package com.kc.shiptransport.mvp.attendance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.attendancerecord.AttendanceRecordActivity;
import com.kc.shiptransport.util.CalendarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/28  14:32
 * @desc ${TODD}
 */

public class AttendanceFragment extends Fragment implements AttendanceContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.btn_quiry)
    Button mBtnQuiry;
    private AttendanceContract.Presenter presenter;
    private AttendanceActivity activity;
    private AttendanceAdapter adapter;
    private int mTypeID = -1;
    private Subcontractor subcontractor;
    private String time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        presenter.subscribe();
        // 获取考勤类型
        presenter.getAttendanceTypeList();
        return view;
    }

    public void initListener() {
        // 提交
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTypeID != -1) {
                    String trim = etRemark.getText().toString().trim();
                    trim = trim.equals("添加备注") ? "" : trim;
                    presenter.commit(mTypeID, subcontractor.getSubcontractorAccount(), trim, textTime.getText().toString());
                } else {
                    Toast.makeText(getContext(), "没有选择考勤类型", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 查看记录
        mBtnQuiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceRecordActivity.startActivity(getContext());
            }
        });

        // 修改日期
        textTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showDatePickerDialog(getContext(), textTime);
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AttendanceActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_attendance);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
    public void setPresenter(AttendanceContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showAttendanceType(final List<AttendanceType> list) {
        if (adapter == null) {
            adapter = new AttendanceAdapter(getContext(), list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    // 刷新
                    adapter.notifyItemChanged(type[0]);
                    // 保存选中类型
                    mTypeID = list.get(position).getItemID();
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

    @Override
    public void showCreate(Subcontractor subcontractor) {
        this.subcontractor = subcontractor;
        textName.setText(subcontractor.getSubcontractorName());
    }

    @Override
    public void showTime(String time) {
        this.time = time;
        textTime.setText(time);
    }

    @Override
    public void showResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
