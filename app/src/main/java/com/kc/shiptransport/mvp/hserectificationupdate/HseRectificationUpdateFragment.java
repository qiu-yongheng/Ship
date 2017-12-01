package com.kc.shiptransport.mvp.hserectificationupdate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectDetailBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationBean;
import com.kc.shiptransport.data.bean.hse.rectification.HseRectificationCommitBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.BaseFragmentBack;
import com.kc.shiptransport.mvp.basemvp.ImgSelectAdapter;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2017/11/28  14:19
 * @desc ${TODD}
 */

public class HseRectificationUpdateFragment extends BaseFragmentBack<HseRectificationUpdateActivity> implements HseRectificationUpdateContract.View, View.OnClickListener {
    @BindView(R.id.tv_checker)
    TextView tvChecker;
    @BindView(R.id.tv_defect_date)
    TextView tvDefectDate;
    @BindView(R.id.tv_defect_type)
    TextView tvDefectType;
    @BindView(R.id.tv_defect_deadline)
    TextView tvDefectDeadline;
    @BindView(R.id.tv_defect_item)
    TextView tvDefectItem;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.recycler_view_defect)
    RecyclerView recyclerViewDefect;
    @BindView(R.id.tv_defect_img)
    TextView tvDefectImg;
    @BindView(R.id.tv_rectification_date)
    TextView tvRectificationDate;
    @BindView(R.id.ll_rectification_date)
    LinearLayout llRectificationDate;
    @BindView(R.id.tv_defect_deadline_date)
    TextView tvDefectDeadlineDate;
    @BindView(R.id.tv_rectification_img)
    TextView tvRectificationImg;
    private HseRectificationUpdateContract.Presenter presenter;
    private int defectTypeID;
    private int rectificationDeadline;
    private String defectItem;
    private String remark;
    private List<ImgList> imgLists = new ArrayList<>(); // 图片显示列表
    private List<ImgList> deleteImgLists = new ArrayList<>(); // 需要请求删除的图片列表
    private ImgSelectAdapter adapter;
    private CommonAdapter<ImgList> defectAdapter;
    private String rectificationDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initListener();
        initAdapter();
        return rootView;
    }

    @Override
    public void initViews(View view) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerViewDefect.setLayoutManager(new GridLayoutManager(getContext(), 4));
        assert activity.mData != null;
        // 当前用户
        String creatorName = activity.mData.getCreatorName();
        creator = activity.mData.getCreator();
        tvChecker.setText(creatorName);
        // 提单时间
        String systemDate = activity.mData.getSystemDate();
        tvDefectDate.setText(systemDate);
        // 缺陷类别
        String defectTypeName = activity.mData.getDefectTypeName();
        defectTypeID = activity.mData.getDefectTypeID();
        tvDefectType.setText(defectTypeName);
        // 整改期限
        String rectificationDeadlineName = activity.mData.getRectificationDeadlineName();
        rectificationDeadline = activity.mData.getRectificationDeadline();
        tvDefectDeadline.setText(rectificationDeadlineName);
        // 缺陷项目
        defectItem = activity.mData.getDefectItem();
        tvDefectItem.setText(defectItem);
        // 整改时间
        rectificationDate = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS);
        tvRectificationDate.setText(rectificationDate);
        // 截止整改日期
        tvDefectDeadlineDate.setText(activity.mData.getDeadlineTime());

        switch (activity.type) {
            case SettingUtil.TYPE_HSE_RECTIFICATION_ADD: // 新增, 不需要显示缺陷图片
                tvRectificationDate.setOnClickListener(this);
                if (activity.mData.getRectificationRecordID() != 0) {
                    // 更新数据(获取整改数据, 包含整改图片)
                    presenter.getDetailData(activity.mData.getRectificationRecordID());
                }
                break;
            case SettingUtil.TYPE_HSE_RECTIFICATION_READ_ONLY: // 只读(已整改)
                btnSave.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                btnReturn.setVisibility(View.VISIBLE);

                // 不可编辑 (不能获取焦点)
                etRemark.setFocusable(false);
                etRemark.setFocusableInTouchMode(false);
                tvRectificationDate.setBackground(null);

                // 更新数据(获取整改数据, 包含整改图片)
                presenter.getDetailData(activity.mData.getRectificationRecordID());
                break;
            case SettingUtil.TYPE_HSE_RECTIFICATION_DEFECT_DETAIL: // 缺陷详情, 显示缺陷图片
                btnSave.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                btnReturn.setVisibility(View.VISIBLE);

                // 不可编辑 (不能获取焦点)
                etRemark.setFocusable(false);
                etRemark.setFocusableInTouchMode(false);
                llRectificationDate.setVisibility(View.GONE);

                // 请求缺陷详情
                presenter.getDefectDetailData(activity.mData.getItemID());
                break;
        }
    }

    @Override
    public void initListener() {
        btnSave.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
    }

    private void initAdapter() {
        if (adapter == null) {
            adapter = new ImgSelectAdapter(getContext(), imgLists, activity.type == SettingUtil.TYPE_HSE_RECTIFICATION_ADD);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, final int position, int... type) {
                    switch (type[0]) {
                        case 0: // 预览
                            ImgViewPageActivity.startActivity(getContext(), (ArrayList<ImgList>) adapter.getDatas(), position);
                            break;
                        case SettingUtil.ITEM_DELETE: // 删除
                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 如果itemID不为0, 保存到待删除列表
                                    if (adapter.getDatas().get(position).getItemID() != 0) {
                                        deleteImgLists.add(adapter.getDatas().get(position));
                                    }

                                    // 从图片列表删除
                                    adapter.removeItem(position);
                                }
                            });
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    // 弹出图片选择器
                    RxGalleryUtil.getImagMultiple(getContext(), SettingUtil.NUM_IMAGE_SELECTION, new OnRxGalleryRadioListener() {
                        @Override
                        public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                            List<ImgList> list = RxGalleryUtil.MultipletoImgList(imageMultipleResultEvent);
                            adapter.loadmore(list);
                        }

                        @Override
                        public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                        }
                    });
                }
            });
        }
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(HseRectificationUpdateContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("请稍等", "请稍等...", new OnDailogCancleClickListener() {
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
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public int setView() {
        return R.layout.fragment_hse_rectification;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_retification_add;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit: // 提交
                commit(1);
                break;
            case R.id.btn_save: // 保存
                commit(0);
                break;
            case R.id.btn_return: // 返回
                activity.onBackPressed();
                break;
            case R.id.tv_rectification_date: // 时间
                try {
                    CalendarUtil.showTimePickerDialog(getContext(), tvRectificationDate, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            rectificationDate = str;
                        }
                    }, new OnTimePickerLastDateClickListener() {
                        @Override
                        public void onLastDate() {

                        }
                    }, false, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 提交数据
     *
     * @param IsSubmitted 1: 提交后不可修改, 0: 保存
     */
    public void commit(int IsSubmitted) {
        remark = etRemark.getText().toString().trim();
        rectificationDate = tvRectificationDate.getText().toString().trim();
        if (imgLists.isEmpty()) {
            ToastUtil.tip(getContext(), "请选择整改图片");
        } else {
            presenter.commit(new HseRectificationCommitBean(activity.mData.getRectificationRecordID(),
                    activity.mData.getItemID(),
                    activity.mData.getHSECheckedRecordID(),
                    rectificationDate,
                    creator,
                    remark,
                    IsSubmitted,
                    null), RxGalleryUtil.getNoCommitImg(imgLists), deleteImgLists);
        }
    }

    /**
     * @param title
     * @param max
     */
    @Override
    public void showProgressDialog(String title, int max) {
        activity.progressDialog(title, max, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.unsubscribe();
            }
        });
    }

    /**
     * @param title
     * @param len
     */
    @Override
    public void updateProgressDialog(String title, int len) {
        activity.updateProgressTitle(title, len);
    }

    @Override
    public void hideProgressDialog() {
        activity.hideProgress();
    }

    /**
     * @param bean
     */
    @Override
    public void showDetailData(HseRectificationBean bean) {
        // 备注
        String remark = bean.getRemark();
        etRemark.setText(remark);
        // 整改时间
        rectificationDate = bean.getRectificationTime();
        tvRectificationDate.setText(rectificationDate);
        // 整改图片
        if (bean.getRectificationAttachmentList() != null) {
            List<ImgList> imgLists = RxGalleryUtil.rectificationToImgList(bean.getRectificationAttachmentList());
            adapter.loadmore(imgLists);
        }

        if (activity.type == SettingUtil.TYPE_HSE_RECTIFICATION_READ_ONLY) { // 已整改
            // 缺陷图片
            if (bean.getDefectAttachmentList() != null) {
                tvDefectImg.setVisibility(bean.getDefectAttachmentList().isEmpty() ? View.GONE : View.VISIBLE);
                List<ImgList> imgLists = RxGalleryUtil.defectToImgList(bean.getDefectAttachmentList());
                initDefectAdapter(imgLists);
            } else {
                tvDefectImg.setVisibility(View.GONE);
            }
        }
    }

    private void initDefectAdapter(final List<ImgList> imgLists) {
        defectAdapter = new CommonAdapter<ImgList>(getContext(), R.layout.item_img, imgLists) {
            @Override
            protected void convert(ViewHolder holder, ImgList imgList, final int position) {
                holder.setVisible(R.id.iv_delete, false)
                        .setOnClickListener(R.id.iv_img, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImgViewPageActivity.startActivity(getContext(), (ArrayList<ImgList>) defectAdapter.getDatas(), position);
                            }
                        });
                ImageView imageView = holder.getView(R.id.iv_img);
                RxGalleryUtil.showImage(getContext(), imgList.getPath(), null, null, imageView);
            }
        };

        recyclerViewDefect.setAdapter(defectAdapter);
    }

    /**
     * @param bean
     */
    @Override
    public void showDefectDetailData(HseDefectDetailBean bean) {
        // 备注
        etRemark.setText(TextUtils.isEmpty(bean.getRemark()) ? "" : bean.getRemark());
        tvDefectImg.setVisibility(View.VISIBLE);
        tvRectificationImg.setVisibility(View.GONE);
        // 图片
        if (bean.getAttachmentList() != null) {
            List<ImgList> imgLists = RxGalleryUtil.toImgList(bean.getAttachmentList());
            initDefectAdapter(imgLists);
        }
    }

    @Override
    public boolean onBackPressed() {
        if (RxGalleryUtil.getNoCommitImg(imgLists).isEmpty() && deleteImgLists.isEmpty()) {
            return false;
        } else {
            activity.showDailog("警告", "修改数据还未提交, 是否提交?", "返回不保存", "提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    back();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commit(0);
                }
            });
            return true;
        }
    }

    /**
     * 返回
     */
    public void back() {
        imgLists.clear();
        deleteImgLists.clear();
        activity.onBackPressed();
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "提交成功");
            back();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }
}
