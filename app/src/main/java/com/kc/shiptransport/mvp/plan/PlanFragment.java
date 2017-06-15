package com.kc.shiptransport.mvp.plan;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
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
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.plansetting.PlanSetActivity;
import com.kc.shiptransport.util.DividerGridItemDecoration;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

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

    Unbinder unbinder;
    @BindView(R.id.toolbar_plan_title)
    TextView toolbarPlanTitle;
    @BindView(R.id.toolbar_plan)
    Toolbar toolbarPlan;
    @BindView(R.id.title_time)
    AppCompatTextView titleTime;
    @BindView(R.id.title_task)
    AppCompatTextView titleTask;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerviewPlan;
    @BindView(R.id.tv_total_demand_0)
    AppCompatTextView tvTotalDemand0;
    @BindView(R.id.tv_total_demand_1)
    AppCompatTextView tvTotalDemand1;
    @BindView(R.id.tv_total_demand_2)
    AppCompatTextView tvTotalDemand2;
    @BindView(R.id.tv_total_demand_3)
    AppCompatTextView tvTotalDemand3;
    @BindView(R.id.tv_total_demand_4)
    AppCompatTextView tvTotalDemand4;
    @BindView(R.id.tv_total_demand_5)
    AppCompatTextView tvTotalDemand5;
    @BindView(R.id.tv_total_demand_6)
    AppCompatTextView tvTotalDemand6;
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
    @BindView(R.id.tv_tip_black)
    AppCompatTextView tvTotalQuantum;
    @BindView(R.id.btn_refresh)
    AppCompatButton btnRefresh;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;

    private PlanContract.Presenter presenter;
    private PlanAdapter adapter;
    private PlanActivity activity;
    private float dowmX;
    private float upX;
    private int jumpWeek = 0; // 要显示的week, 默认当周
    private GestureDetector mGestureDetector;

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

        // 根据当前周获取时间
        presenter.start(jumpWeek);

        return view;
    }

    private void initListener() {
        /* 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doCommit();
            }
        });

        /* 刷新数据 */
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doRefresh(jumpWeek);
            }
        });

        /* 滑动监听 */
        recyclerviewPlan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("==", "DOWM X = " + motionEvent.getX());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (dowmX == 0) {
                            dowmX = motionEvent.getX();
                            Log.d("==", "MOVE X = " + motionEvent.getX());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = motionEvent.getX();
                        Log.d("==", "UP X = " + motionEvent.getX());

                        if (upX - dowmX > 100) {
                            Toast.makeText(activity, "上一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求上一周数据
                            jumpWeek--;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_PLAN, jumpWeek);
                            presenter.start(jumpWeek);
                        } else if (upX - dowmX < -100) {
                            Toast.makeText(activity, "下一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求下一周数据
                            jumpWeek++;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_PLAN, jumpWeek);
                            presenter.start(jumpWeek);
                        }
                        dowmX = 0;
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void initViews(View view) {
        SharePreferenceUtil.saveInt(getContext(), SettingUtil.WEEK_JUMP_PLAN, 0);
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
        //presenter.getWeekTask(jumpWeek);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            presenter.getDayCount();
        }
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

    /**
     * 任务缺口
     *
     * @param value
     */
    @Override
    public void showTaskVolume(Float value) {
        String s = getResources().getText(R.string.task_valume).toString();
        titleTask.setText(s + value);
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
            adapter.setDates(dates);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 每日计划量
     *
     * @param integers
     */
    @Override
    public void showDayCount(Double[] integers) {
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
    public void showError(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 每日需求
     *
     * @param datas
     */
    @Override
    public void showDemandDayCount(Double[] datas) {
        tvTotalDemand0.setText(String.valueOf(datas[0]));
        tvTotalDemand1.setText(String.valueOf(datas[1]));
        tvTotalDemand2.setText(String.valueOf(datas[2]));
        tvTotalDemand3.setText(String.valueOf(datas[3]));
        tvTotalDemand4.setText(String.valueOf(datas[4]));
        tvTotalDemand5.setText(String.valueOf(datas[5]));
        tvTotalDemand6.setText(String.valueOf(datas[6]));
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
