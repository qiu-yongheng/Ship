package com.kc.shiptransport.mvp.exitapplicationdetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/12  10:01
 * @desc ${TODD}
 */

public class ExitApplicationDetailFragment extends Fragment implements ExitApplicationDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_captain)
    TextView tvCaptain;
    @BindView(R.id.tv_captain_phone)
    TextView tvCaptainPhone;
    @BindView(R.id.tv_ship_num)
    TextView tvShipNum;
    @BindView(R.id.tv_arrive_anchor_time)
    TextView tvArriveAnchorTime;
    @BindView(R.id.tv_stowage)
    TextView tvStowage;
    @BindView(R.id.tv_material)
    TextView tvMaterial;
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.tv_supply_time)
    TextView tvSupplyTime;
    @BindView(R.id.et_quantum)
    EditText etQuantum;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    private ExitApplicationDetailContract.Presenter presenter;
    private ExitApplicationDetailActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_application, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO
        return view;
    }

    @Override
    public void initViews(View view) {
        activity = (ExitApplicationDetailActivity)getActivity();
    }

    @Override
    public void initListener() {
        /** 取消 */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void setPresenter(ExitApplicationDetailContract.Presenter presenter) {
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
    public void showCommitResult(boolean isSuccess) {

    }
}
