package com.kc.shiptransport.mvp.threadsand;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.threadsand.Layered;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.downtimelog.DowntimeLogActivity;
import com.kc.shiptransport.mvp.partition.PartitionActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.kc.shiptransport.view.PopupWindow.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kc.shiptransport.R.id.btn_cancel;


/**
 * @author qiuyongheng
 * @time 2017/7/7  8:49
 * @desc ${TODD}
 */

public class ThreadSandFragment extends Fragment implements ThreadSandContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv)
    TextView tvShipName;
    @BindView(R.id.tv_ship_type)
    TextView tvShipType;
    @BindView(R.id.tv_tide_wather)
    TextView tvTideWather;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_construction_stratification)
    TextView tvConstructionStratification;
    @BindView(R.id.et_sand_voyage)
    EditText etSandVoyage;
    @BindView(R.id.tv_construction_devision)
    TextView tvConstructionDevision;
    @BindView(R.id.tv_devision_num)
    TextView tvDevisionNum;
    @BindView(R.id.et_engineering_quantity)
    EditText etEngineeringQuantity;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    @BindView(R.id.btn_thread_sand_log)
    Button mBtnThreadSandLog;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ThreadSandActivity activity;
    private ThreadSandContract.Presenter presenter;
    private CommonPopupWindow popupWindow;
    private int measuredWidth;
    private int measuredHeight;
    private final String HINT = "请选择";
    private ThreadSandAdapter adapter;
    private int layoutID;
    private ConstructionBoat boat;
    private boolean flag = false;
    private String realDate = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_sand, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // 获取开始时间
        presenter.getDates(activity.currentDate, boat.getShipNum());

        // 获取施工分区
        presenter.getPartition(boat.getShipNum());

        return view;
    }

    @Override
    public void initViews(View view) {
        // 设置标题
        tvTitle.setText(R.string.title_thread_sand);

        setHasOptionsMenu(true);
        activity = (ThreadSandActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
            int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
            boat = all.get(position - 1);
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String shipAccount = lists.get(0).getShipAccount();

                List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                if (!boats.isEmpty()) {
                    boat = boats.get(0);
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
        /** 抛砂日志 */
        mBtnThreadSandLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DowntimeLogActivity.startActivity(getContext(), SettingUtil.TYPE_THREAD);
            }
        });


        /** 结束时间 */
        tvEndTime.setOnClickListener(new View.OnClickListener() {
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

                                            CalendarUtil.showTimeDialog(getContext(), tvEndTime, CalendarUtil.YYYY_MM_DD_HH_MM, activity.currentDate, new OnTimePickerSureClickListener() {
                                                @Override
                                                public void onSure(String str) {
                                                    /** 不能选择在开始时间之前的时间 */
                                                    // 开始时间
                                                    String startTime = tvStartTime.getText().toString();
                                                    try {
                                                        boolean isLastDate = CalendarUtil.isLastDate(startTime, str);

                                                        if (isLastDate) {
                                                            Toast.makeText(getContext(), "结束时间不能在开始时间之前", Toast.LENGTH_SHORT).show();
                                                            tvEndTime.setText("");
                                                        } else {
                                                            realDate = str;
                                                        }

                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                        realDate = "";
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
                                        String date = "";
                                        try {
                                            date = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, activity.currentDate, Calendar.DATE, 1);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        // 显示的时间
                                        String currentDate = date + " 00:00:00";
                                        // 实际上传的时间
                                        realDate = activity.currentDate + " 23:59:59";
                                        tvEndTime.setText(currentDate);
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

        /** 施工分层 */
        tvConstructionStratification.post(new Runnable() {
            @Override
            public void run() {
                measuredWidth = tvConstructionStratification.getMeasuredWidth();
                measuredHeight = tvConstructionStratification.getMeasuredHeight();
            }
        });


        // 单选
        tvConstructionStratification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }

                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setBackGroundLevel(0.8f)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);

                                recycle_view.setLayoutManager(new GridLayoutManager(getContext(), 3));

                                // 获取数据
                                final List<Layered> arr = DataSupport.order("SortNum asc").find(Layered.class);

                                adapter = new ThreadSandAdapter(getContext(), arr);
                                adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int... type) {
                                        layoutID = arr.get(position).getItemID();
                                        tvConstructionStratification.setText(arr.get(position).getLayerName());
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
                        .create();
                popupWindow.showAsDropDown(view);
            }
        });

        /** 施工分区 */
        // 跳转
        tvConstructionDevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                    PartitionActivity.startActivity(getContext(), 0, SettingUtil.TYPE_DATA_NEW);
                } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
                    PartitionActivity.startActivity(getContext(), activity.itemID, SettingUtil.TYPE_DATA_UPDATE);
                }
            }
        });

        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (boat == null) {
                    if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                        List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
                        int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
                        boat = all.get(position - 1);
                    } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
                        List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
                        if (!lists.isEmpty()) {
                            String shipAccount = lists.get(0).getShipAccount();

                            List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                            if (!boats.isEmpty()) {
                                boat = boats.get(0);
                            }
                        }
                    }
                }


                // 账号
                List<User> all = DataSupport.findAll(User.class);
                // 施工分区
                List<PartitionNum> numList = DataSupport.findAll(PartitionNum.class);
                // 开始时间
                String startTime = tvStartTime.getText().toString();
                // 结束时间
                String endTime = realDate;
                // 供砂航次
                String sandVoyage = etSandVoyage.getText().toString();
                // 施工分层
                String stratification = tvConstructionStratification.getText().toString();
                // 工程量
                String quantity = etEngineeringQuantity.getText().toString();
                // 备注
                String remark = etRemark.getText().toString();
                remark = remark.equals("添加备注") ? "" : remark;


                if (!endTime.equals(HINT) &&
                        !TextUtils.isEmpty(endTime) &&
                        !stratification.equals(HINT) &&
                        !TextUtils.isEmpty(stratification) &&
                        !sandVoyage.equals(HINT) &&
                        !TextUtils.isEmpty(sandVoyage) &&
                        !numList.isEmpty() &&
                        !TextUtils.isEmpty(quantity)) {

                    try {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("ItemID", activity.itemID);
                        jsonObject.put("ShipAccount", boat.getShipNum());
                        jsonObject.put("Layered", layoutID);
                        jsonObject.put("ShipItemNum", sandVoyage);
                        jsonObject.put("StartTime", startTime);
                        jsonObject.put("EndTime", endTime);
                        jsonObject.put("Quantity", quantity);
                        jsonObject.put("Creator", all.get(0).getUserID());
                        jsonObject.put("Remark", remark);

                        JSONArray jsonArray = new JSONArray();
                        for (PartitionNum bean : numList) {
                            JSONObject object = new JSONObject();
                            object.put("ItemID", 0);
                            object.put("PartitionName", bean.getNum());
                            jsonArray.put(object);
                        }
                        jsonObject.put("ThrowingSandPartitionList", jsonArray);

                        String json = jsonObject.toString();

                        LogUtil.d("抛砂提交: " + json);

                        presenter.commit(json);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "还有数据未填写", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void setPresenter(ThreadSandContract.Presenter presenter) {
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
    public void showStartDate(LogCurrentDateBean bean) {
        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            // 修改开始日期
            tvStartTime.setText(bean.getStartTime());
        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE) {
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String startTime = lists.get(0).getStartTime();
                tvStartTime.setText(startTime);
            } else {
                tvStartTime.setText("获取数据失败");
            }
        }

        // 详细信息
        List<User> users = DataSupport.findAll(User.class);
        // 记录人
        tvRecord.setText(users.get(0).getUserName());
        // 施工船舶
        tvShipName.setText(bean.getShipName());
        // 船舶类型
        tvShipType.setText(bean.getShipType());
        // 潮水
        tvTideWather.setText(bean.getCurrentTide());
        // 船次
        etSandVoyage.setText(bean.getShipItemNum());
    }

    @Override
    public void showPartition(PartitionSBBean bean) {
        flag = true;
        tvConstructionDevision.setText(TextUtils.isEmpty(bean.getPartition()) ? "" : bean.getPartition());
        tvDevisionNum.setText(String.valueOf(bean.getSize()));
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            // 删除
            DataSupport.deleteAll(PartitionNum.class);

            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag) {
            // 获取施工分区
            presenter.getPartition(boat.getShipNum());
        }
    }
}
