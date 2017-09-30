package com.kc.shiptransport.mvp.bcf;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.bcf.BCFLog;
import com.kc.shiptransport.db.bcf.BCFShip;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.analysis.AnalysisActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kc.shiptransport.R.id.tv;

/**
 * @author 邱永恒
 * @time 2017/9/25  9:32
 * @desc ${TODD}
 */

public class BCFFragment extends Fragment implements BCFContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_sand_ship)
    TextView tvSandShip;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_quantum)
    EditText tvQuantum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.btn_return)
    Button btnReturn;
    private BCFActivity activity;
    private BCFContract.Presenter presenter;
    private int sandWidth;
    private int sandHeight;
    private CommonPopupWindow sandShipPupWindow;
    private String SandHandlingShipID;
    private String subAccount;
    private int subWidth;
    private int subHeight;
    private CommonPopupWindow subPupWindow;
    private String time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bcf, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        presenter.getSandShip();
        presenter.getSubList();

        if (activity.itemID != 0) {
            // 回显数据
            presenter.getUpdateData();
        }
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (BCFActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        tvTitle.setText("BCF供砂来船");
        tvOpinion.setVisibility(View.VISIBLE);
        tvOpinion.setText("查看BCF记录");


        // 回显
        subAccount = SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_SUB_ACCOUNT, "");
        tvSub.setText(SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_SUB_NAME, "请选择"));
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
        /** 返回 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        /** 供砂船舶 */
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

                                // 获取数据
                                List<BCFShip> list = DataSupport.order("rownumber asc").find(BCFShip.class);

                                CommonAdapter<BCFShip> commonAdapter = new CommonAdapter<BCFShip>(getContext(), R.layout.item_thread_sand, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, final BCFShip bcfShip, int position) {
                                        holder.setText(tv, bcfShip.getShipName())
                                                .setOnClickListener(tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        // 供砂船舶账号
                                                        SandHandlingShipID = bcfShip.getShipAccount();
                                                        tvSandShip.setText(bcfShip.getShipName());
                                                        if (sandShipPupWindow != null) {
                                                            sandShipPupWindow.dismiss();
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

        /** 供应商 */
        tvSub.post(new Runnable() {
            @Override
            public void run() {
                subWidth = tvSub.getMeasuredWidth();
                subHeight = tvSub.getMeasuredHeight();
            }
        });
        tvSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subPupWindow != null && subPupWindow.isShowing()) {
                    return;
                }

                subPupWindow = new CommonPopupWindow.Builder(getContext())
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

                                // 获取数据
                                List<SubcontractorList> list = DataSupport.findAll(SubcontractorList.class);

                                CommonAdapter<SubcontractorList> commonAdapter = new CommonAdapter<SubcontractorList>(getContext(), R.layout.item_thread_sand, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, final SubcontractorList sub, int position) {
                                        holder.setText(tv, sub.getSubcontractorName())
                                                .setOnClickListener(tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        // 分包商账号
                                                        subAccount = sub.getSubcontractorAccount();
                                                        tvSub.setText(sub.getSubcontractorName());

                                                        // 记忆选择的分包商
                                                        SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_SUB_ACCOUNT, subAccount);
                                                        SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_SUB_NAME, sub.getSubcontractorName());
                                                        if (subPupWindow != null) {
                                                            subPupWindow.dismiss();
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
                subPupWindow.showAsDropDown(view);
            }
        });

        /** 时间 */
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String offsetDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DAY_OF_YEAR, 1);
                    CalendarUtil.showDateDialog(getContext(), tvTime, CalendarUtil.YYYY_MM_DD, offsetDate, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            time = str;
                            // 判断是否允许提交
                            presenter.isAllowCommit(time);
                        }
                    }, new OnTimePickerLastDateClickListener() {
                        @Override
                        public void onLastDate() {

                        }
                    }, false, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(SandHandlingShipID)) {
                    ToastUtil.tip(getContext(), "请选择来砂船舶");
                } else if (TextUtils.isEmpty(subAccount)) {
                    ToastUtil.tip(getContext(), "请选择供应商");
                } else if (TextUtils.isEmpty(time)) {
                    ToastUtil.tip(getContext(), "请选择时间");
                } else {
                    // 工程量
                    int quan = TextUtils.isEmpty(tvQuantum.getText().toString()) ? 0 : Integer.valueOf(tvQuantum.getText().toString());
                    // 备注
                    String remark = TextUtils.isEmpty(etRemark.getText().toString()) ? "" : etRemark.getText().toString();
                    // creator
                    String userID = DataSupport.findAll(User.class).get(0).getUserID();

                    presenter.commit(activity.itemID, SandHandlingShipID, subAccount, quan, remark, userID, time);
                }
            }
        });

        /** 查看BCF记录 */
        tvOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_BCF_LOG);
            }
        });
    }

    @Override
    public void setPresenter(BCFContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("提交", "提交中", new OnDailogCancleClickListener() {
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
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showSandShipResult(boolean isSuccess) {

    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "提交成功");
            getActivity().onBackPressed();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }

    @Override
    public void showIsAllowCommit(boolean isAllow) {
        if (isAllow) {
            btnCommit.setVisibility(View.VISIBLE);
            btnReturn.setVisibility(View.GONE);
        } else {
            btnCommit.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);
            ToastUtil.tip(getContext(), "当前时间不可修改BCF供砂来船");
        }
    }

    /**
     * 回显数据
     *
     * @param list
     */
    @Override
    public void showUpdateData(List<BCFLog> list) {
        List<BCFLog> logList = DataSupport.where("ItemID = ?", String.valueOf(activity.itemID)).find(BCFLog.class);
        if (logList.isEmpty()) {
            ToastUtil.tip(getContext(), "加载数据失败, 请重试");
            return;
        }

        BCFLog bcfLog = logList.get(0);
        // 来砂船舶
        SandHandlingShipID = bcfLog.getSandHandlingShipID();
        String sandHandlingShipName = bcfLog.getSandHandlingShipName();
        // 供应商
        subAccount = bcfLog.getSubcontractorAccount();
        String subcontractorName = bcfLog.getSubcontractorName();
        // 时间
        time = bcfLog.getDate();
        // 工程量
        String totalAmount = bcfLog.getTotalAmount();
        // 备注
        String remark = bcfLog.getRemark();

        tvSandShip.setText(TextUtils.isEmpty(sandHandlingShipName) ? "请选择" : sandHandlingShipName);
        tvSub.setText(TextUtils.isEmpty(subcontractorName) ? "请选择" : subcontractorName);
        tvTime.setText(TextUtils.isEmpty(time) ? "请选择" : time);
        tvQuantum.setText(TextUtils.isEmpty(totalAmount) ? "0" : totalAmount);
        etRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);
    }
}
