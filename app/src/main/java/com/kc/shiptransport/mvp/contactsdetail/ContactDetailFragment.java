package com.kc.shiptransport.mvp.contactsdetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.util.PatternUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/8/16  10:55
 * @desc ${TODD}
 */

public class ContactDetailFragment extends Fragment implements ContactDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_account)
    TextView tvUserAccount;
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
    Unbinder unbinder;
    private ContactDetailActivity activity;
    private Contacts contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ContactDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle("返回");


        this.contacts = activity.contacts;
        // 用户名
        tvUserName.setText(contacts.getDepartment());
        // 英文名
        tvUserAccount.setText(contacts.getEnglishName());
        // 性别
        tvSex.setText(TextUtils.isEmpty(contacts.getSex()) ? "" : contacts.getSex());
        // 部门
        tvSection.setText(TextUtils.isEmpty(contacts.getDuties()) ? "" : contacts.getDuties());
        // 职务
        tvDuties.setText(TextUtils.isEmpty(contacts.getDuties()) ? "" : contacts.getDuties());
        // 手机
        tvPhone.setText(TextUtils.isEmpty(contacts.getMobile()) ? "" : contacts.getMobile());
        // email
        tvEmail.setText(TextUtils.isEmpty(contacts.getEmail()) ? "" : contacts.getEmail());
        // 电话
        tvTell.setText(TextUtils.isEmpty(contacts.getTelephoneNumber()) ? "" : contacts.getTelephoneNumber());
    }

    @Override
    public void initListener() {
        /** 拨打香港号码 */
        rlPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = tvPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "此联系人还未添加手机号码", Toast.LENGTH_SHORT).show();
                } else if (PatternUtil.patternNumber(phone)) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(phoneIntent);
                } else {
                    Toast.makeText(getContext(), "此号码不是手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /** 拨打国内号码 */
        rlTell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tell = tvTell.getText().toString().trim();
                if (TextUtils.isEmpty(tell)) {
                    Toast.makeText(getContext(), "此联系人还未添加电话号码", Toast.LENGTH_SHORT).show();
                } else if (PatternUtil.patternNumber(tell)) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tell));
                    startActivity(phoneIntent);
                } else {
                    Toast.makeText(getContext(), "此号码不是电话号码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /** 发送邮件 */
        rlEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tvEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "此联系人还未添加邮箱", Toast.LENGTH_SHORT).show();
                } else if (PatternUtil.patternEmail(email)) {
                    try {
                        Uri uri = Uri.parse("mailto:" + email);
                        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(), "手机没有发送邮件的APP, 请到应用市场下载", Toast.LENGTH_SHORT).show();
                        Log.e("==", ex.toString());
                    }
                } else {
                    Toast.makeText(getContext(), "此邮箱格式不对", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
    public void setPresenter(ContactDetailContract.Presenter presenter) {

    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
