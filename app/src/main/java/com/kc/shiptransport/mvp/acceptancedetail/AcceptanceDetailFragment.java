package com.kc.shiptransport.mvp.acceptancedetail;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Acceptance;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private AcceptanceDetailContract.Presenter presenter;
    private AcceptanceDetailActivity activity;
    private int rbcomplete = 0;
    private int rbtimely = 0;
    private String shipItemNum;

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
                    presenter.commit(activity.itemID, time, activity.acceptanceDetailActivity_evaluationID, rbcomplete, rbtimely, shipItemNum);
                }
            }
        });

        /* 点击弹出时间选择器 */
        tvAcceptanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Calendar instance = Calendar.getInstance();
                        instance.set(Calendar.HOUR_OF_DAY, hour);
                        instance.set(Calendar.MINUTE, minute);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String format = df.format(instance.getTime());
                        tvAcceptanceTime.setText(format);
                    }
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), DateFormat.is24HourFormat(activity));

                timePickerDialog.show();
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
