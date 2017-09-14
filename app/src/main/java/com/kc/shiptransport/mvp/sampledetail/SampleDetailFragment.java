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
import android.widget.LinearLayout;
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
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.DatesListActivity;
import com.kc.shiptransport.view.actiivty.InputActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.ModelUtils;

/**
 * @author qiuyongheng
 * @time 2017/6/14  10:59
 * @desc ${TODD}
 */

public class SampleDetailFragment extends Fragment implements SampleDetailContract.View {
    Unbinder unbinder;
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
    @BindView(R.id.text_batch)
    TextView textBatch;
    @BindView(R.id.rl_bacth)
    RelativeLayout rlBacth;
    @BindView(R.id.text_naqq)
    TextView textNaqq;
    @BindView(R.id.rl_nqaa)
    RelativeLayout rlNqaa;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private SampleDetailContract.Presenter presenter;
    private SampleDetailActivity activity;
    private SampleDetailAdapter mAdapter;
    private int itemID;
    private SandSample sandSample;
    private int isExit;
    private CommonAdapter<SampleShowDatesBean.SandSamplingNumRecordListBean> commonAdapter;
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

    public void initListener() {
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
                // 添加数据, 取样编号自增
                mAdapter.addData(mAdapter.sandSamplingNumRecordList.size());
                recyclerview.smoothScrollToPosition(mAdapter.sandSamplingNumRecordList.size());
            }
        });

        // BATCH
        rlBacth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExit == 1) {
                    ToastUtil.tip(getContext(), "不能修改数据");
                } else {
                    InputActivity.startActivityForResult(getActivity(), "BATCH", "", SettingUtil.TYPE_TEXT, 100);
                }
            }
        });

        // NQAA
        rlNqaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExit == 1) {
                    ToastUtil.tip(getContext(), "不能修改数据");
                } else {
                    InputActivity.startActivityForResult(getActivity(), "NQAA", "", SettingUtil.TYPE_TEXT, 101);
                }
            }
        });

        /** 返回 */
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
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
        isExit = sandSample.getIsExit();

        // 获取要显示的数据
        presenter.getDates(activity.position, sandSample.getIsSandSampling() == 1, sandSample.getIsExit() == 1);
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

        if (requestCode == 100) {
            // 设置batch
            String bacth = data.getStringExtra(InputActivity.TAG);

            if (!TextUtils.isEmpty(bacth)) {
                textBatch.setText(bacth);
                List<SampleShowDatesBean.SandSamplingNumRecordListBean> sandSamplingNumRecordList = mAdapter.sandSamplingNumRecordList;
                // 保存数据
                mAdapter.sampleShowDates.setBatch(bacth);

                // 更新取样编号
                for (int i = 0; i < sandSamplingNumRecordList.size(); i++) {
                    // 数字转字符
                    char asdf = (char) (i + 65);
                    SampleShowDatesBean.SandSamplingNumRecordListBean bean = sandSamplingNumRecordList.get(i);
                    bean.setSamplingNum(bacth + asdf);
                }

                mAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 101) {
            // 设置NQAA
            String nqaa = data.getStringExtra(InputActivity.TAG);

            if (!TextUtils.isEmpty(nqaa)) {
                textNaqq.setText(nqaa);
                mAdapter.sampleShowDates.setNQAA(nqaa);

                mAdapter.notifyDataSetChanged();
            }
        } else {
            // 获取position对应的数据
            if (data != null) {
                SampleShowDatesBean.SandSamplingNumRecordListBean listBean = mAdapter.sandSamplingNumRecordList.get(requestCode);
                Bundle bundle = data.getExtras();
                String shipNum = bundle.getString(DatesListActivity.NUM);
                String shipName = bundle.getString(DatesListActivity.NAME);

                listBean.setConstructionBoatAccount(shipNum);
                listBean.setConstructionBoatAccountName(shipName);

                mAdapter.notifyDataSetChanged();
            }

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
                String string = SharePreferenceUtil.getString(getContext(), String.valueOf(itemID), "");
                SampleShowDatesBean showDatesBean = new Gson().fromJson(string, SampleShowDatesBean.class);
                presenter.commitJson(showDatesBean);
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
        /** 判断是否退场, 如果已退场, 不能修改提交数据 */
        if (isExit == 1) {
            Toast.makeText(getContext(), "已进行退场申请, 不能修改验砂取样记录", Toast.LENGTH_SHORT).show();
            btnAdd.setVisibility(View.GONE);
            btnCommit.setVisibility(View.GONE);
            view.setVisibility(View.GONE);

            btnReturn.setVisibility(View.VISIBLE);
        }


        // 回显batch数据
        textBatch.setText(bean.getBatch());
        // 回显nqaa数据
        textNaqq.setText(bean.getNQAA());

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
                SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageBean3 = new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();
                SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imageBean4 = new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean();

                // 保存两张图片
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean1);
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean2);
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean3);
                numRecordListBean.getSandSamplingAttachmentRecordList().add(imageBean4);

                // 保存到集合中
                bean.getSandSamplingNumRecordList().add(numRecordListBean);
            }
        } else {
            // 不做任何操作
        }


        /** TODO 重写adapter */
        //        commonAdapter = new CommonAdapter<SampleShowDatesBean.SandSamplingNumRecordListBean>(getContext(), R.layout.item_sample, bean.getSandSamplingNumRecordList()) {
        //            @Override
        //            protected void convert(ViewHolder holder, SampleShowDatesBean.SandSamplingNumRecordListBean listBean, final int position) {
        //                holder.setText(R.id.tv_sample_num, listBean.getSamplingNum())
        //                        .setText(R.id.tv_cons_ship, listBean.getConstructionBoatAccountName())
        //                        .setOnClickListener(R.id.rl_cons_ship, new View.OnClickListener() {
        //                            @Override
        //                            public void onClick(View view) {
        //                                // 跳转到施工船舶选择界面
        //                                DatesListActivity.startActivityForResult(getActivity(), position);
        //                            }
        //                        });
        //
        //                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
        //                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        //
        //                // 图片列表
        //                List<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> imgList = listBean.getSandSamplingAttachmentRecordList();
        //
        //                final SampleImgAdapter imgAdapter = new SampleImgAdapter(getContext(), imgList);
        //                imgAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
        //                    @Override
        //                    public void onItemClick(View view, int position, int... type) {
        //                        SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean imgBean = imgAdapter.list.get(position);
        //                        if (type[0] == 0) {
        //                            // 预览
        //                            ImageActivity.startActivity(getContext(), imgBean.getFilePath());
        //                        } else {
        //                            // 删除
        //                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
        //                                @Override
        //                                public void onClick(DialogInterface dialogInterface, int i) {
        //                                    // TODO 删除
        //                                }
        //                            });
        //                        }
        //                    }
        //
        //                    @Override
        //                    public void onItemLongClick(View view, int position) {
        //                        // 弹出图片选择器
        //                        int size = imgAdapter.list.size();
        //                        int max = 4 - size;
        //                        if (max > 0) {
        //                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
        //                                @Override
        //                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
        //                                    // TODO 把图片解析成可以上传的任务, 上传
        //                                }
        //
        //                                @Override
        //                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {
        //
        //                                }
        //                            });
        //                        } else {
        //                            Toast.makeText(getContext(), "已到达图片选择上限", Toast.LENGTH_SHORT).show();
        //                        }
        //                    }
        //                });
        //
        //                recyclerView.setAdapter(imgAdapter);
        //            }
        //        };
        //
        //        recyclerview.setAdapter(commonAdapter);


        // 设置adapter
        if (mAdapter == null) {
            mAdapter = new SampleDetailAdapter(getContext(), bean);
            /** 如果已退场, 不处理点击事件 */
            if (isExit == 1) {

            } else {
                mAdapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position, final int... type) {
                        // 获取position对应数据
                        final SampleShowDatesBean.SandSamplingNumRecordListBean numRecordListBean = mAdapter.sandSamplingNumRecordList.get(position);

                        final List<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> imageList = numRecordListBean.getSandSamplingAttachmentRecordList();

                        if (imageList.isEmpty()) {
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                        } else if (imageList.size() == 1) {
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                        } else if (imageList.size() == 2) {
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                            imageList.add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
                        } else if (imageList.size() == 3) {
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
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        } else {
                                            // 有缓存数据, 修改
                                            SampleImageList list = sampleImageLists.get(0);
                                            list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

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
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        } else {
                                            // 有缓存数据, 修改
                                            SampleImageList list = sampleImageLists.get(0);
                                            list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                    }
                                });
                                break;
                            case SettingUtil.HOLDER_IMAGE_3:
                                RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                    @Override
                                    public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                        // 保存图片地址
                                        imageList.get(2).setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                                        // 图片名
                                        String title = imageMultipleResultEvent.getResult().get(0).getTitle();

                                        // 图片类型
                                        String mimeType = imageMultipleResultEvent.getResult().get(0).getMimeType();
                                        String[] split = mimeType.split("/");
                                        String suffixName = split[split.length - 1];

                                        imageList.get(2).setFileName(title + "." + suffixName);


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
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        } else {
                                            // 有缓存数据, 修改
                                            SampleImageList list = sampleImageLists.get(0);
                                            list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                    }
                                });
                                break;
                            case SettingUtil.HOLDER_IMAGE_4:
                                RxGalleryUtil.getImagRadio(getActivity(), new OnRxGalleryRadioListener() {
                                    @Override
                                    public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                        // 保存图片地址
                                        imageList.get(3).setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());

                                        // 图片名
                                        String title = imageMultipleResultEvent.getResult().get(0).getTitle();

                                        // 图片类型
                                        String mimeType = imageMultipleResultEvent.getResult().get(0).getMimeType();
                                        String[] split = mimeType.split("/");
                                        String suffixName = split[split.length - 1];

                                        imageList.get(3).setFileName(title + "." + suffixName);


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
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        } else {
                                            // 有缓存数据, 修改
                                            SampleImageList list = sampleImageLists.get(0);
                                            list.setFilePath(imageMultipleResultEvent.getResult().get(0).getOriginalPath());
                                            list.setConstructionBoatAccount(numRecordListBean.getConstructionBoatAccount());
                                            list.save();
                                        }

                                        mAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                    }
                                });
                                break;
                            case SettingUtil.HOLDER_CONS_SHIP:
                                // 跳转到施工船舶选择界面
                                DatesListActivity.startActivityForResult(getActivity(), position);
                                break;
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, final int position) {
                        activity.showDailog("删除", "是否删除该数据", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SampleShowDatesBean.SandSamplingNumRecordListBean numRecordListBean = mAdapter.sandSamplingNumRecordList.get(position);
                                if (mAdapter.sandSamplingNumRecordList.size() <= 3) {
                                    Toast.makeText(getContext(), "取样次数不能少于3次", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 发送网络请求, 删除item
                                    presenter.deleteItem(numRecordListBean.getItemID());
                                    mAdapter.delete(position);
                                }
                            }
                        });
                    }
                });
            }

            recyclerview.setAdapter(mAdapter);

        } else {
            mAdapter.setDates(bean);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 提交成功后
     */
    @Override
    public void showImageUpdataResult() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDeleteResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @Override
    public void showCommitReturn() {
        getActivity().onBackPressed();
    }

    @Override
    public void startCommit() {
        String string = SharePreferenceUtil.getString(getContext(), String.valueOf(itemID), "");
        SampleShowDatesBean showDatesBean = new Gson().fromJson(string, SampleShowDatesBean.class);
        presenter.commitJson(showDatesBean);
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

    @Override
    public void onResume() {
        super.onResume();
    }
}
