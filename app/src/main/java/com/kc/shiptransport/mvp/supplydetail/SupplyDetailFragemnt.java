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
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Subcontractor;
import com.kc.shiptransport.db.supply.SupplyDetail;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnRxGalleryRadioListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
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
    private Unbinder unbinder;
    private SupplyDetailContract.Presenter presenter;
    private SupplyDetailActivity activity;
    private SupplyDetailAdapter adapter;

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
            btnAcceptanceCancel.setText(R.string.btn_return);

        } else {
            // 开启软键盘
            etShipVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etDeckVolume.setInputType(InputType.TYPE_CLASS_TEXT);
            etBatch.setInputType(InputType.TYPE_CLASS_TEXT);
            btnAcceptanceCommit.setVisibility(View.VISIBLE);
            btnAcceptanceCancel.setText(R.string.btn_cancle);
        }

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    public void initListener() {
        /* 提交 */
        btnAcceptanceCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(tvSupplyTime.getText().toString())) {
                    presenter.commit(activity.itemID, tvSupplyTime.getText().toString());
                } else {
                    Toast.makeText(getContext(), "请选择时间", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* 返回 */
        btnAcceptanceCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
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

        /* 点击弹出时间选择器 */
        tvSupplyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CalendarUtil.showPickerDialog(getContext(), tvSupplyTime, CalendarUtil.YYYY_MM_DD_HH_MM, new OnTimePickerSureClickListener() {
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

        int itemID = value.getItemID();
        String shipAccount = value.getShipAccount();
        String shipName = value.getShipName();
        String subcontractorAccount = value.getSubcontractorAccount();
        String subcontractorName = value.getSubcontractorName();
        String shipItemNum = value.getShipItemNum();
        String receptionSandTime = value.getReceptionSandTime();
        String systemDate = value.getSystemDate();
        String totalCompleteRide = value.getTotalCompleteRide();
        String totalCompleteSquare = value.getTotalCompleteSquare();
        String avgSquare = value.getAvgSquare();


        tvShipName.setText(TextUtils.isEmpty(shipName) ? "" : shipName);

        tvShipId.setText("船次: " + (TextUtils.isEmpty(shipItemNum) ? "" : shipItemNum));
        tvSubontractor.setText("供应商: " + (TextUtils.isEmpty(subcontractorName) ? "" : subcontractorName));
        tvTotalVoyage.setText("累计完成航次: " + (TextUtils.isEmpty(totalCompleteRide) ? "0" : totalCompleteRide) + "次");
        tvTotalValue.setText("累计完成方量: " + (TextUtils.isEmpty(totalCompleteSquare) ? "0" : totalCompleteSquare) + "㎡");
        tvAvgValue.setText("平均航次方量: " + (TextUtils.isEmpty(avgSquare) ? "0" : avgSquare) + "㎡");

        // 时间
        tvSupplyTime.setText(TextUtils.isEmpty(receptionSandTime) ? CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM) : receptionSandTime);


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
                    SupplyDetail.ReceptionSandAttachmentListBean bean = adapter.list.get(position);
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
                                presenter.getCommitImgList(imageMultipleResultEvent, value.getItemID(), all.get(0).getSubcontractorAccount());
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
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(activity, "数据获取失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitError() {
        Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCommitResult(boolean active) {
        if (active) {
            Toast.makeText(activity, "提交成功!", Toast.LENGTH_SHORT).show();
            btnAcceptanceCancel.setText(R.string.btn_return);
            getActivity().onBackPressed();
        } else {
            Toast.makeText(activity, "提交失败, 请重试", Toast.LENGTH_SHORT).show();
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
        List<SupplyDetail.ReceptionSandAttachmentListBean> list = value.getReceptionSandAttachmentList();
        if (list == null) {
            list = new ArrayList<>();
        }

        if (adapter != null) {
            adapter.setDates(list);
            adapter.notifyDataSetChanged();
        }
    }


}
