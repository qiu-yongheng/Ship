package com.kc.shiptransport.mvp.todayplan;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.todayplan.TodayPlanBean;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/11/2  15:23
 * @desc ${TODD}
 */

public class TodayPlanFragment extends Fragment implements TodayPlanContract.View {
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_ship_count)
    TextView tvShipCount;
    @BindView(R.id.tv_total_cube)
    TextView tvTotalCube;
    @BindView(R.id.ll_fix_btn)
    LinearLayout llFixBtn;
    @BindView(R.id.rv_today_sub)
    RecyclerView rvTodaySub;
    RecyclerView rvShipType;
    RecyclerView rvMaterialType;
    RecyclerView rvGoodsType;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    private TodayPlanActivity activity;
    private TodayPlanContract.Presenter presenter;
    private View shipTypeView;
    private View materialTypeView;
    private View goodsTypeView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_plan, container, false);
        unbinder = ButterKnife.bind(this, view);


        initViews(view);
        initListener();

        // TODO: 请求今日数据
        presenter.getTodayPlan(CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD));
        return view;
    }

    @Override
    public void initViews(View view) {
        tvTime.setText(CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD));
        setHasOptionsMenu(true);
        activity = (TodayPlanActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        activity.getSupportActionBar().setTitle(R.string.title_today_plan);

        /** 船舶类型 */
        shipTypeView = LayoutInflater.from(getContext()).inflate(R.layout.view_today_ship_type, null);
        rvShipType = (RecyclerView) shipTypeView.findViewById(R.id.rv_ship_type);
        /** 材料分类 */
        materialTypeView = LayoutInflater.from(getContext()).inflate(R.layout.view_today_material_type, null);
        rvMaterialType = (RecyclerView) materialTypeView.findViewById(R.id.rv_material_type);
        /** 货物分类 */
        goodsTypeView = LayoutInflater.from(getContext()).inflate(R.layout.view_today_goods_type, null);
        rvGoodsType = (RecyclerView) goodsTypeView.findViewById(R.id.rv_goods_type);

        rvShipType.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMaterialType.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGoodsType.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTodaySub.setLayoutManager(new LinearLayoutManager(getContext()));
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
    public void initListener() {
        /** 切换日期 */
        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    CalendarUtil.showDatePickerDialog(getContext(), tvTime, new OnTimePickerSureClickListener() {
                        @Override
                        public void onSure(String str) {
                            presenter.getTodayPlan(str);
                        }
                    }, new OnTimePickerLastDateClickListener() {
                        @Override
                        public void onLastDate() {

                        }
                    }, false, true, false);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setPresenter(TodayPlanContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "正在加载...", new OnDailogCancleClickListener() {
                @Override
                public void onCancel(ProgressDialog dialog) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showTodayPlan(TodayPlanBean bean) {
        // 日期
        String today = TextUtils.isEmpty(bean.getDate()) ? "" : bean.getDate();
        // 船舶数
        String allShipCount = TextUtils.isEmpty(bean.getAllShipCount()) ? "0" : bean.getAllShipCount();
        // 船舶分类
        List<TodayPlanBean.ShipTypeListBean> shipTypeList = bean.getShipTypeList();
        // 材料分类
        List<TodayPlanBean.MaterialClassificationListBean> materialClassificationList = bean.getMaterialClassificationList();
        // 货物分类
        List<TodayPlanBean.GoodsNameListBean> goodsNameList = bean.getGoodsNameList();
        // 分包商分类
        List<TodayPlanBean.SubcontractorListBean> subcontractorList = bean.getSubcontractorList();

        tvNoData.setVisibility(subcontractorList.isEmpty() ? View.VISIBLE : View.GONE);
        rvTodaySub.setVisibility(subcontractorList.isEmpty() ? View.GONE : View.VISIBLE);

        /** 船舶总数 */
        tvShipCount.setText("共" + allShipCount + "艘");

        /** 船舶分类 */
        CommonAdapter<TodayPlanBean.ShipTypeListBean> shipAdapter = new CommonAdapter<TodayPlanBean.ShipTypeListBean>(getContext(), R.layout.item_today_ship_type, shipTypeList) {
            @Override
            protected void convert(ViewHolder holder, TodayPlanBean.ShipTypeListBean shipTypeListBean, int position) {
                holder.setText(R.id.tv_name, shipTypeListBean.getShipType())
                        .setText(R.id.tv_value, shipTypeListBean.getCount());
            }
        };
        rvShipType.setAdapter(shipAdapter);

        /** 材料分类 */
        CommonAdapter<TodayPlanBean.MaterialClassificationListBean> materialAdapter = new CommonAdapter<TodayPlanBean.MaterialClassificationListBean>(getContext(), R.layout.item_today_ship_type, materialClassificationList) {
            @Override
            protected void convert(ViewHolder holder, TodayPlanBean.MaterialClassificationListBean materialClassificationListBean, int position) {
                holder.setText(R.id.tv_name, materialClassificationListBean.getMaterialClassification())
                        .setText(R.id.tv_value, materialClassificationListBean.getCount());
            }
        };
        rvMaterialType.setAdapter(materialAdapter);

        /** 货物分类 */
        CommonAdapter<TodayPlanBean.GoodsNameListBean> goodsAdapter = new CommonAdapter<TodayPlanBean.GoodsNameListBean>(getContext(), R.layout.item_today_ship_type, goodsNameList) {
            @Override
            protected void convert(ViewHolder holder, TodayPlanBean.GoodsNameListBean goodsNameListBean, int position) {
                holder.setText(R.id.tv_name, goodsNameListBean.getGoodsName())
                        .setText(R.id.tv_value, goodsNameListBean.getCount());
            }
        };
        rvGoodsType.setAdapter(goodsAdapter);

        /** 分包商分类 */
        CommonAdapter<TodayPlanBean.SubcontractorListBean> subAdapter = new CommonAdapter<TodayPlanBean.SubcontractorListBean>(getContext(), R.layout.item_today_sub, subcontractorList) {
            @Override
            protected void convert(ViewHolder holder, TodayPlanBean.SubcontractorListBean subcontractorListBean, int position) {
                holder.setText(R.id.tv_name, subcontractorListBean.getSubcontractorName())
                        .setText(R.id.tv_value, (TextUtils.isEmpty(subcontractorListBean.getTotalAmount()) ? "0" : subcontractorListBean.getTotalAmount()) + "方");

                // 分包商船舶数组
                List<TodayPlanBean.SubcontractorListBean.ShipListBean> shipList = subcontractorListBean.getShipList();
                RecyclerView recyclerView = holder.getView(R.id.recycler_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                CommonAdapter<TodayPlanBean.SubcontractorListBean.ShipListBean> adapter = new CommonAdapter<TodayPlanBean.SubcontractorListBean.ShipListBean>(getContext(), R.layout.item_today_sub_detail, shipList) {
                    @Override
                    protected void convert(ViewHolder holder, TodayPlanBean.SubcontractorListBean.ShipListBean shipListBean, int position) {
                        holder.setText(R.id.tv_ship_name, shipListBean.getShipName())
                                .setText(R.id.tv_ship_type, shipListBean.getShipType())
                                .setText(R.id.tv_material_type, shipListBean.getMaterialClassification())
                                .setText(R.id.tv_mez, shipListBean.getMEZ())
                                .setText(R.id.tv_total_amount, TextUtils.isEmpty(shipListBean.getTotalAmount()) ? "0" : shipListBean.getTotalAmount())
                                .setText(R.id.tv_goods_name, shipListBean.getGoodsName())
                                .setText(R.id.tv_status, shipListBean.getStatusValue())
                                .setText(R.id.tv_height, shipListBean.getHAMUMaxHeight());
                    }
                };
                recyclerView.setAdapter(adapter);
            }
        };

        HeaderAndFooterWrapper<Object> headerWrapper = new HeaderAndFooterWrapper<>(subAdapter);
        headerWrapper.addHeaderView(shipTypeView);
        headerWrapper.addHeaderView(materialTypeView);
        headerWrapper.addHeaderView(goodsTypeView);


        rvTodaySub.setAdapter(headerWrapper);
    }
}
