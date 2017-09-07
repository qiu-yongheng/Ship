package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/8/31  15:46
 * @desc ${TODD}
 */

public class ExitApplicationAssessorFragment extends Fragment implements ExitApplicationAssessorContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_captain)
    TextView tvCaptain;
    @BindView(R.id.tv_nation_phone)
    TextView tvNationPhone;
    @BindView(R.id.tv_internal_phone)
    TextView tvInternalPhone;
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
    @BindView(R.id.tv_total_volume)
    TextView tvTotalVolume;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.btn_return)
    AppCompatButton btnReturn;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    private ExitApplicationAssessorContract.Presenter presenter;
    private ExitApplicationAssessorActivity activity;
    private ExitDetail bean;
    private ExitApplicationAssessorAdapter adapter;
    private int totalAmount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_application_assessor, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        presenter.getDates(activity.itemID, 0);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ExitApplicationAssessorActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_exit_assessor);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
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

        /** 保存 */
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null) {
                    ToastUtil.tip(getContext(), "退场数据获取失败, 请退出界面重试");
                    return;
                }
                int itemID = TextUtils.isEmpty(bean.getItemID()) ? 0 : Integer.valueOf(bean.getItemID());
                String time = tvSupplyTime.getText().toString();
                String creator = DataSupport.findAll(User.class).get(0).getUserID();
                String remark = etRemark.getText().toString();
                String RemnantAmount = TextUtils.isEmpty(etQuantum.getText().toString()) ? "0" : etQuantum.getText().toString();
                int SubcontractorInterimApproachPlanID = activity.itemID;
                int IsSumbitted = 1;
                int Status = 0;

                if (TextUtils.isEmpty(RemnantAmount)) {
                    Toast.makeText(getContext(), "请填写残余方量", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.commit(itemID, time, creator, remark, RemnantAmount, SubcontractorInterimApproachPlanID, IsSumbitted, Status);
                }
            }
        });

        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDailog("提交", "提交后, 将不能进行修改, 是否提交?", "取消", "提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 取消
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 提交
                        if (bean == null) {
                            ToastUtil.tip(getContext(), "退场数据获取失败, 请退出界面重试");
                            return;
                        }
                        int itemID = TextUtils.isEmpty(bean.getItemID()) ? 0 : Integer.valueOf(bean.getItemID());
                        String time = tvSupplyTime.getText().toString();
                        String creator = DataSupport.findAll(User.class).get(0).getUserID();
                        String remark = etRemark.getText().toString();
                        String RemnantAmount = TextUtils.isEmpty(etQuantum.getText().toString()) ? "0" : etQuantum.getText().toString();
                        int SubcontractorInterimApproachPlanID = activity.itemID;
                        int IsSumbitted = 1;
                        int Status = 1;

                        if (TextUtils.isEmpty(RemnantAmount)) {
                            Toast.makeText(getContext(), "请填写残余方量", Toast.LENGTH_SHORT).show();
                        } else {
                            presenter.commit(itemID, time, creator, remark, RemnantAmount, SubcontractorInterimApproachPlanID, IsSumbitted, Status);
                        }
                    }
                });
            }
        });

        /** edit 监听 */
        etQuantum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                int v = TextUtils.isEmpty(s) ? 0 : Integer.valueOf(s);
                tvTotalVolume.setText(String.valueOf(totalAmount - v));
            }
        });
    }

    @Override
    public void setPresenter(ExitApplicationAssessorContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中...", new OnDailogCancleClickListener() {
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
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "提交失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取要显示的数据, 显示图片列表
     */
    @Override
    public void showDates(ExitDetail bean) {
        this.bean = bean;
        // 船名
        String shipName = bean.getShipName();
        // 供应商
        String subcontractorName = bean.getSubcontractorName();
        // 船长
        String captain = bean.getCaptain();
        // 国内电话
        String nationPhone = bean.getNationPhone();
        // 国外电话
        String internalPhone = bean.getInternalPhone();
        // 航次编号
        String shipItemNum = bean.getShipItemNum();
        // 到达锚地时间
        String arrivaOfAnchorageTime = bean.getArrivaOfAnchorageTime();
        // 装舱方
        String compartmentQuantity = bean.getCompartmentQuantity();
        // 材料分类
        String materialClassification = bean.getMaterialClassification();
        // 退场时间
        String exitTime = bean.getExitTime();
        // 残余方量
        String remnantAmount = bean.getRemnantAmount();
        // 备注
        String remark = bean.getRemark();
        // 量方合计方
        totalAmount = TextUtils.isEmpty(bean.getTotalAmount()) ? 0 : Integer.valueOf(bean.getTotalAmount());
        // 总合计方
        int total = totalAmount - (TextUtils.isEmpty(remnantAmount) ? 0 : Integer.valueOf(remnantAmount));

        /** 设置数据 */
        tv.setText(TextUtils.isEmpty(shipName) ? "" : shipName);
        tvSub.setText(TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName);
        tvCaptain.setText(TextUtils.isEmpty(captain) ? "" : captain);
        tvNationPhone.setText(TextUtils.isEmpty(nationPhone) ? "" : nationPhone);
        tvInternalPhone.setText(TextUtils.isEmpty(internalPhone) ? "" : internalPhone);
        tvShipNum.setText(TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum);
        tvArriveAnchorTime.setText(TextUtils.isEmpty(arrivaOfAnchorageTime) ? "" : arrivaOfAnchorageTime);
        tvStowage.setText(TextUtils.isEmpty(compartmentQuantity) ? "" : compartmentQuantity);
        tvMaterial.setText(TextUtils.isEmpty(materialClassification) ? "" : materialClassification);
        tvSupplyTime.setText(TextUtils.isEmpty(exitTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : exitTime);
        etQuantum.setText(TextUtils.isEmpty(remnantAmount) ? "" : remnantAmount);
        etRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);
        tvTotalVolume.setText(String.valueOf(total));


        /** 获取图片列表 */
        List<ExitDetail.AttachmentListBean> imageList = bean.getAttachmentList();
        if (imageList == null) {
            imageList = new ArrayList<>();
        }


        /** 初始化图片列表 */
        if (adapter == null) {
            adapter = new ExitApplicationAssessorAdapter(getContext(), imageList);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    /** 预览, 删除图片 */
                    final ExitDetail.AttachmentListBean bean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                    }
                    //                    else {
                    //                        if (activity.isExit == 1) {
                    //                            Toast.makeText(getContext(), "退场申请已提交, 不能删除图片", Toast.LENGTH_SHORT).show();
                    //                        } else {
                    //                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                    //                                @Override
                    //                                public void onClick(DialogInterface dialogInterface, int i) {
                    //                                    // 删除
                    //                                    presenter.deleteImgForItemID(bean.getItemID());
                    //                                }
                    //                            });
                    //                        }
                    //                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    //                    if (activity.isExit == 1) {
                    //                        Toast.makeText(getContext(), "退场申请已提交, 不能新增图片", Toast.LENGTH_SHORT).show();
                    //                    } else {
                    //                        /** 弹出图片选择器 */
                    //                        int size = adapter.list.size();
                    //                        int max = SettingUtil.NUM_IMAGE_SELECTION - size;
                    //                        if (max > 0) {
                    //                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                    //                                @Override
                    //                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                    //                                    // 把图片解析成可以上传的任务, 上传
                    //                                    List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                    //                                    presenter.getCommitImgList(imageMultipleResultEvent, activity.itemID, all.get(0).getSubcontractorAccount());
                    //                                }
                    //
                    //                                @Override
                    //                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                    //
                    //                                }
                    //                            });
                    //                        } else {
                    //                            Toast.makeText(getContext(), "已到达图片选择上限", Toast.LENGTH_SHORT).show();
                    //                        }
                    //                    }
                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgress(int max) {
        activity.progressDialog("提交图片", max, new DialogInterface.OnClickListener() {
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
                // TODO 全部上传后的回调
                presenter.getDates(activity.itemID, 1);
            }
        });
    }

    @Override
    public void showDeleteResult(Boolean aBoolean) {
        if (aBoolean) {
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
            presenter.getDates(activity.itemID, 1);
        } else {
            Toast.makeText(getContext(), "删除失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showImgList(ExitDetail exitDetail) {
        List<ExitDetail.AttachmentListBean> list = exitDetail.getAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }

        if (adapter != null) {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }
}
