package com.kc.shiptransport.mvp.downtimelog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/10  15:08
 * @desc ${TODD}
 */

public class DowntimeLogFragment extends Fragment implements DowntimeLogContract.View {
    @BindView(R.id.ll_push)
    LinearLayout llPush;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_type_id)
    TextView tvTypeId;
    @BindView(R.id.tv_ship_account)
    TextView tvShipAccount;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    @BindView(R.id.btn_scanner)
    Button btnScanner;
    @BindView(R.id.ll_scanner)
    LinearLayout llScanner;
    @BindView(R.id.btn_scanner_all)
    Button btnScannerAll;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_divi)
    TextView mTvDivi;
    private DowntimeLogContract.Presenter presenter;
    private DowntimeLogActivity activity;
    private boolean show = true;
    private Animation showAnim;
    private Animation hideAnim;
    private StopOptionAdapter adapter;
    private CommonPopupWindow popupWindow;
    private int typeID;
    private int measuredWidth;
    private int measuredHeight;
    private ConstructionBoatAdapter boatAdapter;
    private String shipNum = "";
    private DowntimeLogAdapter logAdapter;
    private int mType;
    private ThreadLogAdapter threadAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down_log, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        /** 获取类型 */
        mType = activity.type;

        // TODO
        return view;
    }

    @Override
    public void initViews(View view) {
        activity = (DowntimeLogActivity) getActivity();

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        /** 初始化动画 */
        showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);
        hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);

        /** 设置标题 */
        switch (mType) {
            case SettingUtil.TYPE_STOP:
                /** 停工 */
                mTvTitle.setText(R.string.title_down_log);
                break;
            case SettingUtil.TYPE_THREAD:
                /** 抛砂 */
                mTvTitle.setText(R.string.title_thread_sand_log);

                // 隐藏停工因素
                tvTypeId.setVisibility(View.GONE);
                mTvDivi.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void initListener() {
        /** 点击搜索 */
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startTime = tvStartTime.getText().toString();
                String endTime = tvEndTime.getText().toString();

                List<User> all = DataSupport.findAll(User.class);

                presenter.scanner(0, shipNum, startTime, endTime, String.valueOf(typeID == 0 ? "" : typeID), all.get(0).getUserID(), mType);
            }
        });

        /** 搜索所有 */
        btnScannerAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.scanner(0, "", "", "", "", "", mType);
                Toast.makeText(getContext(), "搜索所有", Toast.LENGTH_SHORT).show();
            }
        });

        /** 还原 */
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvStartTime.setText("");
                tvEndTime.setText("");
                tvTypeId.setText("");
                tvShipAccount.setText("");

                Toast.makeText(getContext(), "已还原", Toast.LENGTH_SHORT).show();
            }
        });

        /** 默认显示, 点击隐藏 */
        llPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show) {
                    // 当前显示, 设置隐藏
                    llScanner.startAnimation(hideAnim);
                    llScanner.setVisibility(View.GONE);
                    show = !show;
                } else {
                    // 当前隐藏, 设置显示
                    llScanner.startAnimation(showAnim);
                    llScanner.setVisibility(View.VISIBLE);
                    show = !show;
                }
            }
        });

        /** 开始时间 */
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showDatePickerDialog(getContext(), tvStartTime);
            }
        });

        /** 结束时间 */
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showDatePickerDialog(getContext(), tvEndTime);
            }
        });

        /** 停工原因 */
        tvTypeId.post(new Runnable() {
            @Override
            public void run() {
                measuredWidth = tvTypeId.getMeasuredWidth();
                measuredHeight = tvTypeId.getMeasuredHeight();
            }
        });

        tvTypeId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 初始化控件
                // 获取数据
                //取值范围0.0f-1.0f 值越小越暗
                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);

                                recycle_view.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                // 获取数据
                                final List<StopOption> arr = DataSupport.where("Name is not null").order("OptionType asc").find(StopOption.class);


                                adapter = new StopOptionAdapter(getContext(), arr);
                                adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int... type) {
                                        // 保存类型ID
                                        typeID = arr.get(position).getItemID();
                                        tvTypeId.setText(arr.get(position).getName());

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });

                                recycle_view.setAdapter(adapter);
                            }
                        })
                        .setOutsideTouchable(true)
                        .setBackGroundLevel(0.8f)//取值范围0.0f-1.0f 值越小越暗
                        .create();
                // 向上弹出
                popupWindow.showAsDropDown(view, 0, -(popupWindow.getHeight() + view.getMeasuredHeight()));
            }
        });

        /** 船舶账号 */
        tvShipAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 初始化控件
                // 获取数据
                //取值范围0.0f-1.0f 值越小越暗
                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);

                                recycle_view.setLayoutManager(new GridLayoutManager(getContext(), 2));

                                // 获取数据
                                final List<ConstructionBoat> arr = DataSupport.findAll(ConstructionBoat.class);


                                boatAdapter = new ConstructionBoatAdapter(getContext(), arr);
                                boatAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int... type) {
                                        // 保存船舶ID
                                        shipNum = arr.get(position).getShipNum();
                                        tvShipAccount.setText(arr.get(position).getShipName());

                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });

                                recycle_view.setAdapter(boatAdapter);
                            }
                        })
                        .setOutsideTouchable(true)
                        .setBackGroundLevel(0.8f)//取值范围0.0f-1.0f 值越小越暗
                        .create();
                // 向上弹出
                popupWindow.showAsDropDown(view, 0, -(popupWindow.getHeight() + view.getMeasuredHeight()));
            }
        });
    }

    @Override
    public void setPresenter(DowntimeLogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 停工日志
     * @param list
     */
    @Override
    public void showLog(List<DownLogBean> list) {
        if (logAdapter == null) {
            logAdapter = new DowntimeLogAdapter(getContext(), list);
            logAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });

            recyclerview.setAdapter(logAdapter);
        } else {
            logAdapter.setDates(list);
            logAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 抛砂日志
     * @param list
     */
    @Override
    public void showThreadLog(List<ThreadSandLogBean> list) {
        if (threadAdapter == null) {
            threadAdapter = new ThreadLogAdapter(getContext(), list);
            recyclerview.setAdapter(threadAdapter);
        } else {
            threadAdapter.setDates(list);
            threadAdapter.notifyDataSetChanged();
        }
    }
}
