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
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import org.litepal.crud.DataSupport;

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
    @BindView(R.id.et_remark)
    EditText etRemark;
    private ExitApplicationDetailContract.Presenter presenter;
    private ExitApplicationDetailActivity activity;
    private ExitApplicationDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exit_application, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 获取要显示的数据
        presenter.getDates(activity.itemID, 0);
        return view;
    }

    @Override
    public void initViews(View view) {
        activity = (ExitApplicationDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_exit_application);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
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
                String userID = DataSupport.findAll(User.class).get(0).getUserID();
                String time = tvSupplyTime.getText().toString();
                String quantum = etQuantum.getText().toString();
                String remark = etRemark.getText().toString();
                
                if (TextUtils.isEmpty(quantum)) {
                    Toast.makeText(getContext(), "请填写残余方量", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.commit(0, time, userID, remark, quantum, activity.itemID);
                }
            }
        });

        tvSupplyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showPickerDialog(getContext(), tvSupplyTime, CalendarUtil.YYYY_MM_DD_HH_MM, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {
                        // TODO 可以限制不能选择之前的时间
                    }
                });
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
        // 船名
        String shipName = bean.getShipName();
        // 供应商
        String subcontractorName = bean.getSubcontractorName();
        // 船长
        String captain = bean.getCaptain();
        // 手机
        String captainPhone = bean.getCaptainPhone();
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

        /** 设置数据 */
        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);
        tvSub.setText(TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName);
        tvCaptain.setText(TextUtils.isEmpty(captain) ? "" : captain);
        tvCaptainPhone.setText(TextUtils.isEmpty(captainPhone) ? "" : captainPhone);
        tvShipNum.setText(TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum);
        tvArriveAnchorTime.setText(TextUtils.isEmpty(arrivaOfAnchorageTime) ? "" : arrivaOfAnchorageTime);
        tvStowage.setText(TextUtils.isEmpty(compartmentQuantity) ? "" : compartmentQuantity);
        tvMaterial.setText(TextUtils.isEmpty(materialClassification) ? "" : materialClassification);
        tvSupplyTime.setText(TextUtils.isEmpty(exitTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : exitTime);
        etQuantum.setText(TextUtils.isEmpty(remnantAmount) ? "" : remnantAmount);


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
                    ExitDetail.AttachmentListBean bean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                    } else {
                        // 删除
                        presenter.deleteImgForItemID(bean.getItemID());
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    /** 弹出图片选择器 */
                    int size = adapter.list.size();
                    int max = 3 - size;
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
