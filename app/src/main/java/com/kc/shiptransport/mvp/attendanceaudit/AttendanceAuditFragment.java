package com.kc.shiptransport.mvp.attendanceaudit;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
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
    @BindView(R.id.btn_agree)
    Button btnAgree;
    @BindView(R.id.btn_no_agree)
    Button btnNoAgree;
    @BindView(R.id.ll_audit)
    LinearLayout llAudit;
    private AttendanceAuditContract.Presenter presenter;
    private AttendanceAuditActivity activity;
    private AttendanceAuditAdapter adapter;
    private Animation showAnim;
    private Animation hideAnim;

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

        /** 初始化动画 */
        showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);
        hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
    }

    /**
     * 加载menu菜单
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.attendance_setting, menu);
    }

    /**
     * 设置menu item的点击事件
     * 这个方法只在onCreateOptionsMenu 创建的菜单被选中时才会被触发
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.select_all:
                // 全部选中
                setVisible();

                // 把所有数据的状态都修改为选中
                List<AttendanceRecordList> list = adapter.list;
                if (list != null) {
                    for (AttendanceRecordList bean : list) {
                        bean.setIsSelect(1);
                    }

                    DataSupport.saveAll(list);

                    // 刷新
                    showAttendance(list);
                }
                break;
            case R.id.unselect_all:
                // 取消全部选中
                setGone();

                // 把所有数据的状态都修改为未选中
                List<AttendanceRecordList> lists = adapter.list;
                if (lists != null) {
                    for (AttendanceRecordList bean : lists) {
                        bean.setIsSelect(0);
                    }

                    DataSupport.saveAll(lists);

                    // 刷新
                    showAttendance(lists);
                }
                break;
        }
        return true;
    }

    /**
     * 隐藏
     */
    private void setGone() {
        if (llAudit.getVisibility() != View.GONE) {
            llAudit.startAnimation(hideAnim);
            llAudit.setVisibility(View.GONE);
        }
    }

    /**
     * 显示
     */
    private void setVisible() {
        if (llAudit.getVisibility() != View.VISIBLE) {
            llAudit.startAnimation(showAnim);
            llAudit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initListener() {
        // 同意
        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("IsCheck", 1);
                DataSupport.updateAll(AttendanceRecordList.class, contentValues, "isSelect = ?", String.valueOf(1));

                List<AttendanceRecordList> list = DataSupport.where("isSelect = ?", String.valueOf(1)).find(AttendanceRecordList.class);
                presenter.commitAudit(list, 0, 1);
            }
        });

        // 不同意
        btnNoAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("IsCheck", -1);
                DataSupport.updateAll(AttendanceRecordList.class, contentValues, "isSelect = ?", String.valueOf(1));

                List<AttendanceRecordList> list = DataSupport.where("isSelect = ?", String.valueOf(1)).find(AttendanceRecordList.class);
                presenter.commitAudit(list, 0, 1);
            }
        });
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

                    ArrayList<AttendanceRecordList> lists = new ArrayList<>();
                    switch (type[0]) {
                        case 1:
                            // TODO 审核通过
                            recordList.setIsCheck(1);
                            lists.add(recordList);
                            presenter.commitAudit(lists, position, 0);
                            break;
                        case -1:
                            // TODO 审核不通过
                            recordList.setIsCheck(-1);
                            lists.add(recordList);
                            presenter.commitAudit(lists, position, 0);
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    // 判断是否有控件被选中, 有, 显示, 没有, 隐藏
                    List<AttendanceRecordList> lists = DataSupport.where("isSelect = ?", "1").find(AttendanceRecordList.class);

                    if (lists.isEmpty()) {
                        setGone();
                    } else {
                        setVisible();
                    }

                    // 刷新position显示
                    //adapter.notifyItemChanged(position);
                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showResult(boolean isSuccess, int position) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            // 删除position对应的数据
            adapter.delete(position);
        } else {
            Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMutiResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            // 重新请求数据
            presenter.getAttendance();
        } else {
            Toast.makeText(getContext(), "提交失败", Toast.LENGTH_SHORT).show();
        }
    }
}
