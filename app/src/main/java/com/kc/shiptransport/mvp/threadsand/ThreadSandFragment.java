package com.kc.shiptransport.mvp.threadsand;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.logmanager.LogManagerList;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.db.pump.PumpShip;
import com.kc.shiptransport.db.thread.ThreadShip;
import com.kc.shiptransport.db.threadsand.Layered;
import com.kc.shiptransport.db.threadsand.ThreadDetailInfo;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.downtimelog.DowntimeLogActivity;
import com.kc.shiptransport.mvp.partition.PartitionActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.DBUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static org.litepal.crud.DataSupport.findAll;


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
    @BindView(R.id.tv_sand_ship)
    TextView tvSandShip;
    @BindView(R.id.cb_already_clear)
    CheckBox cbAlreadyClear;
    private ThreadSandActivity activity;
    private ThreadSandContract.Presenter presenter;
    private CommonPopupWindow popupWindow;
    private int measuredWidth;
    private int measuredHeight;
    private final String HINT = "请选择";
    private ThreadSandAdapter adapter;
    //private int layoutID;
    private ConstructionBoat boat;
    private boolean flag = false;
    private String realDate = "";
    private CommonPopupWindow sandShipPupWindow;
    private int sandWidth;
    private int sandHeight;
    private int isClear = 0;
    private String SandHandlingShipID = "";
    private String sandShipName;
    private String startTime;
    private String pumpShipID;
    private String pumpShipName;
    private String endTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_sand, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();


        /**
         * 根据类型加载数据
         */
        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
            List<ConstructionBoat> all = findAll(ConstructionBoat.class);
            int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
            boat = all.get(position - 1);
        } else {
            /** 修改数据 */
            List<LogManagerList> lists = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(LogManagerList.class);
            if (!lists.isEmpty()) {
                String shipAccount = lists.get(0).getShipAccount();
                List<ConstructionBoat> boats = DataSupport.where("ShipNum = ?", shipAccount).find(ConstructionBoat.class);
                if (!boats.isEmpty()) {
                    boat = boats.get(0);
                }

                // 回显开始时间
                startTime = lists.get(0).getStartTime();
                tvStartTime.setText(startTime);
                // 回显结束时间
                String endTime = lists.get(0).getEndTime();
                tvEndTime.setText(endTime);
                realDate = endTime;
                //                // 回显Remark
                //                etRemark.setText(lists.get(0).getRemark());


                switch (activity.type) {
                    case SettingUtil.TYPE_DATA_UPDATE_THREAD:
                        presenter.getDetailThreadInfo(lists.get(0).getItemID());
                        break;
                    case SettingUtil.TYPE_DATA_UPDATE_BCF:
                        presenter.getDetailBCFInfo(lists.get(0).getItemID());
                        break;
                }

            } else {
                tvStartTime.setText("获取数据失败");
            }
        }

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
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);


        // 泵砂船账号
        int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_PUMP_SHIP_POSITION);
        List<PumpShip> pumpShips = DataSupport.findAll(PumpShip.class);
        if (position == 0) {
            pumpShipID = "";
        } else {
            PumpShip pumpShip = pumpShips.get(position - 1);
            pumpShipID = pumpShip.getShipNum();
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
                if (activity.isAllow) {
                    try {
                        // 默认结束时间为开始时间+2小时
                        if (TextUtils.isEmpty(startTime)) {
                            ToastUtil.tip(getContext(), "开始时间不能为空");
                            return;
                        }

                        String offsetDate = "";
                        if (TextUtils.isEmpty(endTime)) {
                            offsetDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS, startTime, Calendar.HOUR, 2);
                        } else {
                            offsetDate = endTime;
                        }

                        CalendarUtil.showTimeDialog(getContext(), tvEndTime, CalendarUtil.YYYY_MM_DD_HH_MM_SS, offsetDate, new OnTimePickerSureClickListener() {
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
                        }, new OnTimePickerLastDateClickListener() {
                            @Override
                            public void onLastDate() {
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
                        }, false, true);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.tip(getContext(), "不能修改结束时间");
                }
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
                        .setBackGroundLevel(0.9f)
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
                                        //layoutID = arr.get(position).getItemID();
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

        /** 供砂船名 */
        tvSandShip.post(new Runnable() {
            @Override
            public void run() {
                sandWidth = tvSandShip.getMeasuredWidth();
                sandHeight = tvSandShip.getMeasuredHeight();
            }
        });
        tvSandShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sandShipPupWindow != null && sandShipPupWindow.isShowing()) {
                    return;
                }

                // 获取数据
                final List<ThreadShip> list = DataSupport.order("rownumber asc").find(ThreadShip.class);

                if (list.isEmpty()) {
                    ToastUtil.tip(getContext(), "当前日期没有可以选择的供砂船舶");
                    return;
                }

                sandShipPupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(sandWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setBackGroundLevel(0.9f)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);

                                recycle_view.setLayoutManager(new GridLayoutManager(getContext(), 3));


                                CommonAdapter<ThreadShip> commonAdapter = new CommonAdapter<ThreadShip>(getContext(), R.layout.item_thread_sand, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, final ThreadShip threadShip, int position) {
                                        holder.setText(R.id.tv, threadShip.getShipName())
                                                .setOnClickListener(R.id.tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                                                            /** 随便选择 */
                                                            // 供砂船舶账号
                                                            SandHandlingShipID = threadShip.getShipAccount();
                                                            sandShipName = threadShip.getShipName();
                                                            tvSandShip.setText(threadShip.getShipName());
                                                            etSandVoyage.setText(threadShip.getShipItemNum());
                                                            if (sandShipPupWindow != null) {
                                                                sandShipPupWindow.dismiss();
                                                            }
                                                        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE_THREAD) {
                                                            /** 只能选择抛砂船 */
                                                            if (threadShip.getShipName().contains("BCF")) {
                                                                ToastUtil.tip(getContext(), "不能修改供砂船为BCF船");
                                                            } else {
                                                                // 供砂船舶账号
                                                                SandHandlingShipID = threadShip.getShipAccount();
                                                                sandShipName = threadShip.getShipName();
                                                                tvSandShip.setText(threadShip.getShipName());
                                                                etSandVoyage.setText(threadShip.getShipItemNum());
                                                                if (sandShipPupWindow != null) {
                                                                    sandShipPupWindow.dismiss();
                                                                }
                                                            }
                                                        } else if (activity.type == SettingUtil.TYPE_DATA_UPDATE_BCF) {
                                                            /** 只能选择BCF船 */
                                                            if (!threadShip.getShipName().contains("BCF")) {
                                                                ToastUtil.tip(getContext(), "只能选择BCF船为供砂船舶");
                                                            } else {
                                                                // 供砂船舶账号
                                                                SandHandlingShipID = threadShip.getShipAccount();
                                                                sandShipName = threadShip.getShipName();
                                                                tvSandShip.setText(threadShip.getShipName());
                                                                etSandVoyage.setText(threadShip.getShipItemNum());
                                                                if (sandShipPupWindow != null) {
                                                                    sandShipPupWindow.dismiss();
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                };

                                recycle_view.setAdapter(commonAdapter);
                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                sandShipPupWindow.showAsDropDown(view);
            }
        });

        /** 已清仓 */
        cbAlreadyClear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isClear = b ? 1 : 0;
            }
        });

        /** 施工分区 */
        // 跳转
        tvConstructionDevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                    PartitionActivity.startActivity(getContext(), 0, SettingUtil.TYPE_DATA_NEW);
                } else {
                    PartitionActivity.startActivity(getContext(), activity.itemID, SettingUtil.TYPE_DATA_UPDATE);
                }
            }
        });

        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClear == 1) {
                    activity.showDailog("提交抛砂记录", "是否提交已清仓?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            commitThread();
                        }
                    });
                } else {
                    commitThread();
                }
            }
        });
    }

    /**
     * 提交抛砂
     */
    private void commitThread() {
        if (boat == null) {
            if (activity.type == SettingUtil.TYPE_DATA_NEW) {
                List<ConstructionBoat> all = findAll(ConstructionBoat.class);
                int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
                boat = all.get(position - 1);
            } else {
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
        List<User> all = findAll(User.class);
        // 施工分区
        List<PartitionNum> numList = DataSupport.where("num is not null and num != ?", "").find(PartitionNum.class);
        // 长度不一致的分区
        List<PartitionNum> list = DataSupport.where("tag = ? and num is not null and num != ? and userAccount = ?", "0", "", boat.getShipNum()).find(PartitionNum.class);
        // panel命名重复的分区
        List<Map> maps = isRepeat();
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
        quantity = TextUtils.isEmpty(quantity) ? "0" : quantity;
        // 备注
        String remark = etRemark.getText().toString();
        remark = remark.equals("添加备注") ? "" : remark;


        if (!endTime.equals(HINT) &&
                !TextUtils.isEmpty(endTime) &&
                //!stratification.equals(HINT) &&
                //!TextUtils.isEmpty(stratification) &&
                !sandVoyage.equals(HINT) &&
                !TextUtils.isEmpty(sandVoyage) &&
                !numList.isEmpty() &&
                !TextUtils.isEmpty(quantity) &&
                !TextUtils.isEmpty(SandHandlingShipID)) {

            /** 当panel数量大于1条, 且有panel长度不一致, 提示修改 */
            if (!list.isEmpty() && numList.size() > 1) {
                ToastUtil.tip(getContext(), "有" + list.size() + "个施工panel长度不一致, 请修改后提交");
                return;
            }

            /** 判断是否有panel重命名 */
            if (!maps.isEmpty()) {
                ToastUtil.tip(getContext(), "有" + maps.size() + "个施工panel命名重复, 请修改后提交");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ItemID", activity.itemID);
                jsonObject.put("ShipAccount", boat.getShipNum());
                jsonObject.put("Layered", "");
                jsonObject.put("ShipItemNum", sandVoyage);
                jsonObject.put("StartTime", startTime);
                jsonObject.put("EndTime", endTime);
                jsonObject.put("Quantity", quantity);
                jsonObject.put("Creator", all.get(0).getUserID());
                jsonObject.put("Remark", remark);
                jsonObject.put("SandHandlingShipID", SandHandlingShipID);
                jsonObject.put("IsClearance", isClear);
                jsonObject.put("PumpShipID", pumpShipID);

                JSONArray jsonArray = new JSONArray();
                for (PartitionNum bean : numList) {
                    JSONObject object = new JSONObject();
                    object.put("LayerID", bean.getLayoutID());
                    object.put("ItemID", 0);
                    object.put("PartitionName", bean.getNum());
                    jsonArray.put(object);
                }
                jsonObject.put("ThrowingSandPartitionList", jsonArray);

                String json = jsonObject.toString();

                LogUtil.d("抛砂提交: " + json);

                boolean isUpdate = activity.type == SettingUtil.TYPE_DATA_UPDATE;
                /** 判断供砂船舶是否是BCF */
                if (sandShipName.contains("BCF")) {
                    // 提交到BCF
                    presenter.commitBCF(json, boat.getShipNum(), startTime, endTime, isUpdate);
                } else {
                    presenter.commit(json, boat.getShipNum(), startTime, endTime, isUpdate);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getContext(), "还有数据未填写", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取重复命名数据
     *
     * @return
     */
    @NonNull
    private List<Map> isRepeat() {
        ContentValues v = new ContentValues();
        v.put("nameTag", 0);
        int ii = DataSupport.updateAll(PartitionNum.class, v);

        LogUtil.d("还原panel状态数量: " + ii);

        // 验证是否有重命名的panel
        Cursor cursor = DataSupport.findBySQL("select * from PartitionNum where userAccount = " + "\"" + boat.getShipNum() + "\"" + " and num in (select num from PartitionNum group by num having count(num) > 1)");

        List<Map> maps = DBUtil.queryListMap(cursor);

        // 更新命名重合的tag
        StringBuffer stringBuffer = new StringBuffer();
        ContentValues values = new ContentValues();
        values.put("nameTag", 1);
        for (Map map : maps) {
            String num = (String) map.get("num");
            int i = DataSupport.updateAll(PartitionNum.class, values, "num = ? and userAccount = ?", num, boat.getShipNum());
            stringBuffer.append("命名重合panel: " + num + ", 条数: " + i + "\n");
        }
        LogUtil.d(stringBuffer.toString());
        return maps;
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
            startTime = bean.getStartTime();

            // 保存结束时间
            if (!TextUtils.isEmpty(bean.getEndTime())) {
                endTime = bean.getEndTime();
            }
        }

        // 详细信息
        List<User> users = findAll(User.class);
        // 记录人
        tvRecord.setText(users.get(0).getUserName());
        // 施工船舶
        tvShipName.setText(bean.getShipName());
        // 船舶类型
        tvShipType.setText(bean.getShipType());
        // 潮水
        tvTideWather.setText(bean.getCurrentTide());
        // 船次
        //etSandVoyage.setText(bean.getShipItemNum());
    }

    @Override
    public void showPartition(PartitionSBBean bean) {
        flag = true;
        tvConstructionDevision.setText(TextUtils.isEmpty(bean.getPartition()) ? "" : bean.getPartition());
        tvDevisionNum.setText(String.valueOf(bean.getSize()));
    }

    /**
     * 提交成功
     *
     * @param isSuccess
     */
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

    /**
     * 回显详细数据
     *
     * @param bean
     */
    @Override
    public void showDetailInfo(ThreadDetailInfo bean) {
        // TODO: 回显供砂船名
        SandHandlingShipID = bean.getSandHandlingShipID();
        sandShipName = bean.getSandHandlingShipName();
        tvSandShip.setText(bean.getSandHandlingShipName());

        // 回显泵砂船
        pumpShipID = TextUtils.isEmpty(bean.getPumpShipID()) ? "" : bean.getPumpShipID();
        pumpShipName = TextUtils.isEmpty(bean.getPumpShipName()) ? "" : bean.getPumpShipName();

        // 回显是否清仓
        int isClearance = bean.getIsClearance();
        cbAlreadyClear.setChecked(isClearance == 1);
        isClear = isClearance;

        // 回显供砂航次
        etSandVoyage.setText(bean.getShipItemNum());


        // TODO:保存施工分区到数据库
        DataSupport.deleteAll(PartitionNum.class);
        List<ThreadDetailInfo.PartitionRecordListBean> list = bean.getPartitionRecordList();

        // 如果没有施工分区, 不保存
        if (list != null && !list.isEmpty()) {
            tvDevisionNum.setText(String.valueOf(list.size()));
            StringBuffer stringBuffer = new StringBuffer();

            // 记录第一条长度
            int length = list.get(0).getPartitionName().length();
            for (ThreadDetailInfo.PartitionRecordListBean listBean : list) {
                PartitionNum partitionNum = new PartitionNum();
                partitionNum.setUserAccount(boat.getShipNum());
                partitionNum.setNum(listBean.getPartitionName());
                partitionNum.setLayoutID(TextUtils.isEmpty(listBean.getLayerID()) ? 0 : Integer.valueOf(listBean.getLayerID()));
                partitionNum.setLayoutName(listBean.getLayerName());
                if (length == listBean.getPartitionName().length()) {
                    partitionNum.setTag(1);
                } else {
                    partitionNum.setTag(0);
                }
                partitionNum.save();

                stringBuffer.append(listBean.getPartitionName() + ",");
            }

            // 回显施工分区
            //String partitionNameArr = bean.getPartitionNameArr();
            String s = stringBuffer.toString();
            String substring = s.substring(0, s.length() - 1);
            tvConstructionDevision.setText(substring);
        }


        // 回显工程量
        etEngineeringQuantity.setText(bean.getQuantity());
        // 回显备注
        etRemark.setText(bean.getRemark());


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
