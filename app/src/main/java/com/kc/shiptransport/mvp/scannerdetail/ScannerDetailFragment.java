package com.kc.shiptransport.mvp.scannerdetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.scannerimgselect.ScannerImgSelectActivity;
import com.kc.shiptransport.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public class ScannerDetailFragment extends Fragment implements ScannerDetailContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_already_commit)
    Button btnAlreadyCommit;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private ScannerDetailContract.Presenter presenter;
    private ScannerDetailActivity activity;
    private Unbinder unbinder;
    private ScannerDetailAdapter adapter;
    private WeekTask weekTask;
    private int isSumbittedPerfectBoatScanner = 0;
    private List<ScannerListBean> bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 回显数据(从网络获取)
        presenter.start(activity.position, activity.type);
        return view;
    }

    public void initListener() {
        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showDailog("提交", getText(R.string.commit_tip).toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userID = DataSupport.findAll(User.class).get(0).getUserID();
                        int itemID = 0;
                        if (weekTask == null) {
                            itemID = activity.position;
                        } else {
                            itemID = weekTask.getItemID();
                        }
                        presenter.commit("", userID, itemID, 1, "");
                    }
                });
            }
        });

        /** 已提交, 返回 */
        btnAlreadyCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        /** 查看退回意见 */
        tvOpinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remark = "";
                if (bean != null && activity != null) {
                    remark = TextUtils.isEmpty(bean.get(0).getPreAcceptanceEvaluationRemark()) ? "暂无退回意见" : bean.get(0).getPreAcceptanceEvaluationRemark();
                }

                activity.showDailog("退回意见", remark, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
            }
        });

    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ScannerDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(ScannerDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示标题
     *
     * @param weekTask
     */
    @Override
    public void showTitle(WeekTask weekTask) {
        this.weekTask = weekTask;
        tvTitle.setText(weekTask.getShipName());

        /** 如果已提交, 不能修改数据 */
        isSumbittedPerfectBoatScanner = weekTask.getIsSumbittedPerfectBoatScanner();

        if (isSumbittedPerfectBoatScanner == 1) {
            /** 已提交 */
            btnAlreadyCommit.setVisibility(View.VISIBLE);
            btnCommit.setVisibility(View.GONE);
        } else {
            /** 未提交 */
            btnAlreadyCommit.setVisibility(View.GONE);
            btnCommit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog(getResources().getString(R.string.dialog_loading), getResources().getString(R.string.dialog_loading), new OnDailogCancleClickListener() {
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

    /**
     * 获取到扫描件类型
     *
     * @param scannerImage
     */
    @Override
    public void showDatas(List<ScannerListBean> scannerImage) {
        if (scannerImage.isEmpty()) {
            ToastUtil.tip(getContext(), "获取数据失败");
            return;
        }
        this.bean = scannerImage;

        if (scannerImage.get(0).getPreAcceptanceEvaluationStatus() == -1) {
            tvOpinion.setVisibility(View.VISIBLE);
        } else {
            tvOpinion.setVisibility(View.GONE);
        }

        if (adapter == null) {

            adapter = new ScannerDetailAdapter(getContext(), scannerImage);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    // 跳转到图片选择界面, 传递条目ID, 进场ID
                    ScannerListBean bean = adapter.list.get(position);
                    ScannerImgSelectActivity.startActivity(getContext(),
                            bean.getSubcontractorPerfectBoatScannerAttachmentTypeID(),
                            bean.getSubcontractorInterimApproachPlanID(),
                            bean.getSubcontractorPerfectBoatScannerAttachmentTypeName(),
                            bean.getConstructionBoatAccount(),
                            bean.getAttachmentCount(),
                            bean.getDefalutAttachmentCount(),
                            activity.type,
                            isSumbittedPerfectBoatScanner);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDates(scannerImage);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCommitResult(boolean isSuccess) {
        if (isSuccess) {
            ToastUtil.tip(getContext(), "提交成功!");
            getActivity().onBackPressed();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO
        if (adapter != null) {
            presenter.getScannerType(activity.position, activity.type);
        }
    }
}
