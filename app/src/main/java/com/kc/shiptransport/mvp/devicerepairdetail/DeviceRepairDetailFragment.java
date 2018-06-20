package com.kc.shiptransport.mvp.devicerepairdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.device.repair.DeviceRepairDetailBean;
import com.kc.shiptransport.data.bean.device.repair.DeviceShipBean;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.BaseFragment;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.custom.DataInputLayout;
import com.kc.shiptransport.view.custom.ImageSelectLayout;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2018/6/17  12:35
 * @desc
 */

public class DeviceRepairDetailFragment extends BaseFragment<DeviceRepairDetailActivity> {
    @BindView(R.id.dl_creator)
    DataInputLayout mDlCreator;
    @BindView(R.id.dl_ship_account)
    DataInputLayout mDlShipAccount;
    @BindView(R.id.dl_repair_project)
    DataInputLayout mDlRepairProject;
    @BindView(R.id.dl_repair_progress)
    DataInputLayout mDlRepairProgress;
    @BindView(R.id.dl_repair_man)
    DataInputLayout mDlRepairMan;
    @BindView(R.id.dl_repair_start_time)
    DataInputLayout mDlRepairStartTime;
    @BindView(R.id.dl_repair_end_time)
    DataInputLayout mDlRepairEndTime;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.img_select)
    ImageSelectLayout mImgSelect;
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.btn_return)
    Button mBtnReturn;
    Unbinder unbinder;
    private DeviceRepairDetailBean bean;
    private User user;
    private String shipAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        bean = activity.repair_bean;

        switch (activity.type) {
            case SettingUtil.TYPE_DEVICE_REPAIR_ADD:
                user = DataSupport.findFirst(User.class);
                mDlCreator.setValue(user.getUserName());
                break;
            case SettingUtil.TYPE_DEVICE_REPAIR_UPDATE:
                bindView();
                break;
            case SettingUtil.TYPE_DEVICE_REPAIR_READ_ONLY:
                mImgSelect.isCanChange(false);
                bindView();
                changeInputState();
                break;
        }
    }

    private void initListener() {
        /** 船舶账号 */
        mDlShipAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DeviceShipBean> shipBeans = DataSupport.findAll(DeviceShipBean.class);
                PopwindowUtil.showPopwindow(getContext(), shipBeans, mDlShipAccount.getInputView(), true, new PopwindowUtil.InitHolder<DeviceShipBean>() {
                    @Override
                    public void initHolder(ViewHolder holder, final DeviceShipBean deviceShipBean, int position) {
                        holder.setText(R.id.tv_spinner, deviceShipBean.getShipName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mDlShipAccount.setValue(deviceShipBean.getShipName());
                                        shipAccount = deviceShipBean.getShipAccount();

                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
            }
        });

        /** 开始时间 */
        mDlRepairStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CalendarUtil.showTimePickerDialog(getContext(), (TextView) mDlRepairStartTime.getInputView(), new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            try {
                                if (CalendarUtil.isLastDate(mDlRepairStartTime.getSelectText(), mDlRepairEndTime.getSelectText())) {
                                    ToastUtil.tip(getContext(), "开始时间不能大于结束时间");
                                    mDlRepairEndTime.setValue("");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null, false, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /** 结束时间 */
        mDlRepairEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CalendarUtil.showTimePickerDialog(getContext(), (TextView) mDlRepairEndTime.getInputView(), new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            try {
                                if (CalendarUtil.isLastDate(mDlRepairStartTime.getSelectText(), mDlRepairEndTime.getSelectText())) {
                                    ToastUtil.tip(getContext(), "结束时间不能小于开始时间");
                                    mDlRepairEndTime.setValue("");
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null, false, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /** 提交 */
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 当前账号
                // 船舶账号
                // 修理项目
                // 修理过程
                // 修理人员
                // 开始时间
                // 结束时间
                // 备注
                // 新增图片
                // 删除图片
                // TODO: 提交
            }
        });
    }

    private void bindView() {
        mDlCreator.setValue(bean.getCreator());
        mDlShipAccount.setValue(bean.getShipName());
        mDlRepairProject.setValue(bean.getRepairProject());
        mDlRepairProgress.setValue(bean.getRepairProcess());
        mDlRepairMan.setValue(bean.getRepairman());
        mDlRepairStartTime.setValue(bean.getStartTime());
        mDlRepairEndTime.setValue(bean.getEndTime());
        mEtRemark.setText(bean.getRemark());

        mImgSelect.setImgList(RxGalleryUtil.repairToImgList(bean.getAttachment()));
    }

    private void changeInputState() {
        mDlShipAccount.setReadOnly();
        mDlRepairProject.setReadOnly();
        mDlRepairProgress.setReadOnly();
        mDlRepairMan.setReadOnly();
        mDlRepairStartTime.setReadOnly();
        mDlRepairEndTime.setReadOnly();

        mEtRemark.setBackground(null);
        mEtRemark.setFocusable(false);
        mEtRemark.setFocusableInTouchMode(false);
    }

    @Override
    public int setView() {
        return R.layout.fragment_device_repair_detail;
    }

    @Override
    public int setTitle() {
        return R.string.title_device_repair;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
