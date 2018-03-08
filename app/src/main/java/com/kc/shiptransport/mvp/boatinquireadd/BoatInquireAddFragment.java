package com.kc.shiptransport.mvp.boatinquireadd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireCommitBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireDetailBean;
import com.kc.shiptransport.data.bean.boatinquire.BoatInquireItemBean;
import com.kc.shiptransport.data.bean.boatinquire.ItemBean;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/29  14:51
 * @desc ${TODD}
 */

public class BoatInquireAddFragment extends BaseFragment<BoatInquireAddActivity> implements BoatInquireAddContract.View, View.OnClickListener {
    @BindView(R.id.tv_checker)
    TextView tvChecker;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_spinner)
    TextView tvSpinner;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    private BoatInquireAddContract.Presenter presenter;
    private List<HseCheckShip> shipList;
    private String shipAccount;
    private String inquireTime;
    private CommonAdapter<ItemBean> adapter;
    private List<BoatInquireDetailBean.ListBean> list;
    private List<ItemBean> itemBeans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initData();
        initViews(rootView);
        initListener();

        return rootView;
    }

    private void initData() {
        shipList = DataSupport.findAll(HseCheckShip.class);
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 检查人
        tvChecker.setText(creatorName);
        // 检查时间
        inquireTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS);
        tvTime.setText(inquireTime);
        // 受检船舶(当前账号就是受检船舶账号)
        tvSpinner.setText(creatorName);
        shipAccount = creator;

        switch (activity.type) {
            case SettingUtil.TYPE_BOAT_INQUIRE_ADD:
                tvTime.setOnClickListener(this);
                //tvSpinner.setOnClickListener(this); // 改需求, 不需要选择

                presenter.getInquireItem();
                break;
            case SettingUtil.TYPE_BOAT_INQUIRE_UPDATE:
                tvTime.setOnClickListener(this);
                //tvSpinner.setOnClickListener(this); // 改需求, 不需要选择

                presenter.getDetailData(activity.itemID);
                break;
            case SettingUtil.TYPE_BOAT_INQUIRE_READ_ONLY:
                presenter.getDetailData(activity.itemID);
                btnCommit.setVisibility(View.GONE);
                btnReturn.setVisibility(View.VISIBLE);

                etRemark.setFocusable(false);
                etRemark.setFocusableInTouchMode(false);

                tvTime.setBackground(null);
                tvSpinner.setBackground(null);

                break;
        }
    }

    @Override
    public void initListener() {
        tvTime.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
    }

    @Override
    public void setPresenter(BoatInquireAddContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("请稍等", "请稍等...", new OnDailogCancleClickListener() {
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
    public int setView() {
        return R.layout.fragment_boat_inquire_add;
    }

    @Override
    public int setTitle() {
        return R.string.title_boat_inquire;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time: // 时间
                try {
                    CalendarUtil.showTimePickerDialog(getContext(), tvTime, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            inquireTime = str;
                        }
                    }, new OnTimePickerLastDateClickListener() {
                        @Override
                        public void onLastDate() {

                        }
                    }, false, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_spinner: // 受检船舶
                PopwindowUtil.showPopwindow(getContext(), shipList, tvSpinner, true, new PopwindowUtil.InitHolder<HseCheckShip>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseCheckShip hseCheckShip, final int position) {
                        holder.setText(R.id.tv_spinner, hseCheckShip.getShipName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvSpinner.setText(hseCheckShip.getShipName());
                                        shipAccount = hseCheckShip.getShipAccount();

                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.btn_commit: // 提交
                commit();
                break;
            case R.id.btn_return:
                activity.onBackPressed();
                break;
        }
    }

    /**
     * 提交数据
     */
    private void commit() {
        String remark = etRemark.getText().toString().trim();
        if (TextUtils.isEmpty(creator)) {
            ToastUtil.tip(getContext(), "请登录");
        } else if (TextUtils.isEmpty(inquireTime)) {
            ToastUtil.tip(getContext(), "请选择检查时间");
        } else if (TextUtils.isEmpty(shipAccount)) {
            ToastUtil.tip(getContext(), "请选择受检船舶");
        } else if (itemBeans == null || itemBeans.isEmpty()) {
            ToastUtil.tip(getContext(), "检查项获取失败, 请重新打开界面");
        } else {
            for (ItemBean bean : itemBeans) {
                if (bean.getCheckedResult() == 0) {
                    ToastUtil.tip(getContext(), "有检查项未审核, 请审核后提交");
                    return;
                }
            }
            presenter.commit(new BoatInquireCommitBean(activity.itemID, shipAccount, inquireTime, creator, remark, itemBeans));
        }
    }

    /**
     *
     * @param bean
     */
    @Override
    public void showDetailData(BoatInquireDetailBean bean) {
        // 检查人
        creatorName = TextUtils.isEmpty(bean.getCreatorName()) ? creatorName : bean.getCreatorName();
        tvChecker.setText(creatorName);
        creator = TextUtils.isEmpty(bean.getCreator()) ? creator : bean.getCreator();
        // 检查日期
        inquireTime = TextUtils.isEmpty(bean.getCheckDate()) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS) : bean.getCheckDate();
        tvTime.setText(inquireTime);
        // 受检船舶
        tvSpinner.setText(TextUtils.isEmpty(bean.getShipName()) ? "请选择" : bean.getShipName());
        shipAccount = bean.getShipAccount();
        // 备注
        etRemark.setText(bean.getRemark());

        list = bean.getList();

        if (list == null) {
            list = new ArrayList<>();
        }

        itemBeans = ToUtil.toItemBeanList2(list);
        setAdapter();
    }

    @Override
    public void showInquireItem(List<BoatInquireItemBean> list) {
        itemBeans = ToUtil.toItemBeanList1(list);
        setAdapter();
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "提交成功");
            activity.onBackPressed();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }

    private void setAdapter() {
        adapter = new CommonAdapter<ItemBean>(getContext(), R.layout.item_boat_inquire_list, itemBeans) {
            @Override
            protected void convert(ViewHolder holder, final ItemBean listBean, int position) {
                holder.setText(R.id.tv_description, String.valueOf(position + 1) + ". " + listBean.getSelfCheckItemName())
                        .setVisible(R.id.radioGroup, activity.type != SettingUtil.TYPE_BOAT_INQUIRE_READ_ONLY)
                        .setVisible(R.id.tv_status, activity.type == SettingUtil.TYPE_BOAT_INQUIRE_READ_ONLY)
                        .setText(R.id.tv_status, listBean.getCheckedResult() == 1 ? "通过" : "不通过");
                RadioGroup radioGroup = holder.getView(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(null); //重点，取消监听
                switch (listBean.getCheckedResult()) {
                    case 1: // 合格
                        radioGroup.check(R.id.radio_pass);
                        break;
                    case -1: // 不合格
                        radioGroup.check(R.id.radio_no_pass);
                        break;
                    default: // 未选
                        radioGroup.clearCheck();
                        break;
                }
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        switch (i) {
                            case R.id.radio_pass:
                                // 通过
                                LogUtil.d("通过");
                                listBean.setCheckedResult(1);
                                break;
                            case R.id.radio_no_pass:
                                // 不通过
                                LogUtil.d("不通过");
                                listBean.setCheckedResult(-1);
                                break;
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}
