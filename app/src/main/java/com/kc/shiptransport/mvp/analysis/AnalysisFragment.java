package com.kc.shiptransport.mvp.analysis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.ship.ShipList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.analysisdetail.AnalysisDetailActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author 邱永恒
 * @time 2017/7/26  13:58
 * @desc ${TODD}
 */

public class AnalysisFragment extends Fragment implements AnalysisContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_time)
    TextView selectTime;
    @BindView(R.id.select_sub)
    TextView selectSub;
    @BindView(R.id.select_ship)
    TextView selectShip;
    @BindView(R.id.recycler_view)
    RecyclerView recycleView;
    Unbinder unbinder;
    private AnalysisActivity activity;
    private AnalysisContract.Presenter presenter;
    private CommonPopupWindow pop_time;
    private String startTime = "";
    private String endTime = "";
    private CommonPopupWindow pop_sub;
    private CommonPopupWindow pop_ship;
    private String subAccount = "";
    private String consShip = "";
    private CommonAdapter<ProgressTrack> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // TODO
        presenter.subscribe();
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AnalysisActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(R.string.title_analysis);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {
        /** 选择时间 */
        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 文字高亮 */
                initSelectColor();
                selectTime.setTextColor(getResources().getColor(R.color.colorPrimary));

                if (pop_time != null && pop_time.isShowing()) {
                    return;
                }

                pop_time = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                final RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
                                recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

                                // 自定义时间
                                final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll);
                                TextView tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
                                TextView tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
                                Button btnReturn = (Button) view.findViewById(R.id.btn_return);
                                Button btnOk = (Button) view.findViewById(R.id.btn_ok);

                                // 获取数据
                                String[] stringArray = getResources().getStringArray(R.array.select_time);
                                List<String> dates = new ArrayList<String>();
                                for (int i = 0; i < stringArray.length; i++) {
                                    dates.add(stringArray[i]);
                                }

                                CommonAdapter<String> adapter = new CommonAdapter<String>(getContext(), R.layout.item_analysis, dates) {
                                    @Override
                                    protected void convert(ViewHolder holder, final String s, final int position) {
                                        holder.setText(R.id.tv, s)
                                                .setOnClickListener(R.id.tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        selectTime.setText(s);

                                                        switch (position) {
                                                            case 0:
                                                                // 全部
                                                                startTime = "";
                                                                endTime = "";
                                                                break;
                                                            case 1:
                                                                // 未来7天
                                                                startTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                endTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, 7);
                                                                break;
                                                            case 2:
                                                                // 最近3天
                                                                startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -3);
                                                                endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                break;
                                                            case 3:
                                                                // 最近7天
                                                                startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -7);
                                                                endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                break;
                                                            case 4:
                                                                // 最近1月
                                                                startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.MONTH, -1);
                                                                endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                break;
                                                            case 5:
                                                                // 最近3月
                                                                startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.MONTH, -3);
                                                                endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                break;
                                                            case 6:
                                                                // 自定义时间
                                                                recycle_view.setVisibility(View.GONE);
                                                                linearLayout.setVisibility(View.VISIBLE);
                                                                break;
                                                        }

                                                        if (pop_time.isShowing() && position != 6) {
                                                            // 搜索
                                                            presenter.search(startTime, endTime, subAccount, consShip);

                                                            pop_time.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                };


                                /** ------------------------------ 点击事件 ------------------------------ */
                                /** 开始时间 */
                                tvStartTime.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            CalendarUtil.showDatePickerDialog(getContext(), (TextView) view, new OnTimePickerSureClickListener() {
                                                @Override
                                                public void onSure(String str) {
                                                    startTime = str;
                                                }
                                            }, true, false);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                /** 结束时间 */
                                tvEndTime.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        try {
                                            CalendarUtil.showDatePickerDialog(getContext(), (TextView) view, new OnTimePickerSureClickListener() {
                                                @Override
                                                public void onSure(String str) {
                                                    endTime = str;
                                                }
                                            }, true, false);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                /** 返回 */
                                btnReturn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        recycle_view.setVisibility(View.VISIBLE);
                                        linearLayout.setVisibility(View.GONE);
                                    }
                                });

                                /** 确定 */
                                btnOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // 搜索
                                        presenter.search(startTime, endTime, subAccount, consShip);

                                        if (pop_time.isShowing()) {
                                            pop_time.dismiss();
                                        }
                                    }
                                });


                                recycle_view.setAdapter(adapter);

                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                pop_time.showAsDropDown(view);

                /** 消失监听 */
                pop_time.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        initSelectColor();
                    }
                });
            }
        });

        /** 选择分包商 */
        selectSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 文字高亮 */
                initSelectColor();
                selectSub.setTextColor(getResources().getColor(R.color.colorPrimary));

                if (pop_sub != null && pop_sub.isShowing()) {
                    return;
                }

                pop_sub = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                final RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
                                recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

                                // 获取数据
                                List<SubcontractorList> list = DataSupport.findAll(SubcontractorList.class);

                                // 创建适配器
                                CommonAdapter<SubcontractorList> adapter = new CommonAdapter<SubcontractorList>(getContext(), R.layout.item_analysis, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, final SubcontractorList subcontractor, int position) {
                                        holder.setText(R.id.tv, subcontractor.getSubcontractorName())
                                                .setOnClickListener(R.id.tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        selectSub.setText(subcontractor.getSubcontractorName());

                                                        // 获取账号
                                                        subAccount = subcontractor.getSubcontractorAccount();

                                                        // TODO 搜索
                                                        presenter.search(startTime, endTime, subAccount, consShip);

                                                        // 隐藏pop
                                                        if (pop_sub.isShowing()) {
                                                            pop_sub.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                };


                                // 添加头
                                HeaderAndFooterWrapper<Object> headWrapper = new HeaderAndFooterWrapper<>(adapter);
                                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_analysis, recycle_view, false);
                                TextView v = (TextView) inflate.findViewById(R.id.tv);
                                v.setText("全部");
                                headWrapper.addHeaderView(inflate);

                                headWrapper.setHeadAndFootClickListener(new HeaderAndFooterWrapper.OnHeadAndFootClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        selectSub.setText("全部分包商");
                                        subAccount = "";

                                        // 搜索
                                        presenter.search(startTime, endTime, subAccount, consShip);
                                        if (pop_sub.isShowing()) {
                                            pop_sub.dismiss();
                                        }
                                    }
                                });

                                recycle_view.setAdapter(headWrapper);
                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                pop_sub.showAsDropDown(view);

                /** 消失监听 */
                pop_sub.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        initSelectColor();
                    }
                });
            }
        });

        /** 选择施工船舶 */
        selectShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** 文字高亮 */
                initSelectColor();
                selectShip.setTextColor(getResources().getColor(R.color.colorPrimary));

                if (pop_ship != null && pop_ship.isShowing()) {
                    return;
                }

                pop_ship = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                final RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycler_view);
                                recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

                                // 获取数据
                                List<ShipList> list = DataSupport.order("ShipID asc").find(ShipList.class);

                                // 创建adapter
                                CommonAdapter<ShipList> adapter = new CommonAdapter<ShipList>(getContext(), R.layout.item_analysis, list) {
                                    @Override
                                    protected void convert(ViewHolder holder, final ShipList ShipList, int position) {
                                        holder.setText(R.id.tv, ShipList.getShipName())
                                                .setOnClickListener(R.id.tv, new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        selectShip.setText(ShipList.getShipName());

                                                        consShip = ShipList.getShipName();

                                                        // 搜索
                                                        presenter.search(startTime, endTime, subAccount, consShip);

                                                        if (pop_ship.isShowing()) {
                                                            pop_ship.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                };


                                // 添加头
                                HeaderAndFooterWrapper<Object> headWrapper = new HeaderAndFooterWrapper<>(adapter);
                                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_analysis, recycle_view, false);
                                TextView v = (TextView) inflate.findViewById(R.id.tv);
                                v.setText("全部");
                                headWrapper.addHeaderView(inflate);

                                headWrapper.setHeadAndFootClickListener(new HeaderAndFooterWrapper.OnHeadAndFootClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        selectShip.setText("全部供砂船舶");
                                        consShip = "";

                                        // 搜索
                                        presenter.search(startTime, endTime, subAccount, consShip);
                                        if (pop_ship.isShowing()) {
                                            pop_ship.dismiss();
                                        }
                                    }
                                });

                                recycle_view.setAdapter(headWrapper);
                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                pop_ship.showAsDropDown(view);

                /** 消失监听 */
                pop_ship.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        initSelectColor();
                    }
                });
            }
        });
    }

    private void initSelectColor() {
        selectTime.setTextColor(getResources().getColor(R.color.text_tag_gray));
        selectSub.setTextColor(getResources().getColor(R.color.text_tag_gray));
        selectShip.setTextColor(getResources().getColor(R.color.text_tag_gray));
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
    public void setPresenter(AnalysisContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog("加载中", "加载中", new OnDailogCancleClickListener() {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 搜索结果
     */
    @Override
    public void showSearchResult(List<ProgressTrack> list) {
        //        if (adapter == null) {
        adapter = new CommonAdapter<ProgressTrack>(getContext(), R.layout.item_analysis_list, list) {
            @Override
            protected void convert(ViewHolder holder, final ProgressTrack progressTrack, int position) {
                holder.setText(R.id.tv_sub_name, progressTrack.getSubcontractorName())
                        .setText(R.id.tv_plan_time, progressTrack.getPlanDay())
                        .setText(R.id.tv_ship_name, progressTrack.getShipName())
                        .setText(R.id.tv_state, progressTrack.getStatusValue())
                        .setOnClickListener(R.id.card_view, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO 跳转
                                AnalysisDetailActivity.startActivity(getContext(), progressTrack.getItemID());
                            }
                        });
            }
        };

        EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(adapter);
        emptyWrapper.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, recycleView, false));
        recycleView.setAdapter(emptyWrapper);
        //        } else {
        //            adapter.setDates(list);
        //            adapter.notifyDataSetChanged();
        //        }
    }
}
