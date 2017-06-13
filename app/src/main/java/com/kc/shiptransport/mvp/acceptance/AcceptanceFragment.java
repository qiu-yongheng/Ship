package com.kc.shiptransport.mvp.acceptance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.acceptancedetail.AcceptanceDetailActivity;
import com.kc.shiptransport.util.DividerGridItemDecoration;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/1  15:12
 * @desc ${TODD}
 */

public class AcceptanceFragment extends Fragment implements AcceptanceContract.View {

    Unbinder unbinder;
    @BindView(R.id.toolbar_acceptance_man)
    TextView toolbarAcceptanceMan;
    @BindView(R.id.toolbar_acceptance)
    Toolbar toolbarAcceptance;
    @BindView(R.id.title_time)
    AppCompatTextView titleTime;
    @BindView(R.id.title_stay_acceptance)
    AppCompatTextView titleStayAcceptance;
    @BindView(R.id.recyclerview)
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
    @BindView(R.id.tv_tip_red)
    AppCompatTextView tvTaskRequire;
    @BindView(R.id.tv_tip_black)
    AppCompatTextView tvTotalQuantum;
    @BindView(R.id.btn_refresh)
    AppCompatButton btnRefresh;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.spinner_select_subcontractor)
    AppCompatSpinner spinnerSelectSubcontractor;
    private AcceptanceActivity activity;
    private AcceptanceContract.Presenter presenter;
    private int jumpWeek = 0; // 要显示的week, 默认当周
    private float dowmX;
    private float upX;
    private AcceptanceAdapter adapter;
    private String subcontractorAccount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // 默认获取所有计划任务
        presenter.start(jumpWeek);
        return view;
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
                            Log.d("==", "上一周");
                            // TODO 请求上一周数据
                            jumpWeek--;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_ACCEPTANCE, jumpWeek);
                            presenter.start(jumpWeek);
                        } else if (upX - dowmX < -100) {
                            Toast.makeText(activity, "下一周", Toast.LENGTH_SHORT).show();
                            Log.d("==", "下一周");
                            // TODO 请求下一周数据
                            jumpWeek++;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_ACCEPTANCE, jumpWeek);
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
        SharePreferenceUtil.saveInt(getContext(), SettingUtil.WEEK_JUMP_ACCEPTANCE, 0);
        setHasOptionsMenu(true);
        activity = (AcceptanceActivity) getActivity();
        activity.setSupportActionBar(toolbarAcceptance);
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
    public void setPresenter(AcceptanceContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    /**
     * 验收人
     *
     * @param acceptanceMan
     */
    @Override
    public void showAcceptanceMan(String acceptanceMan) {
        toolbarAcceptanceMan.setText("工程人员: " + acceptanceMan);
    }

    /**
     * 当前时间
     *
     * @param date
     */
    @Override
    public void showCurrentDate(String date) {
        titleTime.setText(date);
    }

    /**
     * 未验收数量
     *
     * @param num
     */
    @Override
    public void showStayAcceptanceShip(String num) {
        titleStayAcceptance.setText("待验收航次: " + num);
    }

    /**
     * 创建adapter
     * 加载recyclerview
     * 添加item的点击事件
     *
     * @param dates
     * @param weekLists
     */
    @Override
    public void showWeekTask(List<String> dates, final List<WeekTask> weekLists) {
        if (adapter == null) {
            adapter = new AcceptanceAdapter(getContext(), dates, weekLists);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);
                    // 如果item有数据, 设置点击事件
                    if (weekTasks != null && !weekTasks.isEmpty()) {
                        // 判断是否验收
                        String passReceptionSandTime = weekTasks.get(0).getPassReceptionSandTime();

                        if (passReceptionSandTime != null && !passReceptionSandTime.isEmpty()) {
                            // 已验收
                            AcceptanceDetailActivity.startActivity(activity, weekTasks.get(0).getItemID(), true, weekTasks.get(0).getSandSubcontractorPreAcceptanceEvaluationID());
                        } else {
                            // 未验收
                            AcceptanceDetailActivity.startActivity(activity, weekTasks.get(0).getItemID(), false, weekTasks.get(0).getSandSubcontractorPreAcceptanceEvaluationID());
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerviewPlan.setAdapter(adapter);
        } else {
            adapter.setAccount(subcontractorAccount);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 每日计划量统计
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
            activity.showProgressDailog("加载中", "加载中");
        } else {
            activity.hideProgressDailog();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(activity, "加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSpinner(final List<Subcontractor> value) {
        List<String> datas = new ArrayList<>();
        datas.add("请选择分包商");
        for (Subcontractor subcontractor : value) {
            datas.add(subcontractor.getSubcontractorName());
        }

        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(activity, R.layout.spinner_hear, datas);
        // 设置样式
        arr_adapter.setDropDownViewResource(R.layout.spinner_item);
        // 加载适配器
        spinnerSelectSubcontractor.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
        spinnerSelectSubcontractor.setSelection(0);

        // 点击后, 筛选分包商的数据
        spinnerSelectSubcontractor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                if (i != 0) {
                    subcontractorAccount = value.get(i - 1).getSubcontractorAccount();
                    presenter.doRefresh(jumpWeek);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        if (presenter != null) {
            presenter.getStayAcceptanceShip();
        }
    }
}
