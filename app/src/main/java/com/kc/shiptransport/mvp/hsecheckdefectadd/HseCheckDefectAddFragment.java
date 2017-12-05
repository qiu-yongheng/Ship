package com.kc.shiptransport.mvp.hsecheckdefectadd;

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
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.hse.HseDefectAddCommitBean;
import com.kc.shiptransport.data.bean.hse.HseDefectDetailBean;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.db.hse.HseDefectDeadline;
import com.kc.shiptransport.db.hse.HseDefectType;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.mvp.BaseFragmentBack;
import com.kc.shiptransport.mvp.basemvp.ImgSelectAdapter;
import com.kc.shiptransport.util.PopwindowUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2017/11/22  17:42
 * @desc ${TODD}
 */

public class HseCheckDefectAddFragment extends BaseFragmentBack<HseCheckDefectAddActivity> implements HseCheckDefectAddContract.View, View.OnClickListener {
    @BindView(R.id.tv_checker)
    TextView tvChecker;
    @BindView(R.id.tv_defect_type)
    TextView tvDefectType;

    @BindView(R.id.tv_defect_deadline)
    TextView tvDefectDeadline;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    @BindView(R.id.et_defect_item)
    EditText etDefectItem;
    private HseCheckDefectAddContract.Presenter presenter;
    private String userAccount;
    private List<HseDefectType> defectType;
    private List<HseDefectDeadline> defectDeadline;
    private int hse_defect_type_id;
    private int hse_defect_deadline_id;
    private String hse_defect_item;
    private String hse_remark;
    private List<ImgList> imgLists = new ArrayList<>(); // 图片显示列表
    private List<ImgList> deleteImgLists = new ArrayList<>(); // 需要请求删除的图片列表
    //    private List<ImgList> addImgLists = new ArrayList<>(); // 需要提交的图片列表
    private ImgSelectAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initViews(rootView);
        initData();
        initListener();
        initAdapter();

        return rootView;
    }

    private void initData() {
        // 缺陷类别
        defectType = DataSupport.findAll(HseDefectType.class);
        // 整改期限
        defectDeadline = DataSupport.findAll(HseDefectDeadline.class);
    }

    @Override
    public void initViews(View view) {
        // 当前用户
        List<User> all = DataSupport.findAll(User.class);
        userAccount = all.isEmpty() ? "" : all.get(0).getUserID();
        tvChecker.setText(all.isEmpty() ? "" : all.get(0).getUserName());

        // recycler view
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        switch (activity.type) {
            case SettingUtil.TYPE_HSE_DEFECT_ADD:
                tvDefectType.setOnClickListener(this);
                tvDefectDeadline.setOnClickListener(this);
                break;
            case SettingUtil.TYPE_HSE_DEFECT_UPDATE:
                presenter.getDetailData(activity.itemID);
                tvDefectType.setOnClickListener(this);
                tvDefectDeadline.setOnClickListener(this);
                break;
            case SettingUtil.TYPE_HSE_DEFECT_READ_ONLY:
                presenter.getDetailData(activity.itemID);

                tvDefectType.setBackground(null);
                tvDefectDeadline.setBackground(null);
                etDefectItem.setBackground(null);
                etDefectItem.setFocusable(false);
                etDefectItem.setFocusableInTouchMode(false);
                btnReturn.setVisibility(View.VISIBLE);
                btnCommit.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        if (adapter == null) {
            adapter = new ImgSelectAdapter(getContext(), imgLists, activity.type != SettingUtil.TYPE_HSE_DEFECT_READ_ONLY);
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
    public void initListener() {
        btnCommit.setOnClickListener(this);
        btnReturn.setOnClickListener(this);
    }

    @Override
    public void setPresenter(HseCheckDefectAddContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "请稍等", new OnDailogCancleClickListener() {
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
        return R.layout.fragment_hsecheck_defect_add;
    }

    @Override
    public int setTitle() {
        return R.string.title_hse_check_defect_add;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_defect_type: // 缺陷类别
                PopwindowUtil.showPopwindow(getContext(), defectType, tvDefectType, true, new PopwindowUtil.InitHolder<HseDefectType>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseDefectType hseDefectType, int position) {
                        holder.setText(R.id.tv_spinner, hseDefectType.getName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvDefectType.setText(hseDefectType.getName());
                                        hse_defect_type_id = hseDefectType.getItemID(); // id
                                        // TODO 记忆
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.tv_defect_deadline: // 整改期限
                PopwindowUtil.showPopwindow(getContext(), defectDeadline, tvDefectDeadline, true, new PopwindowUtil.InitHolder<HseDefectDeadline>() {
                    @Override
                    public void initHolder(ViewHolder holder, final HseDefectDeadline hseDefectDeadline, int position) {
                        holder.setText(R.id.tv_spinner, hseDefectDeadline.getRectificationDeadlineName())
                                .setOnClickListener(R.id.tv_spinner, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tvDefectDeadline.setText(hseDefectDeadline.getRectificationDeadlineName());
                                        hse_defect_deadline_id = hseDefectDeadline.getRectificationDeadlineID();


                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.btn_commit:
                // 提交
                commit();
                break;
            case R.id.btn_return:
                // 返回
                getActivity().onBackPressed();
                break;
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
                    commit();
                }
            });
            return true;
        }
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

    @Override
    public void showDetailData(HseDefectDetailBean bean) {
        // 缺陷类别
        String defectTypeName = bean.getDefectTypeName();
        hse_defect_type_id = bean.getDefectTypeID();
        // 缺陷项目
        hse_defect_item = bean.getDefectItem();
        // 整改期限
        String rectificationDeadlineName = bean.getRectificationDeadlineName();
        hse_defect_deadline_id = bean.getRectificationDeadline();
        // 备注
        hse_remark = bean.getRemark();

        tvDefectType.setText(defectTypeName);
        etDefectItem.setText(hse_defect_item);
        tvDefectDeadline.setText(rectificationDeadlineName);
        etRemark.setText(hse_remark);


        if (bean.getAttachmentList() != null) {
            List<ImgList> imgLists = RxGalleryUtil.toImgList(bean.getAttachmentList());
            adapter.loadmore(imgLists);
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
     * 提交修改
     */
    public void commit() {
        hse_defect_item = etDefectItem.getText().toString().trim();
        hse_remark = etRemark.getText().toString().trim();
        if (hse_defect_type_id == 0) {
            ToastUtil.tip(getContext(), "请选择缺陷类别");
        } else if (TextUtils.isEmpty(hse_defect_item)) {
            ToastUtil.tip(getContext(), "请填写缺陷项目");
        } else if (hse_defect_deadline_id == 0) {
            ToastUtil.tip(getContext(), "请选择整改期限");
        } else {
            presenter.commit(new HseDefectAddCommitBean(activity.itemID,
                    activity.checkRecordID,
                    hse_defect_type_id,
                    hse_defect_item,
                    hse_defect_deadline_id,
                    creator,
                    hse_remark), RxGalleryUtil.getNoCommitImg(imgLists), deleteImgLists);
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
}
