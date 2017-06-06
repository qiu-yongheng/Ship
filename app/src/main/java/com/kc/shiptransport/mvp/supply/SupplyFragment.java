package com.kc.shiptransport.mvp.supply;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.supplydetail.SupplyDetailActivity;
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
 * @time 2017/5/31  17:03
 * @desc ${TODD}
 */

public class SupplyFragment extends Fragment implements SupplyContract.View {

    Unbinder unbinder;
    @BindView(R.id.toolbar_supply_man)
    TextView toolbarSupplyMan;
    @BindView(R.id.title_time)
    AppCompatTextView titleTime;
    @BindView(R.id.title_stay_supply)
    AppCompatTextView titleStaySupply;
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
    @BindView(R.id.btn_refresh)
    AppCompatButton btnRefresh;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.toolbar_supply)
    Toolbar toolbarSupply;
    private SupplyContract.Presenter presenter;
    private SupplyActivity activity;
    private int jumpWeek = 0; // 要显示的week, 默认当周
    private float dowmX;
    private float upX;
    private SupplyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supply, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews(view);
        initListener();
        // TODO 获取数据
        presenter.start(jumpWeek);
        return view;
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

    private void initListener() {
        /* 刷新 */
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
        SharePreferenceUtil.saveInt(getContext(), SettingUtil.WEEK_JUMP_SUPPLY, 0);
        // 允许使用menu
        setHasOptionsMenu(true);
        activity = (SupplyActivity) getActivity();
        activity.setSupportActionBar(toolbarSupply);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerviewPlan.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        // 添加边框
        recyclerviewPlan.addItemDecoration(new DividerGridItemDecoration(getActivity()));
    }

    @Override
    public void setPresenter(SupplyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示验沙人
     *
     * @param supplyMan
     */
    @Override
    public void showSupplyMan(String supplyMan) {
        toolbarSupplyMan.setText("验砂人: " + supplyMan);
    }

    /**
     * 显示当前时间
     *
     * @param date
     */
    @Override
    public void showCurrentDate(String date) {
        titleTime.setText(date);
    }

    /**
     * 显示未验沙船
     */
    @Override
    public void showStaySupplyShip(String num) {
        titleStaySupply.setText("待验砂船次:" + num);
    }

    /**
     * 创建adapter, 绑定recyclerview
     *
     * @param dates
     * @param weekLists
     */
    @Override
    public void showWeekTask(List<String> dates, final List<WeekTask> weekLists) {
        if (adapter == null) {
            adapter = new SupplyAdapter(getContext(), dates, weekLists);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);
                    if (weekTasks != null && !weekTasks.isEmpty()) {
                        List<Acceptance> acceptances = DataSupport.where("ItemID = ? and isSupply = ?", String.valueOf(weekTasks.get(0).getItemID()), "1").find(Acceptance.class);

                        if (acceptances != null && !acceptances.isEmpty()) {
                            // 已验收
                            SupplyDetailActivity.startActivity(activity, weekTasks.get(0).getItemID(), true);
                        } else {
                            // 未验收
                            SupplyDetailActivity.startActivity(activity, weekTasks.get(0).getItemID(), false);
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

    /**
     * 显示每日总量
     *
     * @param integers
     */
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

    /**
     * 是否显示加载
     *
     * @param active
     */
    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("加载中", "加载中");
        } else {
            activity.hideProgressDailog();
        }
    }

    /**
     * 显示错误
     */
    @Override
    public void showError() {
        Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 退出时, 取消所有rxjava操作
     */
    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (presenter != null) {
            presenter.getStaySupplyShip();
        }
    }
}