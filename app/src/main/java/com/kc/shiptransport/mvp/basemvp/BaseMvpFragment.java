package com.kc.shiptransport.mvp.basemvp;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
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
 * @time 2017/6/13  15:40
 * @desc ${TODD}
 */

public abstract class BaseMvpFragment extends Fragment implements BaseMvpContract.View {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_other_info)
    TextView toolbarOtherInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_time)
    AppCompatTextView titleTime;
    @BindView(R.id.title_stay_info)
    AppCompatTextView titleStayInfo;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
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
    @BindView(R.id.cb_tip_red)
    CheckBox cbTipRed;
    @BindView(R.id.cb_tip_black)
    CheckBox cbTipBlack;
    @BindView(R.id.btn_refresh)
    AppCompatButton btnRefresh;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    Unbinder unbinder;
    protected BaseMvpContract.Presenter presenter;
    @BindView(R.id.title_spinner)
    AppCompatSpinner mTitleSpinner;
    @BindView(R.id.cb_tip_blue)
    CheckBox cbTipBlue;
    @BindView(R.id.cb_tip_purple)
    CheckBox cbTipPurple;
    @BindView(R.id.ll_status_ext)
    LinearLayout llStatusExt;
    protected float dowmX;
    protected float upX;
    protected int jumpWeek = 0; // 要显示的week, 默认当周
    protected RecyclerAdapter adapter;
    protected int TYPE;

    protected String subcontractorAccount;
    protected double dowmY;
    protected float upY;
    @BindView(R.id.tv_total_ship_0)
    AppCompatTextView tvTotalShip0;
    @BindView(R.id.tv_total_ship_1)
    AppCompatTextView tvTotalShip1;
    @BindView(R.id.tv_total_ship_2)
    AppCompatTextView tvTotalShip2;
    @BindView(R.id.tv_total_ship_3)
    AppCompatTextView tvTotalShip3;
    @BindView(R.id.tv_total_ship_4)
    AppCompatTextView tvTotalShip4;
    @BindView(R.id.tv_total_ship_5)
    AppCompatTextView tvTotalShip5;
    @BindView(R.id.tv_total_ship_6)
    AppCompatTextView tvTotalShip6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voyage, container, false);
        unbinder = ButterKnife.bind(this, view);

        // 获取type类型
        TYPE = getType();

        initViews(view);
        initListener();
        // TODO 获取数据, 给子类处理
        initData();
        return view;
    }

    /**
     * 获取数据
     */
    protected abstract void initData();

    /**
     * 获取要显示的数据类型
     *
     * @return
     */
    protected abstract int getType();

    public void initListener() {
        /* 刷新 */
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doRefresh(jumpWeek, TYPE, subcontractorAccount);
            }
        });

        /* 滑动监听 */
        recyclerview.setOnTouchListener(new View.OnTouchListener() {
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

                        if (dowmY == 0) {
                            dowmY = motionEvent.getY();
                            Log.d("==", "MOVE Y = " + motionEvent.getY());
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = motionEvent.getX();
                        upY = motionEvent.getY();
                        Log.d("==", "UP X = " + motionEvent.getX());
                        Log.d("==", "UP Y = " + motionEvent.getY());

                        // Y轴位移必须小于X轴
                        if (upX - dowmX > 100 && Math.abs(upY - dowmY) < Math.abs(upX - dowmX) && dowmX != 0 && dowmY != 0) {
                            Toast.makeText(getContext(), "上一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求上一周数据
                            jumpWeek--;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_BASE, jumpWeek);
                            presenter.start(jumpWeek, TYPE, subcontractorAccount);
                        } else if (upX - dowmX < -100 && Math.abs(upY - dowmY) < Math.abs(upX - dowmX) && dowmX != 0 && dowmY != 0) {
                            Toast.makeText(getContext(), "下一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求下一周数据
                            jumpWeek++;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_BASE, jumpWeek);
                            presenter.start(jumpWeek, TYPE, subcontractorAccount);
                        }
                        dowmX = 0;
                        dowmY = 0;
                        upX = 0;
                        upY = 0;
                        break;
                }
                return false;
            }
        });

        // 已验收
        cbTipRed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.ACCEPTED, b);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // 未验收
        cbTipBlack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.NO_ACCEPTED, b);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // 已提交
        cbTipBlue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.ALREADY_COMMIT, b);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // 被退回
        cbTipPurple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.RETURNED, b);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void initViews(View view) {
        SharePreferenceUtil.saveInt(getContext(), SettingUtil.WEEK_JUMP_BASE, 0);
        subcontractorAccount = DataSupport.findAll(Subcontractor.class).get(0).getSubcontractorAccount();
        // 允许使用menu
        setHasOptionsMenu(true);

        initToolbar(toolbar);

        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        // 添加边框
        recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        // 已完善
        cbTipRed.setText(getRedText());
        // 未完善
        cbTipBlack.setText(getBlackText());

        // 已提交
        cbTipBlue.setText("蓝色字体: 已提交");
        // 被退回
        cbTipPurple.setText("紫色字体: 被退回");

        /** 根据type判断是否显示扩展状态 */
        switch (TYPE) {
            case SettingUtil.TYPE_VOYAGEINFO:
                /** 航次信息完善 */
                llStatusExt.setVisibility(View.VISIBLE);
                break;
            case SettingUtil.TYPE_SCANNER:
                /** 航次完善扫描件 */
                llStatusExt.setVisibility(View.VISIBLE);
                break;
            default:
                llStatusExt.setVisibility(View.GONE);
                break;
        }

        /** 初始化状态 */
        SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.ALREADY_COMMIT, true);
        SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.RETURNED, true);

        // 默认显示已验收与未验收数据
        SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.ACCEPTED, true);
        SharePreferenceUtil.saveBoolean(getContext(), SettingUtil.NO_ACCEPTED, true);
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

    /**
     * 初始化Toolbar
     */
    protected abstract void initToolbar(Toolbar toolbar);

    /**
     * 获取红色字体 显示的内容
     *
     * @return
     */
    protected abstract int getRedText();

    /**
     * 获取黑色字体 显示的内容
     *
     * @return
     */
    protected abstract int getBlackText();

    @Override
    public void setPresenter(BaseMvpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTitle(String data) {
        toolbarTitle.setText(data);
    }

    @Override
    public void showTitleOtherInfo(String data) {
        toolbarOtherInfo.setText(abs_showTitleOtherInfo(data));
    }

    protected abstract String abs_showTitleOtherInfo(String data);

    @Override
    public void showTime(String data) {
        titleTime.setText(data);
    }

    @Override
    public void showStayInfo(String data) {
        titleStayInfo.setText(abs_showStayInfo(data));
    }

    protected abstract String abs_showStayInfo(String data);

    @Override
    public void showWeekTask(List<String> dates) {
        if (adapter == null) {
            adapter = new RecyclerAdapter(getContext(), dates, TYPE);
            adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    abs_onItemClick(view, position);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerview.setAdapter(adapter);

        } else {
            adapter.setDates(dates);
            adapter.notifyDataSetChanged();
        }
    }

    protected abstract void abs_onItemClick(View view, int position);

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

    //    @Override
    //    public void showLoading(boolean active) {
    //        if (active) {
    //            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
    //                @Override
    //                public void onCancel(ProgressDialog dialog) {
    //                    presenter.unsubscribe();
    //                }
    //            });
    //        } else {
    //            activity.hideProgressDailog();
    //        }
    //    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            if (TYPE != 0) {
                presenter.getStayInfo(TYPE);
            }
        }
    }

    /**
     * 初始化spinner
     *
     * @param value
     */
    @Override
    public void showSpinner(final List<SubcontractorList> value) {
        mTitleSpinner.setVisibility(View.VISIBLE);
        List<String> datas = new ArrayList<>();
        datas.add("所有供应商");
        for (SubcontractorList subcontractor : value) {
            datas.add(subcontractor.getSubcontractorName());
        }

        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_hear, datas);
        // 设置样式
        arr_adapter.setDropDownViewResource(R.layout.spinner_item);
        // 加载适配器
        mTitleSpinner.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
        mTitleSpinner.setSelection(0);

        // 点击后, 筛选供应商的数据
        mTitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String) adapterView.getItemAtPosition(i);
                //Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
                if (i != 0) {
                    // 这里的账号是提供给adapter去数据库查询数据的
                    subcontractorAccount = value.get(i - 1).getSubcontractorAccount();
                    // 这里应该根据选择的账号重新
                    presenter.doRefresh(jumpWeek, TYPE, subcontractorAccount);
                } else if (i == 0) {
                    subcontractorAccount = "";
                    presenter.doRefresh(jumpWeek, TYPE, subcontractorAccount);
                }
                // 保存当前选中的供应商账号
                SharePreferenceUtil.saveString(getContext(), SettingUtil.SUBCONTRACTOR_ACCOUNT, subcontractorAccount);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * 统计每日船舶数
     * @param list
     */
    @Override
    public void showDayShipCount(List<Integer> list) {
        tvTotalShip0.setText(list.get(0) + "艘");
        tvTotalShip1.setText(list.get(1) + "艘");
        tvTotalShip2.setText(list.get(2) + "艘");
        tvTotalShip3.setText(list.get(3) + "艘");
        tvTotalShip4.setText(list.get(4) + "艘");
        tvTotalShip5.setText(list.get(5) + "艘");
        tvTotalShip6.setText(list.get(6) + "艘");
    }
}
