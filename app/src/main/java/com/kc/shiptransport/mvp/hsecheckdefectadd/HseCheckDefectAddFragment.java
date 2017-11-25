package com.kc.shiptransport.mvp.hsecheckdefectadd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
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
    @BindView(R.id.tv_defect_project)
    TextView tvDefectProject;
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
    private HseCheckDefectAddContract.Presenter presenter;
    private String userAccount;
    private List<HseDefectType> defectType;
    private List<HseDefectDeadline> defectDeadline;
    private int hse_defect_type;
    private int hse_defect_deadline;
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
        // 缺陷项目
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
    }

    /**
     * 初始化adapter
     */
    private void initAdapter() {
        if (adapter == null) {
            adapter = new ImgSelectAdapter(getContext(), imgLists);
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
        tvDefectType.setOnClickListener(this);
        tvDefectProject.setOnClickListener(this);
        tvDefectDeadline.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
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
                                        hse_defect_type = hseDefectType.getItemID(); // id
                                        // TODO 记忆
                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.tv_defect_project: // 缺陷项目
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
                                        hse_defect_deadline = hseDefectDeadline.getRectificationDeadlineID();


                                        PopwindowUtil.hidePopwindow();
                                    }
                                });
                    }
                }, null, null);
                break;
            case R.id.btn_commit:
                // TODO:

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
                    imgLists.clear();
                    deleteImgLists.clear();
                    activity.onBackPressed();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            return true;
        }
    }

    @Override
    public void showCommitResult(boolean isSuccess) {

    }
}
