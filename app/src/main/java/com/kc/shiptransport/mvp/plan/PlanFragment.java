package com.kc.shiptransport.mvp.plan;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.plansetting.PlanSetActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.DividerGridItemDecoration;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/5/16  19:51
 * @desc ${TODD}
 */

public class PlanFragment extends Fragment implements PlanContract.View {

    @BindView(R.id.toolbar_plan)
    Toolbar toolbarPlan;
    Unbinder unbinder;
    @BindView(R.id.toolbar_plan_title)
    TextView toolbarPlanTitle;
    @BindView(R.id.title_time)
    AppCompatTextView titleTime;
    @BindView(R.id.title_task)
    AppCompatTextView titleTask;
    @BindView(R.id.recyclerview_plan)
    RecyclerView recyclerviewPlan;
    @BindView(R.id.tv_total_0)
    AppCompatTextView tvTotal0;
    @BindView(R.id.tv_total_1)
    AppCompatTextView tvTotal1;
    @BindView(R.id.tv_total_2)
    AppCompatTextView tvTotal2;
    @BindView(R.id.tv_total_3)
    AppCompatTextView tvTotal3;
    @BindView(R.id.tv_total_4)
    AppCompatTextView tvTotal4;
    @BindView(R.id.tv_total_5)
    AppCompatTextView tvTotal5;
    @BindView(R.id.tv_total_6)
    AppCompatTextView tvTotal6;
    @BindView(R.id.tv_task_require)
    AppCompatTextView tvTaskRequire;
    @BindView(R.id.tv_total_quantum)
    AppCompatTextView tvTotalQuantum;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.btn_refresh)
    AppCompatButton btnRefresh;
    private PlanContract.Presenter presenter;
    private PlanAdapter adapter;
    private PlanActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews(view);

        initListener();

        // TODO 获取数据 网络请求
        presenter.subscribe();
        // 网络请求
        List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
        presenter.doRefresh(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount(), CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY), CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY));
        return view;
    }

    private void initListener() {
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCommit();
            }
        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doRefresh(DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount(), CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SUNDAY), CalendarUtil.getSelectDate("yyyy-MM-dd", Calendar.SATURDAY));
            }
        });
    }

    @Override
    public void initViews(View view) {
        // 允许使用menu
        setHasOptionsMenu(true);
        activity = (PlanActivity) getActivity();
        activity.setSupportActionBar(toolbarPlan);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerviewPlan.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        // 添加边框
        recyclerviewPlan.addItemDecoration(new DividerGridItemDecoration(getActivity()));
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
    public void setPresenter(PlanContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 订阅异步任务
     */
    @Override
    public void onResume() {
        super.onResume();
        presenter.getWeekTask();
        Log.d("==", "PlanFragment");
    }

    /**
     * 取消订阅异步任务
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTitle(String title) {
        toolbarPlanTitle.setText(title);
    }

    @Override
    public void showCurrentDate(String date) {
        titleTime.setText(date);
    }

    @Override
    public void showTaskVolume() {

    }

    @Override
    public void showTaskRequire() {

    }

    @Override
    public void showTotalTaskVolume(int total) {
        tvTotalQuantum.setText("计划量: " + total);
    }

    /**
     * 给adapter绑定数据
     *
     * @param dates
     * @param weekLists
     */
    @Override
    public void showWeekTask(final List<String> dates, final List<WeekTask> weekLists) {
        if (adapter == null) {
            adapter = new PlanAdapter(getActivity(), dates, weekLists);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (position < 7) {
                        //Toast.makeText(getActivity(), dates.get(position) + "", Toast.LENGTH_SHORT).show();
                        navigationToPlanSetActivity(position);
                    } else {
                        WeekTask task = DataSupport.where("position = ?", String.valueOf(position)).findFirst(WeekTask.class);
                        if (task != null) {
                            String msg = "船舶: " + task.getShipName() + "\n供沙量: " + task.getSandSupplyCount();
                            activity.showDailog("任务", msg, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    activity.hideDailog();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerviewPlan.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDayCount(Integer[] integers) {
        tvTotal0.setText(String.valueOf(integers[0]));
        tvTotal1.setText(String.valueOf(integers[1]));
        tvTotal2.setText(String.valueOf(integers[2]));
        tvTotal3.setText(String.valueOf(integers[3]));
        tvTotal4.setText(String.valueOf(integers[4]));
        tvTotal5.setText(String.valueOf(integers[5]));
        tvTotal6.setText(String.valueOf(integers[6]));
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("加载中", "加载中");
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showSuccess() {
        Toast.makeText(activity, "刷新成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转
     *
     * @param position
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void navigationToPlanSetActivity(int position) {
        Intent intent = new Intent(activity, PlanSetActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("PlanSetActivity", position);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
