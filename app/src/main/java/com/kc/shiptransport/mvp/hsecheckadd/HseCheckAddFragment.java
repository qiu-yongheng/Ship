package com.kc.shiptransport.mvp.hsecheckadd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseCheckAddBean;
import com.kc.shiptransport.data.bean.hse.HseCheckListBean;
import com.kc.shiptransport.db.hse.HseCheckShip;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.ToastUtil;
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
 * @time 2017/11/22  14:31
 * @desc ${TODD}
 */

public class HseCheckAddFragment extends BaseFragment<HseCheckAddActivity> implements HseCheckAddContract.View, View.OnClickListener {
    @BindView(R.id.tv_checker)
    TextView tvChecker;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    @BindView(R.id.tv_spinner)
    TextView tvSpinner;
    private HseCheckAddContract.Presenter presenter;
    private List<HseCheckShip> shipList;
    private String checkShip;
    private String checherAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        initViews(rootView);
        initListener();
        return rootView;
    }

    @Override
    public void initViews(View view) {
        // 当前用户信息
        List<User> users = DataSupport.findAll(User.class);
        String checker = users.isEmpty() ? "" : users.get(0).getUserName();
        checherAccount = users.isEmpty() ? "" : users.get(0).getUserID();
        tvChecker.setText(checker);

        // 当前时间
        tvTime.setText(CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM));

        // 船舶
        checkShip = SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_HSE_SHIP_ACCOUNT, "");

        // 回显船舶名称
        tvSpinner.setText(SharePreferenceUtil.getString(getContext(), SettingUtil.SP_KEY_HSE_SHIP_NAME, "请选择"));
        shipList = DataSupport.findAll(HseCheckShip.class);

        switch (activity.type) {
            case SettingUtil.TYPE_HSE_CHECK_ADD:
                break;
            case SettingUtil.TYPE_HSE_CHECK_UPDATE:
                presenter.getDetail(activity.itemID);
                break;
        }
    }

    @Override
    public void initListener() {
        tvTime.setOnClickListener(this);
        tvSpinner.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void setPresenter(HseCheckAddContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "请稍等...", new OnDailogCancleClickListener() {
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
        return R.layout.fragment_hsecheck_add;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_check_add;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_time:
                try {
                    CalendarUtil.showTimePickerDialog(getContext(), tvTime, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {

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
            case R.id.tv_spinner:
                PopwindowUtil.showPopwindow(getContext(), shipList, tvSpinner, true, new PopwindowUtil.InitHolder<HseCheckShip>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseCheckShip hseCheckShip, final int position) {
                        holder.setText(R.id.tv_spinner, hseCheckShip.getShipName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvSpinner.setText(hseCheckShip.getShipName());
                                        checkShip = hseCheckShip.getShipAccount();
                                        // 保存受检船舶
                                        SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_HSE_SHIP_ACCOUNT, checkShip);
                                        SharePreferenceUtil.saveString(getContext(), SettingUtil.SP_KEY_HSE_SHIP_NAME, hseCheckShip.getShipName());

                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.btn_commit:
                String checkTime = tvTime.getText().toString().trim();
                String remark = etRemark.getText().toString().trim();

                if (TextUtils.isEmpty(checherAccount) ) {
                   ToastUtil.tip(getContext(), "请登录");
                } else if (TextUtils.isEmpty(checkTime)) {
                    ToastUtil.tip(getContext(), "请选择检查日期");
                } else if (TextUtils.isEmpty(checkShip) || checkShip.equals("请选择")) {
                    ToastUtil.tip(getContext(), "请选择受检船舶");
                } else {
                    List<HseCheckAddBean> commitList = new ArrayList<>();
                    commitList.add(new HseCheckAddBean(String.valueOf(activity.itemID), checkTime, checkShip, checherAccount, remark));
                    presenter.commit(commitList);
                }
                break;
        }
    }

    @Override
    public void commitResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "提交成功");
            getActivity().onBackPressed();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }

    /**
     * 回显数据
     * @param list
     */
    @Override
    public void showDetail(HseCheckListBean list) {
        // 检查人
        checherAccount = list.getCreator();
        tvChecker.setText(list.getCreatorName());
        // 检查日期
        tvTime.setText(list.getCheckedTime());
        // 受检船舶
        tvSpinner.setText(list.getCheckedShipName());
        checkShip = list.getCheckedShipAccount();
        // remark
        etRemark.setText(list.getRemark());
    }
}
