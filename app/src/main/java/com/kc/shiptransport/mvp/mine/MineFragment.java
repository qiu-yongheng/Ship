package com.kc.shiptransport.mvp.mine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.source.DataRepository;
import com.kc.shiptransport.db.user.Department;
import com.kc.shiptransport.db.userinfo.UserInfo;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.mvp.about.AboutActivity;
import com.kc.shiptransport.mvp.changepassword.ChangePasswordActivity;
import com.kc.shiptransport.mvp.login.LoginActivity;
import com.kc.shiptransport.mvp.main.MainActivity;
import com.kc.shiptransport.util.SelectUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.kc.shiptransport.view.actiivty.InputActivity;
import com.umeng.analytics.MobclickAgent;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:37
 * @desc ${TODO}
 */

public class MineFragment extends Fragment {


    @BindView(R.id.btn_mine_exit)
    Button btnMineExit;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
    @BindView(R.id.tv_change_password)
    TextView tvChangePassword;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.tv_section)
    TextView tvSection;
    @BindView(R.id.rl_section)
    RelativeLayout rlSection;
    @BindView(R.id.tv_duties)
    TextView tvDuties;
    @BindView(R.id.rl_duties)
    RelativeLayout rlDuties;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.tv_tell)
    TextView tvTell;
    @BindView(R.id.rl_tell)
    RelativeLayout rlTell;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    private SharedPreferences sp;
    private MainActivity activity;
    private UserInfo userInfo;
    private DataRepository dataRepository;
    private CommonPopupWindow popupWindow;
    private MinePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        initListener();

        new MinePresenter(getContext(), this, new DataRepository());

        return view;
    }

    public void setPresenter(MinePresenter presenter) {
        this.presenter = presenter;
    }

    private void initView(View view) {
        sp = PreferenceManager.getDefaultSharedPreferences(getContext());

        activity = (MainActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(R.string.title_mine);

        // 添加下划线
        tvChangePassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

        // 显示用户信息
        userInfo = DataSupport.findAll(UserInfo.class).get(0);
        syncDates(userInfo);
    }

    /**
     * 更新数据
     *
     * @param userInfo
     */
    private void syncDates(UserInfo userInfo) {
        // 用户名
        tvUserName.setText(userInfo.getDisplayName());
        // 用户账号
        tvUserAccount.setText(userInfo.getLoginName());
        // 性别
        tvSex.setText(TextUtils.isEmpty(userInfo.getSex()) ? "" : userInfo.getSex());
        // 部门
        tvSection.setText(TextUtils.isEmpty(userInfo.getDepartment()) ? "" : userInfo.getDepartment());
        // 职务
        tvDuties.setText(TextUtils.isEmpty(userInfo.getTitle()) ? "" : userInfo.getTitle());
        // 手机
        tvPhone.setText(TextUtils.isEmpty(userInfo.getMobile()) ? "" : userInfo.getMobile());
        // email
        tvEmail.setText(TextUtils.isEmpty(userInfo.getEmail()) ? "" : userInfo.getEmail());
        // 电话
        tvTell.setText(TextUtils.isEmpty(userInfo.getTelephoneNumber()) ? "" : userInfo.getTelephoneNumber());
    }

    public void initListener() {
        /** 退出登录 */
        btnMineExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDailog("退出登录", "真的需要退出登录吗?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 记录登出
                        MobclickAgent.onProfileSignOff();

                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString(SettingUtil.DATA_USERNAME, "");
                        edit.putString(SettingUtil.DATA_PASSWORD, "");
                        edit.putBoolean(SettingUtil.KEY_REMEMBER_PASSWORD, false);
                        edit.putBoolean(SettingUtil.KEY_AUTHOR_LOGIN, false);
                        edit.apply();

                        LoginActivity.navigateToLoginActivity(getContext());
                        getActivity().finish();
                    }
                });
            }
        });

        /** 修改密码 */
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePasswordActivity.startActivity(getContext());
            }
        });

        /** 性别 */
        rlSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }

                // 性别
                // 初始化控件
                // 男
                // 女
                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_sex)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        .setBackGroundLevel(0.8f)
                        .setAnimationStyle(R.style.AnimBottem)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                TextView man = (TextView) view.findViewById(R.id.tv_man);
                                TextView woman = (TextView) view.findViewById(R.id.tv_woman);

                                // 男
                                man.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userInfo.setSex(getResources().getString(R.string.text_man));
                                        if (popupWindow != null && popupWindow.isShowing()) {
                                            popupWindow.dismiss();
                                        }

                                        presenter.changeInfo(userInfo);
                                    }
                                });

                                // 女
                                woman.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userInfo.setSex(getResources().getString(R.string.text_woman));
                                        if (popupWindow != null && popupWindow.isShowing()) {
                                            popupWindow.dismiss();
                                        }

                                        presenter.changeInfo(userInfo);
                                    }
                                });

                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                popupWindow.showAtLocation(activity.findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
            }
        });

        /** 部门 */
        rlSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                InputActivity.startActivityForResult(getActivity(),
//                        "部门",
//                        userInfo.getDepartment(),
//                        SettingUtil.TYPE_TEXT,
//                        SettingUtil.ID_DEPARTMENT);

                final List<Department> list = DataSupport.order("SortNum asc").find(Department.class);
                SelectUtil<Department> selectUtil = new SelectUtil<>();
                selectUtil.showSelectDialog(getContext(), list, false, "", new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String pickerViewText = list.get(options1).getPickerViewText();

                        // 修改界面显示
                        tvSection.setText(pickerViewText);
                        userInfo.setDepartment(pickerViewText);
                        userInfo.setDepartmentID(list.get(options1).getItemID());

                        presenter.changeInfo(userInfo);
                    }
                });
            }
        });

        /** 职务 */
        rlDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputActivity.startActivityForResult(getActivity(),
                        "职务",
                        userInfo.getTitle(),
                        SettingUtil.TYPE_TEXT,
                        SettingUtil.ID_DUTIES);
            }
        });

        /** 手机 */
        rlPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputActivity.startActivityForResult(getActivity(),
                        "手机",
                        userInfo.getMobile(),
                        SettingUtil.TYPE_TEXT,
                        SettingUtil.ID_PHONE);
            }
        });

        /** email */
        rlEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputActivity.startActivityForResult(getActivity(),
                        "Email",
                        userInfo.getEmail(),
                        SettingUtil.TYPE_TEXT,
                        SettingUtil.ID_EMAIL);
            }
        });

        /** 电话 */
        rlTell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputActivity.startActivityForResult(getActivity(),
                        "电话",
                        userInfo.getTelephoneNumber(),
                        SettingUtil.TYPE_TEXT,
                        SettingUtil.ID_TELL);
            }
        });

        /** 软件版本 */
        rlAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.startActivity(getContext());
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** 把数据保存到数据库后, 发送网络请求修改数据 */
        String str = data.getStringExtra(InputActivity.TAG);

        switch (requestCode) {
            case SettingUtil.ID_DEPARTMENT:
                // 部门
                userInfo.setDepartment(str);
                break;
            case SettingUtil.ID_DUTIES:
                // 职务
                userInfo.setTitle(str);
                break;
            case SettingUtil.ID_PHONE:
                // 手机
                userInfo.setMobile(str);
                break;
            case SettingUtil.ID_EMAIL:
                // email
                userInfo.setEmail(str);
                break;
            case SettingUtil.ID_TELL:
                // 电话
                userInfo.setTelephoneNumber(str);
                break;
        }

        presenter.changeInfo(userInfo);
    }



    /**
     * 修改结果
     *
     * @param isSuccess
     */
    public void showResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();

            // 更新数据
            syncDates(userInfo);
        } else {
            Toast.makeText(getContext(), "修改失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    public void showLoadding(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("修改中", "修改中", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {

                }
            });
        } else {
            activity.hideProgressDailog();
        }
    }
}
