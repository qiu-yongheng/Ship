package com.kc.shiptransport.mvp.amountdetail;

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
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.amount.AmountDetail;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.view.actiivty.ImageActivity;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public class AmountDetailFragment extends Fragment implements AmountDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_ship_name)
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
    @BindView(R.id.tv_warehouse_space)
    TextView tvWarehouseSpace;
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
    @BindView(R.id.btn_cancel)
    AppCompatButton btnCancel;
    @BindView(R.id.btn_commit)
    AppCompatButton btnCommit;
    @BindView(R.id.ll)
    LinearLayout ll;
    Unbinder unbinder;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private AmountDetailContract.Presenter presenter;
    private AmountDetailActivity activity;
    private AmountDetail value;
    private AmountDetailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amount_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        presenter.start(activity.itemID);
        return view;
    }

    public void initListener() {
        // 取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });

        // 提交
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // 量方时间
                String theAmountTime = tvSupplyTime.getText().toString().trim();
                // 甲板方
                String deck = etDeckVolume.getText().toString().trim();
                // 扣方
                String dedu = etShipVolume.getText().toString().trim();

                // creator
                List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);

                if (!TextUtils.isEmpty(theAmountTime) &&
                        !TextUtils.isEmpty(deck) &&
                        !deck.equals("0") &&
                        !TextUtils.isEmpty(dedu) &&
                        !dedu.equals("0")) {
                    presenter.commit(value.getItemID(), theAmountTime, value.getSubcontractorAccount(), activity.itemID, value.getShipAccount(), value.getCapacity(), deck, dedu, all.get(0).getSubcontractorAccount());
                } else {
                    Toast.makeText(getContext(), "还有数据未填写", Toast.LENGTH_SHORT).show();
                }

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

                presenter.getTotalVolume(ship, deck, value.getCapacity());
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
                presenter.getTotalVolume(ship, deck, value.getCapacity());
            }
        });

        // 选择时间
        tvSupplyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvSupplyTime);
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AmountDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(R.string.title_amount_detail);

        // TODO 根据是否验收, 改变布局
        if (activity.isAmount) {
            // 禁止软键盘
            etShipVolume.setInputType(InputType.TYPE_NULL);
            etDeckVolume.setInputType(InputType.TYPE_NULL);
            btnCommit.setVisibility(View.GONE);
            btnCancel.setText(R.string.btn_return);
        } else {
            // 开启软键盘
            etShipVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etDeckVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            btnCommit.setVisibility(View.VISIBLE);
            btnCancel.setText(R.string.btn_cancle);
        }

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
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
    public void setPresenter(AmountDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showShipDetail(final AmountDetail value) {
        this.value = value;
        List<AmountDetail.TheAmountOfSideAttachmentListBean> list = value.getTheAmountOfSideAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }


        // 进场ID
        int itemID = value.getItemID();
        // 量方时间
        String theAmountOfTime = value.getTheAmountOfTime();
        // 船账号
        String shipAccount = value.getShipAccount();
        // 船名
        String shipName = value.getShipName();
        // 舱容
        String capacity = value.getCapacity();
        // 甲板方
        String deckGauge = value.getDeckGauge();
        // 扣方
        String deduction = value.getDeduction();
        // 创建者账号
        String creator = value.getCreator();
        // 分包商
        String subcontractorName = value.getSubcontractorName();
        // 提交时间
        String systemDate = value.getSystemDate();
        // 船次
        String shipItemNum = value.getShipItemNum();
        // 累计完成航次
        String totalCompleteRide = value.getTotalCompleteRide();
        // 累计完成方量
        String totalCompleteSquare = value.getTotalCompleteSquare();
        // 平均航次方量
        String avgSquare = value.getAvgSquare();


        // 船名
        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);
        // 船次
        tvShipId.setText("船次: " + (TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum));
        // 供应商
        tvSubontractor.setText("供应商: " + (TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName));
        // 累计完成航次
        tvTotalVoyage.setText("累计完成航次: " + (TextUtils.isEmpty(totalCompleteRide) ? "0" : totalCompleteRide) + "次");
        // 累计完成方量
        tvTotalValue.setText("累计完成方量: " + (TextUtils.isEmpty(totalCompleteSquare) ? "0" : totalCompleteSquare) + "㎡");
        // 平均航次方量
        tvAvgValue.setText("平均航次方量: " + (TextUtils.isEmpty(avgSquare) ? "0" : avgSquare) + "㎡");
        // 舱容
        tvWarehouseSpace.setText("舱容: " + (TextUtils.isEmpty(capacity) ? "" : capacity));


        // 扣方
        etShipVolume.setText(TextUtils.isEmpty(deduction) ? "" : deduction);
        // 甲板方
        etDeckVolume.setText(TextUtils.isEmpty(deckGauge) ? "" : deckGauge);
        // 量方时间
        tvSupplyTime.setText(TextUtils.isEmpty(theAmountOfTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : theAmountOfTime);

        /** 显示图片 */
        if (adapter == null) {

            adapter = new AmountDetailAdapter(getContext(), list);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    AmountDetail.TheAmountOfSideAttachmentListBean bean = adapter.list.get(position);
                    if (type[0] == 0) {
                        // 预览
                        ImageActivity.startActivity(getContext(), bean.getFilePath());
                    } else {
                        // 删除
                        presenter.deleteImgForItemID(bean.getItemID());
                    }
                }

                @Override
                public void onItemLongClick(View view, int position) {
                    // 弹出图片选择器
                    int size = adapter.list.size();
                    int max = 3 - size;
                    if (max > 0) {
                        RxGalleryUtil.getImagMultiple(getContext(), max, new OnRxGalleryRadioListener() {
                            @Override
                            public void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) {
                                // 把图片解析成可以上传的任务, 上传
                                List<Subcontractor> all = DataSupport.findAll(Subcontractor.class);
                                presenter.getCommitImgList(imageMultipleResultEvent, activity.itemID, all.get(0).getSubcontractorAccount());
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

            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
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
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean active) {
        Toast.makeText(activity, "提交成功!", Toast.LENGTH_SHORT).show();
        btnCancel.setText(R.string.btn_return);
        getActivity().onBackPressed();
    }

    @Override
    public void showProgress(int max) {
        activity.progressDialog("提交图片", max, new DialogInterface.OnClickListener() {
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
                // TODO 全部上传成功的回调
                Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                // 同步数据
                presenter.getShipDetailList(activity.itemID);
                hideProgress();
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

    /**
     * 只刷新图片列表
     *
     * @param value
     */
    @Override
    public void showImgList(AmountDetail value) {
        this.value = value;
        List<AmountDetail.TheAmountOfSideAttachmentListBean> list = value.getTheAmountOfSideAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }

        if (adapter != null) {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }
}
