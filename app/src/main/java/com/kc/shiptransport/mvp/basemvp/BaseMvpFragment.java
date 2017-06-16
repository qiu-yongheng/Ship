package com.kc.shiptransport.mvp.basemvp;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.voyageinfo.RecyclerAdapter;
import com.kc.shiptransport.util.DividerGridItemDecoration;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

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


    Unbinder unbinder;
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
    protected BaseMvpContract.Presenter presenter;
    private float dowmX;
    private float upX;
    private int jumpWeek = 0; // 要显示的week, 默认当周
    private RecyclerAdapter adapter;
    private int TYPE;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voyage, container, false);
        unbinder = ButterKnife.bind(this, view);

        // 获取type类型
        TYPE = getType();

        initViews(view);
        initListener();
        // TODO 获取数据
        presenter.start(jumpWeek, TYPE);
        return view;
    }

    /**
     * 获取要显示的数据类型
     *
     * @return
     */
    protected abstract int getType();

    private void initListener() {
        /* 刷新 */
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doRefresh(jumpWeek, TYPE);
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
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = motionEvent.getX();
                        Log.d("==", "UP X = " + motionEvent.getX());

                        if (upX - dowmX > 100) {
                            Toast.makeText(getContext(), "上一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求上一周数据
                            jumpWeek--;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_SUPPLY, jumpWeek);
                            presenter.start(jumpWeek, TYPE);
                        } else if (upX - dowmX < -100) {
                            Toast.makeText(getContext(), "下一周", Toast.LENGTH_SHORT).show();
                            // TODO 请求下一周数据
                            jumpWeek++;
                            SharePreferenceUtil.saveInt(getActivity(), SettingUtil.WEEK_JUMP_SUPPLY, jumpWeek);
                            presenter.start(jumpWeek, TYPE);
                        }
                        dowmX = 0;
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
    }

    @Override
    public void initViews(View view) {
        SharePreferenceUtil.saveInt(getContext(), SettingUtil.WEEK_JUMP_BASE, 0);
        // 允许使用menu
        setHasOptionsMenu(true);

        initToolbar(toolbar);

        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        // 添加边框
        recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        cbTipRed.setText(getRedText());
        cbTipBlack.setText(getBlackText());

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
                public void onItemClick(View view, int position) {
                    abs_onItemClick(view, position);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerview.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    protected abstract void abs_onItemClick(View view, int position);

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

//    @Override
//    public void showLoading(boolean active) {
//        if (active) {
//            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
//                @Override
//                public void onCancle(ProgressDialog dialog) {
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
}
