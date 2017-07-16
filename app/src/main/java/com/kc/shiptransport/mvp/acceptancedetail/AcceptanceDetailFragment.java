package com.kc.shiptransport.mvp.acceptancedetail;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.scannerdetail.ScannerDetailActivity;
import com.kc.shiptransport.mvp.voyagedetail.VoyageDetailActivity;
import com.kc.shiptransport.util.CalendarUtil;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */
public class AcceptanceDetailFragment extends Fragment implements AcceptanceDetailContract.View {

    Unbinder unbinder;
    @BindView(R.id.toolbar_supply_detail)
    Toolbar toolbarSupplyDetail;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_ship_id)
    TextView tvShipId;
    @BindView(R.id.tv_subontractor)
    TextView tvSubontractor;
    @BindView(R.id.tv_total_voyage)
    TextView tvTotalVoyage;
    @BindView(R.id.tv_total_value)
    TextView tvTotalValue;
    @BindView(R.id.tv_avg_value)
    TextView tvAvgValue;
    @BindView(R.id.tv_ship_max_draft)
    TextView tvShipMaxDraft;
    @BindView(R.id.tv_ship_current_tide)
    TextView tvShipCurrentTide;
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.tv_acceptance_time)
    TextView tvAcceptanceTime;
    @BindView(R.id.btn_acceptance_cancel)
    AppCompatButton btnAcceptanceCancel;
    @BindView(R.id.btn_acceptance_commit)
    AppCompatButton btnAcceptanceCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.rb_complete)
    RatingBar rbComplete;
    @BindView(R.id.rb_timely)
    RatingBar rbTimely;
    @BindView(R.id.ll_commit_data)
    LinearLayout llCommitData;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    private AcceptanceDetailContract.Presenter presenter;
    private AcceptanceDetailActivity activity;
    private int rbcomplete = 0;
    private int rbtimely = 0;
    private String shipItemNum;
    private Acceptance value;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acceptance_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO 请求数据
        presenter.start(activity.itemID);
        return view;
    }

    public void initListener() {
        /* 点击弹出时间选择器 */
        tvAcceptanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /* 取消 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancle();
            }
        });

        /* 提交 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = tvAcceptanceTime.getText().toString().trim();
                if (time.equals("") || rbcomplete == 0 || rbtimely == 0) {
                    Toast.makeText(activity, "验收时间或评价不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.commit(activity.itemID, time, activity.acceptanceDetailActivity_evaluationID, rbcomplete, rbtimely, shipItemNum, value);
                }
            }
        });

        /* 点击弹出时间选择器 */
        tvAcceptanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CalendarUtil.showPickerDialog(getContext(), tvAcceptanceTime, CalendarUtil.YYYY_MM_DD_HH_MM, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {

                        }
                    }, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        /* 评价 */
        rbComplete.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rbcomplete = (int) v;
            }
        });

        /* 评价 */
        rbTimely.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rbtimely = (int) v;
            }
        });

        /* 基础信息查看 */
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到航次信息完善界面
                VoyageDetailActivity.startActivity(getActivity(), activity.itemID, 1);
            }
        });

        /* 扫描件查看 */
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到扫描件界面
                ScannerDetailActivity.startActivity(getActivity(), activity.itemID, 1);
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AcceptanceDetailActivity) getActivity();
        // 可以显示menu
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarSupplyDetail);
        activity.getSupportActionBar().setTitle("预验收管理");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (activity.isAcceptance) {
            // 已验收
            btnAcceptanceCommit.setVisibility(View.GONE);
            btnAcceptanceCancel.setText(R.string.btn_return);

            // 设置只能用来看
            //            rbComplete.setIsIndicator(true);
            //            rbTimely.setIsIndicator(true);
        } else {
            // 未验收
            btnAcceptanceCommit.setVisibility(View.VISIBLE);

            //            rbComplete.setRating(2);
            //            rbComplete.setIsIndicator(false);
        }


        // 给文字设置下划线
        tvInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvInfo.setText(R.string.text_info);

        tvScan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvScan.setText(R.string.text_scan);

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
    public void setPresenter(AcceptanceDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * 显示选中任务的详细信息
     *
     * @param value
     */
    @Override
    public void showShipDetail(Acceptance value) {
        this.value = value;

        String preAcceptanceTime = value.getPreAcceptanceTime();
        tvAcceptanceTime.setText(TextUtils.isEmpty(preAcceptanceTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : preAcceptanceTime);
        shipItemNum = value.getShipItemNum();
        tvShipName.setText(value.getShipName());
        tvShipId.setText("船次: " + (shipItemNum == null ? "" : shipItemNum));
        tvSubontractor.setText("供应商: " + value.getSubcontractorName());
        tvTotalVoyage.setText("累计完成航次: " + value.getTotalCompleteRide() + "次");
        tvTotalValue.setText("累计完成方量: " + value.getTotalCompleteSquare() + "㎡");
        tvAvgValue.setText("平均航次方量: " + value.getAvgSquare() + "㎡");

        String currentTide = value.getCurrentTide();
        tvShipCurrentTide.setText("当前潮水: " + (currentTide == null ? "0" : currentTide));

        String maxTakeInWater = value.getMaxTakeInWater();
        tvShipMaxDraft.setText("最大吃水: " + (maxTakeInWater == null ? "0" : maxTakeInWater));

        Float materialIntegrity = value.getMaterialIntegrity();
        Float materialTimeliness = value.getMaterialTimeliness();

        // 回显评价
        rbComplete.setRating(materialIntegrity == null ? 0 : materialIntegrity);
        rbTimely.setRating(materialTimeliness == null ? 0 : materialTimeliness);
    }

    @Override
    public void showAcceptanceTime(String currentDate) {
        tvAcceptanceTime.setText(currentDate);
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
            activity.showProgressDailog("提交中", "提交中...", new OnDailogCancleClickListener() {
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

    }

    @Override
    public void showError() {
        Toast.makeText(activity, "数据获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitError() {
        Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean active) {
        if (active) {
            Toast.makeText(activity, "提交成功!", Toast.LENGTH_SHORT).show();
            btnAcceptanceCancel.setText(R.string.btn_return);
            getActivity().onBackPressed();
        } else {
            Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showCancle() {
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
