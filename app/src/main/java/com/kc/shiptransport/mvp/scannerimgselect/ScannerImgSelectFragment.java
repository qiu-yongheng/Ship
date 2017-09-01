package com.kc.shiptransport.mvp.scannerimgselect;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author 邱永恒
 * @time 2017/7/1 11:18
 * @desc ${TODO}
 */

public class ScannerImgSelectFragment extends Fragment implements ScannerImgSelectContract.View {
    @BindView(R.id.btn_determine)
    Button mBtnDetermine;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private ScannerImgSelectActivity activity;
    private ScannerImgSelectContract.Presenter presenter;
    private ScannerImgSelectAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_select, container, false);
        ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO 获取要显示的图片列表
        presenter.getImgList(activity.mSubID, activity.mTypeID);
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (ScannerImgSelectActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(activity.mTitle);

        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(ScannerImgSelectContract.Presenter presenter) {
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showImgList(List<ScannerImgListByTypeBean> scannerImgListByTypeBeen) {
        if (activity.p_type == 0) {
            // 可以修改图片
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

                            if (activity.isFinshReceptionSandAttachment == 0) {
                                activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 删除图片
                                        presenter.deleteImg(scannerImgListByTypeBean.getItemID());
                                    }
                                });
                            } else if (activity.isFinshReceptionSandAttachment == 1) {
                                Toast.makeText(getContext(), "验砂已完成, 不可删除", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        if (activity.isFinshReceptionSandAttachment == 0) {
                            // 弹出图片选择器, 设置多选图片张数
//                            int maxSize = activity.mDefaulAttachmentCount - adapter.list.size();
                            int maxSize = SettingUtil.NUM_IMAGE_SELECTION - adapter.list.size();
                            if (maxSize > 0) {
                                RxGalleryUtil.getImagMultiple(getContext(), maxSize, new OnRxGalleryRadioListener() {
                                    @Override
                                    public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                        // 提交图片
                                        presenter.commit(imageMultipleResultEvent, activity.mSubID, activity.mTypeID, activity.mShipAccount);
                                    }

                                    @Override
                                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
                                        // 单选回调
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "图片张数已到达上限", Toast.LENGTH_SHORT).show();
                            }
                        } else if (activity.isFinshReceptionSandAttachment == 1) {
                            Toast.makeText(getContext(), "已提交, 不能添加图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mRecyclerview.setAdapter(adapter);
            } else {
                adapter.setDates(scannerImgListByTypeBeen);
                adapter.notifyDataSetChanged();
            }

        } else if (activity.p_type == 1) {
            if (adapter == null) {
                adapter = new ScannerImgSelectAdapter(getContext(), scannerImgListByTypeBeen);
                adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int... type) {
                        ScannerImgListByTypeBean scannerImgListByTypeBean = adapter.list.get(position);
                        if (type[0] == 0) {
                            // 显示图片
                            ImageActivity.startActivity(getContext(), scannerImgListByTypeBean.getFilePath());
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                mRecyclerview.setAdapter(adapter);
            } else {
                adapter.setDates(scannerImgListByTypeBeen);
                adapter.notifyDataSetChanged();
            }
        }


    }

    /**
     * 进度条
     *
     * @param max
     */
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
                // 全部上传成功的回调
                presenter.getImgList(activity.mSubID, activity.mTypeID);
            }
        });
    }

    @Override
    public void hideProgress() {
        activity.hideProgress();
    }

    @Override
    public void showDeleteResult(boolean isSuccess) {
        if (isSuccess) {
            presenter.getImgList(activity.mSubID, activity.mTypeID);
        } else {
            Toast.makeText(getContext(), "删除图片失败, 请重试", Toast.LENGTH_SHORT).show();
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
}
