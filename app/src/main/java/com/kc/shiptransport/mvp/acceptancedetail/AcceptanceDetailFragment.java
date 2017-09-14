package com.kc.shiptransport.mvp.acceptancedetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.data.bean.acceptanceinfo.AcceptanceInfoBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.scannerdetail.ScannerDetailActivity;
import com.kc.shiptransport.mvp.scannerimgselect.ScannerImgSelectAdapter;
import com.kc.shiptransport.mvp.voyagedetail.VoyageDetailActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

import static com.kc.shiptransport.R.id.tv;

/**
 * @author qiuyongheng
 * @time 2017/6/1  16:09
 * @desc ${TODD}
 */
public class AcceptanceDetailFragment extends Fragment implements AcceptanceDetailContract.View {

    Unbinder unbinder;
    @BindView(R.id.toolbar_supply_detail)
    Toolbar toolbarSupplyDetail;
    @BindView(tv)
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
    @BindView(R.id.ll_link)
    LinearLayout llLink;
    @BindView(R.id.tv_acceptance_opinion)
    EditText tvAcceptanceOpinion;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_acceptance_return)
    AppCompatButton btnAcceptanceReturn;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    private AcceptanceDetailContract.Presenter presenter;
    private AcceptanceDetailActivity activity;
    private int rbcomplete = 0;
    private int rbtimely = 0;
    private String shipItemNum;
    private AcceptanceInfoBean value;
    private ScannerImgSelectAdapter adapter;

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
        /* 不通过 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过验收时间
                final String time = tvAcceptanceTime.getText().toString().trim();
                // 预验收意见
                final String opinion = tvAcceptanceOpinion.getText().toString().trim();
                // if (time.equals("") || rbcomplete == 0 || rbtimely == 0) {
                if (TextUtils.isEmpty(opinion)) {
                    activity.showDailog("不通过", "填入预验收意见可以更好的帮助供应商进行资料完善", "不填写直接审核", "填写预验收意见", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.commit(activity.itemID, time, activity.acceptanceDetailActivity_evaluationID, rbcomplete, rbtimely, value, -1, opinion);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                } else {
                    activity.showDailog("不通过", "确定预验收审核不通过吗?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.commit(activity.itemID, time, activity.acceptanceDetailActivity_evaluationID, rbcomplete, rbtimely, value, -1, opinion);
                        }
                    });
                }
            }
        });

        /* 通过 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 通过验收时间
                final String time = tvAcceptanceTime.getText().toString().trim();
                // 预验收意见
                final String opinion = tvAcceptanceOpinion.getText().toString().trim();
                if (time.equals("")) {
                    Toast.makeText(activity, "验收时间不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    activity.showDailog("通过", "确定预验收审核通过吗?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            presenter.commit(activity.itemID, time, activity.acceptanceDetailActivity_evaluationID, rbcomplete, rbtimely, value, 1, opinion);
                        }
                    });
                }
            }
        });

        /* 返回 */
        btnAcceptanceReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
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
            btnAcceptanceCancel.setVisibility(View.GONE);
            btnAcceptanceReturn.setVisibility(View.VISIBLE);


            // 设置只能用来看
            rbComplete.setIsIndicator(true);
            rbTimely.setIsIndicator(true);

            // 不可编辑
            tvAcceptanceOpinion.setFocusable(false);
            tvAcceptanceOpinion.setFocusableInTouchMode(false);
            tvAcceptanceOpinion.setHint("");
        } else {
            // 未验收
            btnAcceptanceCommit.setVisibility(View.VISIBLE);
            btnAcceptanceCancel.setVisibility(View.VISIBLE);
            btnAcceptanceReturn.setVisibility(View.GONE);

            // 可以编辑
            tvAcceptanceOpinion.setFocusable(true);
            tvAcceptanceOpinion.setFocusableInTouchMode(true);

            /* 点击弹出时间选择器 */
            tvAcceptanceTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        CalendarUtil.showTimePickerDialog(getContext(), tvAcceptanceTime, new OnTimePickerSureClickListener() {
                            @Override
                            public void onSure(String str) {

                            }
                        }, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


        // 给文字设置下划线
        tvInfo.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvInfo.setText(R.string.text_info);

        tvScan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        tvScan.setText(R.string.text_scan);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
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
    public void showShipDetail(AcceptanceInfoBean value) {
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
        tvStatus.setText(value.getStatus() == 1 ? "通过" : (value.getStatus() == 0 ? "待审核" : "不通过"));
        tvAcceptanceOpinion.setText(value.getRemark());

        String currentTide = value.getCurrentTide();
        tvShipCurrentTide.setText("当前潮水: " + (currentTide == null ? "0" : currentTide));

        String maxTakeInWater = value.getMaxTakeInWater();
        tvShipMaxDraft.setText("最大吃水: " + (maxTakeInWater == null ? "0" : maxTakeInWater));

        Float materialIntegrity = value.getMaterialIntegrity();
        Float materialTimeliness = value.getMaterialTimeliness();

        // 回显评价
        rbComplete.setRating(materialIntegrity == null ? 0 : materialIntegrity);
        rbTimely.setRating(materialTimeliness == null ? 0 : materialTimeliness);


        /** TODO 显示图片 */

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

    /**
     * 显示图片列表
     *
     * @param scannerImgListByTypeBeen
     */
    @Override
    public void showImgList(List<ScannerImgListByTypeBean> scannerImgListByTypeBeen) {
        if (scannerImgListByTypeBeen == null) {
            LogUtil.d("没有预验砂图片");
            scannerImgListByTypeBeen = new ArrayList<>();
        }

        if (adapter == null) {
            adapter = new ScannerImgSelectAdapter(getContext(), scannerImgListByTypeBeen);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    final ScannerImgListByTypeBean scannerImgListByTypeBean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 显示图片
                        ImageActivity.startActivity(getContext(), scannerImgListByTypeBean.getFilePath());
                    } else {

                        if (value.getStatus() != 1) {
                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除图片
                                    presenter.deleteImg(scannerImgListByTypeBean.getItemID());
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "验砂已完成, 不可删除", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (value.getStatus() != 1) {
                        // 弹出图片选择器, 设置多选图片张数
                        //                            int maxSize = activity.mDefaulAttachmentCount - adapter.list.size();
                        int maxSize = SettingUtil.NUM_IMAGE_SELECTION - adapter.list.size();
                        if (maxSize > 0) {
                            RxGalleryUtil.getImagMultiple(getContext(), maxSize, new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 提交图片
                                    presenter.commitImg(imageMultipleResultEvent, activity.itemID, 2, value.getShipAccount());
                                }

                                @Override
                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                    // 单选回调
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "图片张数已到达上限", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "已提交, 不能添加图片", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDates(scannerImgListByTypeBeen);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress(int max) {
        activity.progressDialog("gaga", max, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 取消任务
                presenter.unsubscribe();
            }
        });
    }

    @Override
    public void updateProgress() {
        activity.updataProgress(new OnProgressFinishListener() {
            @Override
            public void onFinish() {
                // TODO 全部上传成功的回调
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                // 同步数据
                presenter.updateImgDetail(activity.itemID);
                hideProgress();
            }
        });
    }

    @Override
    public void showDeleteResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "删除成功");
            presenter.updateImgDetail(activity.itemID);
        } else {
            ToastUtil.tip(getContext(), "删除失败, 请重试");
        }
    }

    /**
     * 刷新图片
     *
     * @param value
     */
    @Override
    public void showImgList(AcceptanceInfoBean value) {

    }

    @Override
    public void hideProgress() {
        activity.hideProgress();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
