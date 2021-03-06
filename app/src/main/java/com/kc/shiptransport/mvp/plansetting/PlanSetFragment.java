package com.kc.shiptransport.mvp.plansetting;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.shipselect.ShipSelectActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author qiuyongheng
 * @time 2017/5/17  17:13
 * @desc ${TODD}
 */

public class PlanSetFragment extends Fragment implements PlanSetContract.View {


    @BindView(R.id.tv_plan_set_title)
    AppCompatTextView tvPlanSetTitle;
    @BindView(R.id.tv_plan_set_date)
    AppCompatTextView tvPlanSetDate;
    @BindView(R.id.toolbar_plan_set)
    Toolbar toolbarPlanSet;
    @BindView(R.id.tv_select)
    AppCompatTextView tvSelect;
    @BindView(R.id.spinner_select_date)
    AppCompatSpinner spinnerSelectDate;
    @BindView(R.id.btn_set_cancel)
    AppCompatButton btnSetCancel;
    @BindView(R.id.btn_set_commit)
    AppCompatButton btnSetCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview_add)
    RecyclerView recyclerviewAdd;
    @BindView(R.id.tv_ship_num)
    TextView tvShipNum;
    @BindView(R.id.tv_plan_num)
    TextView tvPlanNum;
    @BindView(R.id.ll_plan_set_detail)
    LinearLayout llPlanSetDetail;
    private Unbinder unbinder;
    private PlanSetContract.Presenter presenter;
    private PlanSetActivity activity;
    private PlanSetAdapter adapter;
    private String selectData;
    private int jumpWeek;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_set, container, false);
        unbinder = ButterKnife.bind(this, view);
        jumpWeek = SharePreferenceUtil.getInt(getActivity(), SettingUtil.WEEK_JUMP);
        initViews(view);
        initListener();
        return view;
    }

    public void initListener() {
        spinnerSelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("==", "PlanSetFragment");
                // 设置title时间
                tvPlanSetDate.setText(CalendarUtil.getdateOfWeek("yyyy年MM月dd日", jumpWeek).get(spinnerSelectDate.getSelectedItemPosition()));

                // 保存当前选中的日期
                selectData = CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek).get(spinnerSelectDate.getSelectedItemPosition());

                // TODO 根据选择时间, 重新从数据库获取weektask数据
                presenter.getShipCategory(selectData);

                // 设置船舶数
                presenter.getShipCount(selectData);

                // 设置计划量
                presenter.getPlanMeasure(selectData);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // 清空当前选中日期的任务
                // 需要弹出dailog
            }
        });

        btnSetCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
    }


    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (PlanSetActivity) getActivity();
        activity.setSupportActionBar(toolbarPlanSet);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);

        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, CalendarUtil.getdateOfWeek("yyyy-MM-dd", jumpWeek));
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinnerSelectDate.setAdapter(arr_adapter);
        //根据上一个界面传过来的position设置当前显示的item
        spinnerSelectDate.setSelection(activity.currentSelectItem);

        // 创建线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        // 可选: 设置类型
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewAdd.setLayoutManager(layoutManager);
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
    public void setPresenter(PlanSetContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {

            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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
    public void showError(String msg) {
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("==", "PlanSetFragment Resume");
        if (selectData != null) {
            presenter.getShipCategory(selectData);
            presenter.getShipCount(selectData);
            presenter.getPlanMeasure(selectData);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 获取数据, 显示船舶分类
     * 从数据库获取所有数据
     *
     * @param value
     * @param date
     * @param isAllow
     */
    @Override
    public void showShipCategory(final List<Ship> value, final String date, Boolean isAllow) {
        if (adapter == null) {
            adapter = new PlanSetAdapter(activity, value, date, jumpWeek, isAllow);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    if (type[0] == 1) {
                        // 传递类型
                        navigationToShipSelectActivity(value.get(position).getShipType());
                    } else {
                        Toast.makeText(getContext(), "该日期不能修改计划, 请重新选择", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerviewAdd.setAdapter(adapter);
        } else {
            adapter.setDates(value, date, isAllow);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showShipcount(int num) {
        tvShipNum.setText("船舶数: " + num);
    }

    @Override
    public void showPlanMeasure(double measure) {
        tvPlanNum.setText("计划量: " + measure);
    }

    /**
     * 跳转
     *
     * @param type
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void navigationToShipSelectActivity(String type) {
        Intent intent = new Intent(activity, ShipSelectActivity.class);
        Bundle bundle = new Bundle();
        // 传递船舶类型
        bundle.putString("ShipSelectActivity_type", type);
        // 传递当前日期
        bundle.putString("ShipSelectActivity_date", selectData);
        intent.putExtras(bundle);
        activity.startActivity(intent, bundle);
    }
}
