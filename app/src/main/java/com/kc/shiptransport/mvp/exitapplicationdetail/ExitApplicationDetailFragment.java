package com.kc.shiptransport.mvp.exitapplicationdetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/7/12  10:01
 * @desc ${TODD}
 */

public class ExitApplicationDetailFragment extends Fragment implements ExitApplicationDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv)
    TextView tvShipName;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_captain)
    TextView tvCaptain;
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
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_nation_phone)
    TextView tvNationPhone;
    @BindView(R.id.tv_internal_phone)
    TextView tvInternalPhone;
    @BindView(R.id.ll_quantum)
    LinearLayout llQuantum;
    @BindView(R.id.btn_return)
    AppCompatButton btnReturn;
    private ExitApplicationDetailContract.Presenter presenter;
    private ExitApplicationDetailActivity activity;
    private ExitApplicationDetailAdapter adapter;
    private ExitDetail bean;
    private ArrayList<ImgList> imgLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_application, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);

        /** 如果已完善, 不能修改 */
        if (activity.isExit == 1) {
            // 已完善
            etQuantum.setFocusable(false);
            etQuantum.setFocusableInTouchMode(false);
            btnCommit.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            btnReturn.setVisibility(View.VISIBLE);

            etRemark.setFocusable(false);
            etRemark.setFocusableInTouchMode(false);
        } else {
            // 未完善
            etQuantum.setFocusable(true);
            etQuantum.setFocusableInTouchMode(true);
            btnCommit.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.VISIBLE);
            btnReturn.setVisibility(View.GONE);

            etRemark.setFocusable(true);
            etRemark.setFocusableInTouchMode(true);

            tvSupplyTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        CalendarUtil.showTimePickerDialog(getContext(), tvSupplyTime, new OnTimePickerSureClickListener() {
                            @Override
                            public void onSure(String str) {
                                // TODO 可以限制不能选择之前的时间
                            }
                        }, new OnTimePickerLastDateClickListener() {
                            @Override
                            public void onLastDate() {

                            }
                        }, false, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        initListener();


        // TODO 获取要显示的数据
        presenter.getDates(activity.itemID, 0);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ExitApplicationDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_exit_application);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    @Override
    public void initListener() {
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
                int IsSumbitted = 0;
                int Status = 0;

                if (TextUtils.isEmpty(time)) {
                    Toast.makeText(getContext(), "请填写退场时间", Toast.LENGTH_SHORT).show();
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

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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

                        if (TextUtils.isEmpty(time)) {
                            Toast.makeText(getContext(), "请填写退场时间", Toast.LENGTH_SHORT).show();
                        } else {
                            presenter.commit(itemID, time, creator, remark, RemnantAmount, SubcontractorInterimApproachPlanID, IsSumbitted, Status);
                        }
                    }
                });

            }
        });

        /** 返回 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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
    public void setPresenter(ExitApplicationDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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

        /** 设置数据 */
        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);
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


        /** 获取图片列表 */
        List<ExitDetail.AttachmentListBean> imageList = bean.getAttachmentList();
        if (imageList == null) {
            imageList = new ArrayList<>();
        }


        /** 初始化图片列表 */
        if (adapter == null) {
            adapter = new ExitApplicationDetailAdapter(getContext(), imageList);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    /** 预览, 删除图片 */
                    final ExitDetail.AttachmentListBean bean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
//                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                        imgLists.clear();
                        for (ExitDetail.AttachmentListBean listBean : adapter.list) {
                            ImgList imgList = new ImgList();
                            imgList.setPath(listBean.getFilePath());
                            imgLists.add(imgList);
                        }
                        ImgViewPageActivity.startActivity(getContext(), imgLists, position);
                    } else {
                        if (activity.isExit == 1) {
                            Toast.makeText(getContext(), "退场申请已提交, 不能删除图片", Toast.LENGTH_SHORT).show();
                        } else {
                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除
                                    presenter.deleteImgForItemID(bean.getItemID());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (activity.isExit == 1) {
                        Toast.makeText(getContext(), "退场申请已提交, 不能新增图片", Toast.LENGTH_SHORT).show();
                    } else {
                        /** 弹出图片选择器 */
                        int size = adapter.list.size();
                        int max = SettingUtil.NUM_IMAGE_SELECTION - size;
                        if (max > 0) {
                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 把图片解析成可以上传的任务, 上传
                                    List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                                    presenter.getCommitImgList(imageMultipleResultEvent, activity.itemID, all.get(0).getSubcontractorAccount());
                                }

                                @Override
                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "已到达图片选择上限", Toast.LENGTH_SHORT).show();
                        }
                    }
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
