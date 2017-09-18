package com.kc.shiptransport.mvp.scannerimgselect;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.kc.shiptransport.view.PopupWindow.CommonUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import zlc.season.rxdownload2.RxDownload;

import static com.kc.shiptransport.R.id.btn_cancel;

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
    private CommonPopupWindow popupWindow;
    private RxDownload rxDownload;

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
        if (activity.p_type == 0 || activity.p_type == 2) {
            // 可以修改图片
            if (adapter == null) {
                adapter = new ScannerImgSelectAdapter(getContext(), scannerImgListByTypeBeen);
                adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, int... type) {
                        final ScannerImgListByTypeBean scannerImgListByTypeBean = adapter.list.get(position);
                        if (type[0] == 0) {
                            // TODO 显示图片
                            ImgViewPageActivity.startActivity(getContext(), (ArrayList<ScannerImgListByTypeBean>) adapter.list, position);
                        } else if (type[0] == 1) {
                            // 下载预览PDF
                            if (rxDownload == null) {
                                rxDownload = RxDownload.getInstance(getContext());
                            }
                            File[] files = rxDownload.getRealFiles(scannerImgListByTypeBean.getFilePath());
                            if (files != null) {
                                showPDF(scannerImgListByTypeBean.getFilePath());
                            } else {
                                presenter.downloadPDF(scannerImgListByTypeBean.getFilePath());
                            }

                        } else {

                            if (activity.isFinshReceptionSandAttachment == 0) {
                                String fileName = scannerImgListByTypeBean.getFileName();
                                String[] split = fileName.split("\\.");
                                if (split.length > 0 && split[1].equals("pdf")) {
                                    activity.showDailog("删除PDF", "是否删除PDF文件", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // 删除PDF
                                            presenter.deleteImg(scannerImgListByTypeBean.getItemID());
                                        }
                                    });
                                } else {
                                    activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // 删除图片
                                            presenter.deleteImg(scannerImgListByTypeBean.getItemID());
                                        }
                                    });
                                }
                            } else if (activity.isFinshReceptionSandAttachment == 1) {
                                Toast.makeText(getContext(), "验砂已完成, 不可删除", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        if (activity.isFinshReceptionSandAttachment == 0) {
                            if (popupWindow != null && popupWindow.isShowing())
                                return;
                            View upView = LayoutInflater.from(getContext()).inflate(R.layout.popup_scanner, null);
                            //测量View的宽高
                            CommonUtil.measureWidthAndHeight(upView);
                            popupWindow = new CommonPopupWindow.Builder(getContext())
                                    .setView(R.layout.popup_scanner)
                                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                                    .setBackGroundLevel(0.9f)//取值范围0.0f-1.0f 值越小越暗
                                    .setAnimationStyle(R.style.AnimFullUp)
                                    .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                                        @Override
                                        public void getChildView(View view, int layoutResId) {
                                            Button btnUploadImg = (Button) view.findViewById(R.id.btn_upload_img);
                                            Button btnUploadPdf = (Button) view.findViewById(R.id.btn_upload_pdf);
                                            Button btnCancel = (Button) view.findViewById(btn_cancel);

                                            /** 上传图片 */
                                            btnUploadImg.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (popupWindow != null) {
                                                        popupWindow.dismiss();
                                                    }

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

                                                }
                                            });

                                            /** 上传PDF */
                                            btnUploadPdf.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if (popupWindow != null) {
                                                        popupWindow.dismiss();
                                                    }

                                                    Intent intent4 = new Intent(getActivity(), NormalFilePickActivity.class);
                                                    intent4.putExtra(Constant.MAX_NUMBER, 8);
                                                    intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"pdf"});
                                                    startActivityForResult(intent4, Constant.REQUEST_CODE_PICK_FILE);
                                                }
                                            });

                                            /** 取消 */
                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (popupWindow != null) {
                                                        popupWindow.dismiss();
                                                    }
                                                }
                                            });

                                            view.setOnTouchListener(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View v, MotionEvent event) {
                                                    if (popupWindow != null) {
                                                        popupWindow.dismiss();
                                                    }
                                                    return true;
                                                }
                                            });
                                        }
                                    })
                                    .create();
                            popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);


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
    public void showCommitPDFResult(boolean isSuccess) {
        if (isSuccess) {
            // 全部上传成功的回调
            presenter.getImgList(activity.mSubID, activity.mTypeID);
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }

    /**
     * 查看PDF
     *
     * @param url
     */
    @Override
    public void showPDF(String url) {
        if (rxDownload == null) {
            rxDownload = RxDownload.getInstance(getContext());
        }
        //利用url获取
        File[] files = rxDownload.getRealFiles(url);
        if (files != null) {
            File file = files[0];

            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(file), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            Intent intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
                ToastUtil.tip(getContext(), "手机没有下载PDF查看软件, 请到应用市场下载");
            }
        } else {
            ToastUtil.tip(getContext(), "打开PDF文件失败, 请重试");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_FILE:
                if (resultCode == getActivity().RESULT_OK) {
                    ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                    StringBuilder builder = new StringBuilder();
                    for (NormalFile file : list) {
                        String path = file.getPath();
                        builder.append(path);
                    }
                    LogUtil.d("选择PDF: " + builder.toString());

                    /** 上传PDF */
                    presenter.commitPDF(builder.toString(), activity.mSubID, activity.mTypeID, activity.mShipAccount);
                }
                break;
        }
    }
}
