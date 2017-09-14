package com.kc.shiptransport.mvp.supplydetail;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/1  11:18
 * @desc ${TODD}
 */

public class SupplyDetailFragemnt extends Fragment implements SupplyDetailContract.View {


    @BindView(R.id.toolbar_supply_detail)
    Toolbar toolbarSupplyDetail;
    @BindView(R.id.tv)
    TextView tvShipName;
    @BindView(R.id.tv_ship_id)
    TextView tvShipId;
    @BindView(R.id.tv_subontractor)
    TextView tvSubontractor;
    @BindView(R.id.tv_total_voyage)
    TextView tvTotalVoyage;
    @BindView(R.id.tv_total_value)
    TextView tvTotalValue;
    @BindView(R.id.tv_avg_value)
    TextView tvAvgValue;
    @BindView(R.id.ll_supply_detail)
    LinearLayout llSupplyDetail;
    @BindView(R.id.et_ship_volume)
    EditText etShipVolume;
    @BindView(R.id.et_deck_volume)
    EditText etDeckVolume;
    @BindView(R.id.tv_total_volume)
    TextView tvTotalVolume;
    @BindView(R.id.tv_supply_time)
    TextView tvSupplyTime;
    @BindView(R.id.et_batch)
    EditText etBatch;
    @BindView(R.id.btn_acceptance_cancel)
    AppCompatButton btnAcceptanceCancel;
    @BindView(R.id.btn_acceptance_commit)
    AppCompatButton btnAcceptanceCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.recycler_view_ship)
    RecyclerView recyclerViewShip;
    @BindView(R.id.btn_acceptance_return)
    AppCompatButton btnAcceptanceReturn;
    @BindView(R.id.et_remark)
    EditText etRemark;
    private Unbinder unbinder;
    private SupplyDetailContract.Presenter presenter;
    private SupplyDetailActivity activity;
    private SupplyDetailAdapter adapter;
    private SupplyDetailNameAdapter nameAdapter;
    private int FULLY_PHOTO = 0;
    private int NAME_PHOTO = 1;
    private SupplyDetail value;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supply_detail, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViews(view);
        initListener();
        // TODO
        presenter.start(activity.itemID);
        return view;
    }

    @Override
    public void initViews(View view) {
        activity = (SupplyDetailActivity) getActivity();
        // 可以显示menu
        setHasOptionsMenu(true);
        activity.setSupportActionBar(toolbarSupplyDetail);
        activity.getSupportActionBar().setTitle("验砂管理");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (activity.isSupply) {
            // 禁止软键盘
            etShipVolume.setInputType(InputType.TYPE_NULL);
            etDeckVolume.setInputType(InputType.TYPE_NULL);
            etBatch.setInputType(InputType.TYPE_NULL);
            btnAcceptanceCommit.setVisibility(View.GONE);
            btnAcceptanceCancel.setVisibility(View.GONE);
            btnAcceptanceReturn.setVisibility(View.VISIBLE);

            etRemark.setFocusableInTouchMode(false);
            etRemark.setFocusable(false);

        } else {
            // 开启软键盘
            etShipVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etDeckVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etBatch.setInputType(InputType.TYPE_CLASS_TEXT);
            btnAcceptanceCommit.setVisibility(View.VISIBLE);
            btnAcceptanceCancel.setVisibility(View.VISIBLE);
            btnAcceptanceReturn.setVisibility(View.GONE);

            etRemark.setFocusableInTouchMode(true);
            etRemark.setFocusable(true);

            /* 点击弹出时间选择器 */
            tvSupplyTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        CalendarUtil.showTimePickerDialog(getContext(), tvSupplyTime, new OnTimePickerSureClickListener() {
                            @Override
                            public void onSure(String str) {

                            }
                        }, false);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerViewShip.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    public void initListener() {
        /* 通过 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(tvSupplyTime.getText().toString())) {
                    if (value != null &&
                            value.getReceptionSandAttachmentList() != null &&
                            value.getReceptionSandBoatNameAttachmentList() != null &&
                            !value.getReceptionSandBoatNameAttachmentList().isEmpty() &&
                            !value.getReceptionSandAttachmentList().isEmpty()) {

                        String userID = DataSupport.findAll(User.class).get(0).getUserID();
                        int status = 1;
                        String remark = "";
                        presenter.commit(activity.itemID, tvSupplyTime.getText().toString(), userID, status, remark);
                    } else {
                        ToastUtil.tip(getContext(), "满载照片与船名照片上传后才能通过");
                    }

                } else {
                    ToastUtil.tip(getContext(), "请选择时间");
                }
            }
        });

        /* 不通过 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(tvSupplyTime.getText().toString())) {
                    activity.showDailog("不通过", "确定验砂不通过吗?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String userID = DataSupport.findAll(User.class).get(0).getUserID();
                            int status = -1;
                            String remark = "";
                            presenter.commit(activity.itemID, tvSupplyTime.getText().toString(), userID, status, remark);
                        }
                    });
                } else {
                    ToastUtil.tip(getContext(), "请选择时间");
                }
            }
        });

        /* 返回 */
        btnAcceptanceReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        /* edittext输入监听 */
        etShipVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ship = etShipVolume.getText().toString();
                String deck = etDeckVolume.getText().toString();

                presenter.getTotalVolume(ship, deck);
            }
        });

        etDeckVolume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ship = etShipVolume.getText().toString();
                String deck = etDeckVolume.getText().toString();
                presenter.getTotalVolume(ship, deck);
            }
        });


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
    public void setPresenter(SupplyDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示选中任务的详细信息
     *
     * @param value
     */
    @Override
    public void showShipDetail(final SupplyDetail value) {
        this.value = value;
        // 条目ID
        int itemID = TextUtils.isEmpty(value.getItemID()) ? 0 : Integer.valueOf(value.getItemID());
        // 进场ID
        final int subID = TextUtils.isEmpty(value.getSubcontractorInterimApproachPlanID()) ? 0 : Integer.valueOf(value.getSubcontractorInterimApproachPlanID());

        // 船舶账号
        String shipAccount = value.getShipAccount();
        // 船舶名称
        String shipName = value.getShipName();
        // 分包商账号
        String subcontractorAccount = value.getSubcontractorAccount();
        // 分包商名称
        String subcontractorName = value.getSubcontractorName();
        // 船次编号
        String shipItemNum = value.getShipItemNum();
        // 验砂时间
        String receptionSandTime = value.getReceptionSandTime();
        // 系统提交时间
        String systemDate = value.getSystemDate();
        // 累计完成方量
        String totalCompleteRide = value.getTotalCompleteRide();
        // 累计完成方量
        String totalCompleteSquare = value.getTotalCompleteSquare();
        // 平均航次方量
        String avgSquare = value.getAvgSquare();


        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);

        tvShipId.setText("船次: " + (TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum));
        tvSubontractor.setText("供应商: " + (TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName));
        tvTotalVoyage.setText("累计完成航次: " + (TextUtils.isEmpty(totalCompleteRide) ? "0" : totalCompleteRide) + "次");
        tvTotalValue.setText("累计完成方量: " + (TextUtils.isEmpty(totalCompleteSquare) ? "0" : totalCompleteSquare) + "㎡");
        tvAvgValue.setText("平均航次方量: " + (TextUtils.isEmpty(avgSquare) ? "0" : avgSquare) + "㎡");

        // 时间
        tvSupplyTime.setText(TextUtils.isEmpty(receptionSandTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : receptionSandTime);


        /** 满载照片 */
        List<SupplyDetail.ReceptionSandAttachmentListBean> list = value.getReceptionSandAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }

        // 显示图片列表
        if (adapter == null) {
            adapter = new SupplyDetailAdapter(getContext(), list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    final SupplyDetail.ReceptionSandAttachmentListBean bean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                    } else {
                        if (activity.isSupply) {
                            ToastUtil.tip(getContext(), "验砂已完成, 不能删除图片");
                        } else {
                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除
                                    presenter.deleteImgForItemID(bean.getItemID(), FULLY_PHOTO);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (activity.isSupply) {
                        ToastUtil.tip(getContext(), "验砂已完成, 不能新增图片");
                    } else {
                        // 弹出图片选择器
                        int size = adapter.list.size();
                        int max = SettingUtil.NUM_IMAGE_SELECTION - size;
                        if (max > 0) {
                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 把图片解析成可以上传的任务, 上传
                                    List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                                    presenter.getCommitImgList(imageMultipleResultEvent, subID, all.get(0).getSubcontractorAccount(), FULLY_PHOTO);
                                }

                                @Override
                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                }
                            });
                        } else {
                            ToastUtil.tip(getContext(), "已到达图片选择上限");
                        }
                    }
                }
            });

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }


        /** 船名照片 */
        List<SupplyDetail.ReceptionSandBoatNameAttachmentListBean> nameList = value.getReceptionSandBoatNameAttachmentList();
        if (nameList == null) {
            nameList = new ArrayList<>();
        }

        if (nameAdapter == null) {
            nameAdapter = new SupplyDetailNameAdapter(getContext(), nameList);
            nameAdapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    final SupplyDetail.ReceptionSandBoatNameAttachmentListBean bean = nameAdapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                    } else {
                        if (activity.isSupply) {
                            ToastUtil.tip(getContext(), "验砂已完成, 不能删除图片");
                        } else {
                            activity.showDailog("删除图片", "是否删除图片", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // 删除
                                    presenter.deleteImgForItemID(bean.getItemID(), NAME_PHOTO);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    if (activity.isSupply) {
                        ToastUtil.tip(getContext(), "验砂已完成, 不能新增图片");
                    } else {
                        // 弹出图片选择器
                        int size = nameAdapter.list.size();
                        int max = SettingUtil.NUM_IMAGE_SELECTION - size;
                        if (max > 0) {
                            RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                                @Override
                                public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                    // 把图片解析成可以上传的任务, 上传
                                    List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                                    presenter.getCommitImgList(imageMultipleResultEvent, subID, all.get(0).getSubcontractorAccount(), NAME_PHOTO);
                                }

                                @Override
                                public void onEvent(ImageRadioResultEvent imageRadioResultEvent) {

                                }
                            });
                        } else {
                            ToastUtil.tip(getContext(), "已到达图片选择上限");
                        }
                    }
                }
            });

            recyclerViewShip.setAdapter(nameAdapter);
        } else {
            nameAdapter.setDates(nameList);
            nameAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showSupplyTime(String currentDate) {
        tvSupplyTime.setText(currentDate);
    }

    @Override
    public void showTotalVolume(String volume) {
        tvTotalVolume.setText(volume);
    }

    @Override
    public void showLoading(boolean active) {
        if (active) {
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

    @Override
    public void showError(String msg) {
        ToastUtil.tip(getContext(), msg);
    }

    @Override
    public void showError() {
        ToastUtil.tip(getContext(), "数据获取失败");
    }

    @Override
    public void showCommitError() {
        ToastUtil.tip(getContext(), "提交失败, 请重试");
    }

    @Override
    public void showCommitResult(boolean active) {
        if (active) {
            ToastUtil.tip(getContext(), "提交成功!");
            btnAcceptanceCancel.setText(R.string.btn_return);
            getActivity().onBackPressed();
        } else {
            ToastUtil.tip(getContext(), "提交失败, 请重试");
        }
    }

    @Override
    public void showProgress(int max) {
        activity.progressDialog("提交图片", max, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.unsubscribe();
            }
        });
    }

    @Override
    public void updateProgress() {
        activity.updataProgress(new OnProgressFinishListener() {
            @Override
            public void onFinish() {
                // 上传完成的回调
                showError("上传完成");
                presenter.getShipDetailList(activity.itemID);
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
            showError("删除成功");
            presenter.getShipDetailList(activity.itemID);
        } else {
            showError("删除失败, 请重试");
        }
    }

    @Override
    public void showImgList(SupplyDetail value) {
        this.value = value;
        /** 满载照片 */
        List<SupplyDetail.ReceptionSandAttachmentListBean> list = value.getReceptionSandAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }

        if (adapter != null) {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }

        /** 船名照片 */
        List<SupplyDetail.ReceptionSandBoatNameAttachmentListBean> nameList = value.getReceptionSandBoatNameAttachmentList();
        if (nameList == null) {
            nameList = new ArrayList<>();
        }

        if (nameAdapter != null) {
            nameAdapter.setDates(nameList);
            nameAdapter.notifyDataSetChanged();
        }
    }
}
