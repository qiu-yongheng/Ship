package com.kc.shiptransport.mvp.scannerdetail;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.scannerimgselect.ScannerImgSelectActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public class ScannerDetailFragment extends Fragment implements ScannerDetailContract.View{


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ScannerDetailContract.Presenter presenter;
    private ScannerDetailActivity activity;
    public ScannerImage scannerImage;
    private Unbinder unbinder;
    private ScannerDetailAdapter adapter;
    private WeekTask weekTask;
    private int isFinshReceptionSandAttachment = 1;

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

    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ScannerDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));

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
        // 是否完善 (如果已验砂, 不能修改)
        isFinshReceptionSandAttachment = TextUtils.isEmpty(weekTask.getPreAcceptanceTime()) ? 0 : 1;
        activity.getSupportActionBar().setTitle(weekTask.getShipName());
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
                            isFinshReceptionSandAttachment);
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(scannerImage);
            adapter.notifyDataSetChanged();
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
