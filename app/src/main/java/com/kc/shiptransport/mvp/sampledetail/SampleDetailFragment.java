package com.kc.shiptransport.mvp.sampledetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.view.actiivty.InputActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

/**
 * @author qiuyongheng
 * @time 2017/6/14  10:59
 * @desc ${TODD}
 */

public class SampleDetailFragment extends Fragment implements SampleDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.text_ship_name)
    TextView textShipName;
    @BindView(R.id.text_ship_num)
    TextView textShipNum;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_add)
    Button btnAdd;
    Unbinder unbinder;
    private SampleDetailContract.Presenter presenter;
    private SampleDetailActivity activity;
    private SampleDetailAdapter mAdapter;
    private int itemID;
    private SandSample sandSample;
    //private SampleShowDatesBean sampleShowDates;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO 获取进场任务, 调用查看图片接口
        presenter.start(activity.position);
        return view;
    }

    private void initListener() {
        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 保存数据到sp
                saveImageList();

                presenter.commit(sandSample);
            }
        });

        // 添加取样
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.addData(mAdapter.sandSamplingNumRecordList.size());
                recyclerview.smoothScrollToPosition(mAdapter.sandSamplingNumRecordList.size());
            }
        });
    }

    @Override
    public void initViews(View view) {
        //手动打开日志。
        ModelUtils.setDebugModel(true);


        setHasOptionsMenu(true);
        // 初始化toolbar
        activity = (SampleDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(R.string.sampledetail_title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 初始化recyclerview
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
    public void setPresenter(SampleDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTime(String time) {
        tvTime.setText(time);
    }

    @Override
    public void showShipName(String name) {
        textShipName.setText(name);
    }

    @Override
    public void showShipNumber(String num) {
        textShipNum.setText(num);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showItemID(SandSample sandSample) {
        this.itemID = sandSample.getItemID();
        this.sandSample = sandSample;

        // 获取要显示的数据
        presenter.getDates(activity.position);
    }

    /**
     * 获取返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = data.getStringExtra(InputActivity.TAG);
        if (!TextUtils.isEmpty(str)) {
            SampleShowDatesBean.SandSamplingNumRecordListBean bean = mAdapter.sandSamplingNumRecordList.get(requestCode);
            bean.setSamplingNum(str);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("提交中", "提交中...", new OnDailogCancleClickListener() {
                @Override
                public void onCancle(ProgressDialog dialog) {
                    presenter.unsubscribe();
                }
            });
        } else {
            activity.hideProgressDailog();
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
                // 提交json
                presenter.commitJson(mAdapter.sampleShowDates);
            }
        });
    }

    /**
     * 显示详细列表
     *
     * @param bean
     */
    @Override
    public void showDetailList(SampleShowDatesBean bean) {
        //this.sampleShowDates = bean;

        // 获取取样编号的集合
        List<SampleShowDatesBean.SandSamplingNumRecordListBean> sandSamplingNumRecordList = bean.getSandSamplingNumRecordList();

        // 判断如果取样数据小于3个, 手动添加至3个
        int size = sandSamplingNumRecordList.size();
        if (size < 3) {
            for (int i = size; i < 3; i++) {
                // 新建一个空的取样编号数据
                SampleShowDatesBean.SandSamplingNumRecordListBean numRecordListBean = new SampleShowDatesBean.SandSamplingNumRecordListBean();

                numRecordListBean.setSandSamplingAttachmentRecordList(new ArrayList<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>());

                SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageBean1 = new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();
                SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageBean2 = new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();

                // 保存两张图片
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean1);
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean2);

                // 保存到集合中
                bean.getSandSamplingNumRecordList().add(numRecordListBean);
            }
        } else {
            // 不做任何操作
        }

        // 设置adapter
        if (mAdapter == null) {
            mAdapter = new SampleDetailAdapter(getContext(), bean);
            mAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, final int position, final int... type) {
                    // 获取position对应数据
                    SampleShowDatesBean.SandSamplingNumRecordListBean numRecordListBean = mAdapter.sandSamplingNumRecordList.get(position);

                    final List<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> imageList = numRecordListBean.getSandSamplingAttachmentRecordList();

                    if (imageList.isEmpty()) {
                        imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                        imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                    } else if (imageList.size() == 1) {
                        imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                    }

                    switch (type[0]) {
                        case SettingUtil.HOLDER_IMAGE_1:
                            RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {

                                    // 保存图片地址
                                    imageList.get(0).setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                                    // 图片名
                                    String title = imageMultipleResultEvent.getResult().get(0).getTitle();

                                    // 图片类型
                                    String mimeType = imageMultipleResultEvent.getResult().get(0).getMimeType();
                                    String[] split = mimeType.split("/");
                                    String suffixName = split[split.length - 1];

                                    imageList.get(0).setFileName(title + "." + suffixName);


                                    // 保存图片到列表中, 等待提交
                                    List<SampleImageList> sampleImageLists = DataSupport
                                            .where("itemID = ? and position = ? and img_x = ?", String.valueOf(itemID), String.valueOf(position), String.valueOf(type[0]))
                                            .find(SampleImageList.class);

                                    if (sampleImageLists.isEmpty()) {
                                        // 数据库没有, 保存到数据库
                                        SampleImageList list = new SampleImageList();
                                        list.setItemID(itemID);
                                        list.setPosition(position);
                                        list.setImg_x(type[0]);
                                        list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                        list.setFileName(title + "." + suffixName);
                                        list.setSuffixName(suffixName);
                                        list.save();
                                    } else {
                                        // 有缓存数据, 修改
                                        SampleImageList list = sampleImageLists.get(0);
                                        list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                        list.save();
                                    }

                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        case SettingUtil.HOLDER_IMAGE_2:
                            RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 保存图片地址
                                    imageList.get(1).setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                                    // 图片名
                                    String title = imageMultipleResultEvent.getResult().get(0).getTitle();

                                    // 图片类型
                                    String mimeType = imageMultipleResultEvent.getResult().get(0).getMimeType();
                                    String[] split = mimeType.split("/");
                                    String suffixName = split[split.length - 1];

                                    imageList.get(1).setFileName(title + "." + suffixName);


                                    // 保存图片到列表中, 等待提交
                                    List<SampleImageList> sampleImageLists = DataSupport
                                            .where("itemID = ? and position = ? and img_x = ?", String.valueOf(itemID), String.valueOf(position), String.valueOf(type[0]))
                                            .find(SampleImageList.class);

                                    if (sampleImageLists.isEmpty()) {
                                        // 数据库没有, 保存到数据库
                                        SampleImageList list = new SampleImageList();
                                        list.setItemID(itemID);
                                        list.setPosition(position);
                                        list.setImg_x(type[0]);
                                        list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                        list.setFileName(title + "." + suffixName);
                                        list.setSuffixName(suffixName);
                                        list.save();
                                    } else {
                                        // 有缓存数据, 修改
                                        SampleImageList list = sampleImageLists.get(0);
                                        list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                        list.save();
                                    }

                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                            break;
                        case SettingUtil.HOLDER_NUM:
                            InputActivity.startActivityForResult(getActivity(), getResources().getString(R.string.title_sample), "", position);
                            break;
                    }
                }

                @Override
                public void onItemLongClick(View view, final int position) {
                    activity.showDailog("删除", "是否删除该数据", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (mAdapter.sandSamplingNumRecordList.size() <= 3) {
                                Toast.makeText(getContext(), "取样次数不能少于3次", Toast.LENGTH_SHORT).show();
                            } else {
                                mAdapter.delete(position);
                            }
                        }
                    });
                }
            });

            recyclerview.setAdapter(mAdapter);

        } else {
            mAdapter.setDates(bean);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 提交成功后, 删除缓存
     */
    @Override
    public void showImageUpdataResult() {
        // 成功后, 删除缓存
        SharePreferenceUtil.saveString(getContext(), String.valueOf(itemID), "");

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 当界面不显示时, 缓存数据到sp中
     */
    @Override
    public void onPause() {
        super.onPause();
        saveImageList();
    }

    /**
     * 保存图片数据
     */
    private void saveImageList() {
        // 缓存数据
        if (mAdapter != null) {
            String json = new Gson().toJson(mAdapter.sampleShowDates);
            SharePreferenceUtil.saveString(getContext(), String.valueOf(itemID), json);
        }
    }
}
