package com.kc.shiptransport.mvp.downtime;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.downtimelog.DowntimeLogActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.kc.shiptransport.view.PopupWindow.CommonUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kc.shiptransport.R.id.btn_cancel;


/**
 * @author qiuyongheng
 * @time 2017/7/6  17:19
 * @desc ${TODD}
 */

public class DowntimeFragment extends Fragment implements DowntimeContract.View {

    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.text_start_time)
    TextView textStartTime;
    @BindView(R.id.text_end_time)
    TextView textEndTime;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_down_time)
    Button btnDownTime;
    @BindView(R.id.btn_down_log)
    Button btnDownLog;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private DowntimeActivity activity;
    private DowntimeAdapter adapter;
    private DowntimeContract.Presenter presenter;
    private int stopType = -1;
    private ConstructionBoat boat;
    private CommonPopupWindow popupWindow;
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

//        // 设置当前施工船舶
//        List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
//        int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
//
//        boat = all.get(position - 1);

        // 获取停工因素
        presenter.getStopOptions();
        // 获取开始日期
        presenter.getStartDate(activity.currentDate, boat.getShipNum());
        return view;
    }

    @Override
    public void initViews(View view) {

        setHasOptionsMenu(true);
        activity = (DowntimeActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));


        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            if (boat != null) {
                tvTitle.setText("施工船舶: " + boat.getShipName());
            } else {
                List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
                int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);

                boat = all.get(position - 1);
                tvTitle.setText("施工船舶: " + boat.getShipName());
            }
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String shipAccount = lists.get(0).getShipAccount();

                List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                if (!boats.isEmpty()) {
                    boat = boats.get(0);
                    tvTitle.setText("施工船舶: " + boat.getShipName());
                }
            }
        }
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
    public void initListener() {
        // 停工
        btnDownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开始时间
                String startTime = textStartTime.getText().toString();
                // 结束时间
                String endTime = textEndTime.getText().toString();
                // 备注
                String remark = etRemark.getText().toString();

                List<User> users = DataSupport.findAll(User.class);

                if (stopType != -1 && !TextUtils.isEmpty(endTime) && !endTime.equals("请选择时间")) {
                    if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                        presenter.stop(0, boat.getShipNum(), startTime, endTime, users.get(0).getUserID(), stopType, remark);
                    } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
                        presenter.stop(activity.itemID, boat.getShipNum(), startTime, endTime, users.get(0).getUserID(), stopType, remark);
                    }

                } else {
                    Toast.makeText(getContext(), "停工因素或结束时间未选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /** 选择结束时间 */
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 添加到当天结束选项 */
                if (popupWindow != null && popupWindow.isShowing())
                    return;
                View upView = LayoutInflater.from(getContext()).inflate(R.layout.popup_up, null);
                //测量View的宽高
                CommonUtil.measureWidthAndHeight(upView);
                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_up)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                        .setBackGroundLevel(0.9f)//取值范围0.0f-1.0f 值越小越暗
                        .setAnimationStyle(R.style.AnimFullUp)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                Button btnSelect = (Button) view.findViewById(R.id.btn_select_time);
                                Button btnfinish = (Button) view.findViewById(R.id.btn_finish_time);
                                Button btnCancel = (Button) view.findViewById(btn_cancel);

                                /** 选择时间 */
                                btnSelect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            if (popupWindow != null) {
                                                popupWindow.dismiss();
                                            }

                                            CalendarUtil.showTimeDialog(getContext(), textEndTime, CalendarUtil.YYYY_MM_DD_HH_MM, activity.currentDate, new OnTimePickerSureClickListener() {
                                                @Override
                                                public void onSure(String str) {
                                                    /** 不能选择在开始时间之前的时间 */
                                                    // 开始时间
                                                    String startTime = textStartTime.getText().toString();

                                                    boolean isLastDate = false;
                                                    try {
                                                        isLastDate = CalendarUtil.isLastDate(startTime, str);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }

                                                    if (isLastDate) {
                                                        Toast.makeText(getContext(), "结束时间不能在开始时间之前", Toast.LENGTH_SHORT).show();
                                                        textEndTime.setText("");
                                                    }


                                                }
                                            }, false);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                /** 至当天结束 */
                                btnfinish.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                        String currentDate = activity.currentDate + " 24:00";
                                        textEndTime.setText(currentDate);
                                    }
                                });

                                /** 取消 */
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }
                                });

                                view.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                        return true;
                                    }
                                });
                            }
                        })
                        .create();
                popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
            }
        });

        /** 跳转到停工日志 */
        btnDownLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                DowntimeLogActivity.startActivity(getContext(), SettingUtil.TYPE_STOP);
            }
        });
    }

    @Override
    public void setPresenter(DowntimeContract.Presenter presenter) {
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

    @Override
    public void showGetStopOptions(final List<StopOption> stopOptions) {
        if (adapter == null) {
            adapter = new DowntimeAdapter(getContext(), stopOptions);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, final int position, final int... type) {

                    final Runnable r = new Runnable() {
                        public void run() {
                            // 刷新
                            adapter.notifyItemChanged(type[0]);
                            // 保存选中类型
                            stopType = stopOptions.get(position).getItemID();
                        }
                    };
                    handler.post(r);

                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(stopOptions);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showStopResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "停工成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "停工失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showStartDate(LogCurrentDateBean bean) {
        if (textStartTime == null) {
            return;
        }

        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            if (bean != null) {
                textStartTime.setText(bean.getStartTime());
            } else {
                textStartTime.setText("获取数据失败");
            }
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String startTime = lists.get(0).getStartTime();
                textStartTime.setText(startTime);
            } else {
                textStartTime.setText("获取数据失败");
            }
        }

    }
}
