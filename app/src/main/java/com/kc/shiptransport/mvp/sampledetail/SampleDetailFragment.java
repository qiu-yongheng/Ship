package com.kc.shiptransport.mvp.sampledetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.sample.SampleData;
import com.kc.shiptransport.db.sample.SandSamplingNumRecordListBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.DatesListActivity;
import com.kc.shiptransport.view.actiivty.ImgViewPageActivity;
import com.kc.shiptransport.view.actiivty.InputActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
    private int itemID;
    private SandSample sandSample;
    private int isExit;
    private ArrayList<ImgList> imgLists = new ArrayList<>();
    private SampleData bean;
    private CommonAdapter<SandSamplingNumRecordListBean> sandAdapter;
    private String ConstructionBoatAccount = "";
    private String bacth = "";
    private String nqaa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // 根据position获取进场计划数据 设置相关数据
        presenter.start(activity.position);
        return view;
    }

    public void initListener() {
        /** 提交 */
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(bacth)) {
                    ToastUtil.tip(getContext(), "请填写BATCH");
                } else if (TextUtils.isEmpty(nqaa)) {
                    ToastUtil.tip(getContext(), "请填写NAQQ");
                } else {
                    // TODO 提交图片与总json
                    presenter.commit(sandSample);
                }
            }
        });

        /** 添加取样 */
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 添加数据, 取样编号自增
                if (TextUtils.isEmpty(bacth)) {
                    ToastUtil.tip(getContext(), "请先填写batch");
                    return;
                }

                List<SandSamplingNumRecordListBean> datas = sandAdapter.getDatas();
                SandSamplingNumRecordListBean numBean = new SandSamplingNumRecordListBean();
                numBean.setItemID(0);
                numBean.setSandSamplingID(bean.getItemID());
                numBean.setSamplingNum(bacth + String.valueOf((char) (datas.size() + 65)));
                numBean.setSandSamplingAttachmentRecordList(new ArrayList<SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>());
                numBean.save();
                datas.add(numBean);

                sandAdapter.setDates(datas);
                sandAdapter.notifyItemInserted(datas.size() - 1);
                recyclerview.smoothScrollToPosition(datas.size() - 1);
            }
        });

        /** BATCH */
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

        /** NQAA */
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
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);

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

    /**
     * 显示取样时间
     *
     * @param time
     */
    @Override
    public void showTime(String time) {
        tvTime.setText(time);
    }

    /**
     * 显示船舶名称
     *
     * @param name
     */
    @Override
    public void showShipName(String name) {
        textShipName.setText(name);
    }

    /**
     * 显示船舶编号
     *
     * @param num
     */
    @Override
    public void showShipNumber(String num) {
        textShipNum.setText(num);
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 保存进场计划对象
     *
     * @param sandSample
     */
    @Override
    public void showItemID(SandSample sandSample) {
        this.itemID = sandSample.getItemID();
        this.sandSample = sandSample;
        isExit = sandSample.getIsExit();

        // 获取要显示的数据
        presenter.getDates(itemID);
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

        SampleData sampleData = DataSupport.where("SubcontractorInterimApproachPlanID = ?", String.valueOf(itemID)).find(SampleData.class, true).get(0);
        if (requestCode == 100) {
            /** 设置batch */
            String bacth = data.getStringExtra(InputActivity.TAG);

            if (!TextUtils.isEmpty(bacth)) {
                this.bacth = bacth;
                textBatch.setText(bacth);
                List<SandSamplingNumRecordListBean> sandSamplingNumRecordList = sandAdapter.getDatas();
                // 保存数据
                sampleData.setBatch(bacth);
                sampleData.save();

                // 更新取样编号
                for (int i = 0; i < sandSamplingNumRecordList.size(); i++) {
                    // 数字转字符
                    char asdf = (char) (i + 65);
                    SandSamplingNumRecordListBean bean = sandSamplingNumRecordList.get(i);
                    bean.setSamplingNum(bacth + asdf);
                }

                // 保存数据
                DataSupport.saveAll(sandSamplingNumRecordList);

                sandAdapter.setDates(sandSamplingNumRecordList);
                sandAdapter.notifyDataSetChanged();

            }
        } else if (requestCode == 101) {
            /** 设置NQAA */
            nqaa = data.getStringExtra(InputActivity.TAG);

            if (!TextUtils.isEmpty(nqaa)) {
                textNaqq.setText(nqaa);
                sampleData.setNQAA(nqaa);
                sampleData.save();
            }
        } else {
            // 获取position对应的数据
            if (data != null) {
                SandSamplingNumRecordListBean listBean = sandAdapter.getDatas().get(requestCode);
                Bundle bundle = data.getExtras();
                String shipNum = bundle.getString(DatesListActivity.NUM);
                String shipName = bundle.getString(DatesListActivity.NAME);

                listBean.setConstructionBoatAccount(shipNum);
                listBean.setConstructionBoatAccountName(shipName);
                listBean.save();

                sandAdapter.notifyDataSetChanged();
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

    /**
     * 更新图片下载进度
     */
    @Override
    public void updateProgress() {
        activity.updataProgress(new OnProgressFinishListener() {
            @Override
            public void onFinish() {
                // 提交总json
                presenter.commitJson(itemID);
            }
        });
    }

    /**
     * 根据进场计划ID获取验砂取样信息明细
     *
     * @param bean
     */
    @Override
    public void showDetailList(final SampleData bean) {
        this.bean = bean;
        /** 判断是否退场, 如果已退场, 不能修改提交数据 */
        if (isExit == 1) {
            Toast.makeText(getContext(), "已进行退场申请, 不能修改验砂取样记录", Toast.LENGTH_SHORT).show();
            btnAdd.setVisibility(View.GONE);
            btnCommit.setVisibility(View.GONE);
            view.setVisibility(View.GONE);

            btnReturn.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(bean.getBatch())) {
            this.bacth = bean.getBatch();
        }
        // 回显batch数据
        textBatch.setText(bean.getBatch());
        // 回显nqaa数据
        textNaqq.setText(bean.getNQAA());

        // 获取取样编号的集合
        final List<SandSamplingNumRecordListBean> sandNumList = bean.getSandSamplingNumRecordList();

        /** 取样adapter */
        // 跳转到施工船舶选择界面
        sandAdapter = new CommonAdapter<SandSamplingNumRecordListBean>(getContext(), R.layout.item_sample, sandNumList) {
            @Override
            protected void convert(ViewHolder holder, final SandSamplingNumRecordListBean listBean, final int p_position) {
                holder.setText(R.id.tv_sample_num, listBean.getSamplingNum())
                        .setText(R.id.tv_cons_ship, listBean.getConstructionBoatAccountName());

                if (isExit != 1) {
                    holder.setOnClickListener(R.id.rl_cons_ship, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 跳转到施工船舶选择界面
                            DatesListActivity.startActivityForResult(getActivity(), p_position);
                        }
                    });

                    holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (isExit == 1) {
                                ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                                return true;
                            }
                            activity.showDailog("删除", "是否删除此取样编号?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除item
                                    if (listBean.getItemID() == 0) {
                                        listBean.delete();
                                        sandAdapter.getDatas().remove(p_position);
                                        sandAdapter.notifyDataSetChanged();
                                    } else {
                                        presenter.deleteNumForItemID(listBean.getItemID(), p_position);
                                    }
                                }
                            });
                            return true;
                        }
                    });

                    holder.setOnLongClickListener(R.id.rl_cons_ship, new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (isExit == 1) {
                                ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                                return true;
                            }
                            activity.showDailog("删除", "是否删除此取样编号?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除item
                                    if (listBean.getItemID() == 0) {
                                        listBean.delete();
                                        sandAdapter.getDatas().remove(p_position);
                                        sandAdapter.notifyDataSetChanged();
                                    } else {
                                        presenter.deleteNumForItemID(listBean.getItemID(), p_position);
                                    }
                                }
                            });
                            return true;
                        }
                    });

                    holder.setOnLongClickListener(R.id.recycler_view, new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            if (isExit == 1) {
                                ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                                return true;
                            }
                            activity.showDailog("删除", "是否删除此取样编号?", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除item
                                    if (listBean.getItemID() == 0) {
                                        listBean.delete();
                                        sandAdapter.getDatas().remove(p_position);
                                        sandAdapter.notifyDataSetChanged();
                                    } else {
                                        presenter.deleteNumForItemID(listBean.getItemID(), p_position);
                                    }
                                }
                            });
                            return true;
                        }
                    });

                } else {
                    ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                }

                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

                /** 图片adapter */
                final SampleImgAdapter imgAdapter = new SampleImgAdapter(getContext(), listBean.getSandSamplingAttachmentRecordList(), listBean);
                imgAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position, int... type) {
                        switch (type[0]) {
                            case 0:
                                /** 预览图片 */
                                imgLists.clear();
                                for (SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean listBean : imgAdapter.list) {
                                    ImgList imgList = new ImgList();
                                    imgList.setPath(listBean.getFilePath());
                                    imgLists.add(imgList);
                                }

                                ImgViewPageActivity.startActivity(getContext(), imgLists, position);
                                break;
                            case 1:
                                if (isExit == 1) {
                                    ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                                    return;
                                }
                                /** 删除图片 */
                                activity.showDailog("删除图片", "是否删除此图片?", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        presenter.deleteImgForItemID(imgAdapter.list.get(position).getItemID(), p_position, position);
                                    }
                                });
                                break;
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        /** 新增图片 */
                        // 弹出图片选择器
                        if (isExit == 1) {
                            ToastUtil.tip(getContext(), "已退场, 不能修改数据");
                            return;
                        }
                        if (TextUtils.isEmpty(bacth)) {
                            ToastUtil.tip(getContext(), "请先填写batch");
                            return;
                        }
                        int size = imgAdapter.list.size();
                        int max = 4 - size;
                        if (max > 0) {
                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 把图片解析成可以上传的任务, 上传
                                    presenter.commitImgList(imageMultipleResultEvent,
                                            bean.getSubcontractorInterimApproachPlanID(),
                                            imgAdapter.numBean.getItemID(),
                                            ConstructionBoatAccount,
                                            p_position);
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

                recyclerView.setAdapter(imgAdapter);
            }
        };

        recyclerview.setAdapter(sandAdapter);
    }

    /**
     * 提交成功后
     */
    @Override
    public void showImageUpdataResult() {
        // TODO: 提交成功后, 退回上一个界面, 废弃此方法
    }

    /**
     * 提交总json结果
     */
    @Override
    public void showCommitReturn() {
        getActivity().onBackPressed();
    }

    /**
     * 如果没有图片, 直接提交
     */
    @Override
    public void startCommit() {
        presenter.commitJson(itemID);
    }

    /**
     * 删除取样编号
     *
     * @param isSuccess
     */
    @Override
    public void showDeleteNumForItemID(boolean isSuccess, int p_position) {
        ToastUtil.tip(getContext(), isSuccess ? "删除成功" : "删除失败, 请重试");
        if (isSuccess && bean != null && sandAdapter != null) {
            bean.getSandSamplingNumRecordList().remove(p_position);
            sandAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除图片
     *
     * @param isSuccess
     * @param p_position
     * @param position
     */
    @Override
    public void showDeleteImgResult(boolean isSuccess, int p_position, int position) {
        ToastUtil.tip(getContext(), isSuccess ? "删除成功" : "删除失败, 请重试");
        if (isSuccess && bean != null && sandAdapter != null) {
            bean.getSandSamplingNumRecordList().get(p_position).getSandSamplingAttachmentRecordList().remove(position);
            sandAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当界面不显示时, 缓存数据到sp中
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
