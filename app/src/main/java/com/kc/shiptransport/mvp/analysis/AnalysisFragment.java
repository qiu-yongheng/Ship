package com.kc.shiptransport.mvp.analysis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.kc.shiptransport.R;
import com.kc.shiptransport.db.SubcontractorList;
import com.kc.shiptransport.db.acceptanceevaluation.AcceptanceEvaluationList;
import com.kc.shiptransport.db.acceptancerank.Rank;
import com.kc.shiptransport.db.analysis.ProgressTrack;
import com.kc.shiptransport.db.exitfeedback.ExitFeedBack;
import com.kc.shiptransport.db.ship.ShipList;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.analysisdetail.AnalysisDetailActivity;
import com.kc.shiptransport.mvp.exitapplicationassessor.ExitApplicationAssessorActivity;
import com.kc.shiptransport.mvp.scannerdetail.ScannerDetailActivity;
import com.kc.shiptransport.mvp.voyagedetail.VoyageDetailActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SelectUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadmoreWrapper;

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
    private int type;
    private CommonAdapter<AcceptanceEvaluationList> evaluationAdapter;
    private int pageCount = 1;
    private ArrayList<AcceptanceEvaluationList> lists = new ArrayList<>();
    private ArrayList<ExitFeedBack> exitList = new ArrayList<>();
    private LoadmoreWrapper<Object> loadmoreWrapper;
    private CommonAdapter<Rank> rankAdapter;
    private List<String> evaluation = new ArrayList<>();
    private CommonAdapter<ExitFeedBack> exitAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        /**
         * 根据类型加载不同的数据
         */
        switch (type) {
            case SettingUtil.TYPE_ANALYSIS: // 计划跟踪
                presenter.subscribe();
                break;
            case SettingUtil.TYPE_ACCEPTANCE_EVALUATION: // 评价
                presenter.getEvaluation(20, pageCount, "", "", "", true);
                break;
            case SettingUtil.TYPE_ACCEPTANCE_RANK: // 评价排行
                presenter.getRank("", "");
                break;
            case SettingUtil.TYPE_EXIT_FEEDBACK: // 退场反馈
                presenter.getExitFeedBack(20, pageCount, "", "", "", true);
                break;
        }
        return view;
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (AnalysisActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));

        /**
         * 根据不同的类型, 加载不同的布局
         */
        type = activity.type;
        if (type == SettingUtil.TYPE_ANALYSIS) {
            // 计划跟踪
            activity.getSupportActionBar().setTitle(R.string.title_analysis);
        } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION) {
            // 预验收评价
            selectSub.setVisibility(View.GONE);
            activity.getSupportActionBar().setTitle(R.string.title_evaluation);
        } else if (type == SettingUtil.TYPE_ACCEPTANCE_RANK) {
            // 供应商评价
            selectSub.setVisibility(View.GONE);
            selectShip.setVisibility(View.GONE);
            activity.getSupportActionBar().setTitle(R.string.title_rank);
        } else if (type == SettingUtil.TYPE_EXIT_FEEDBACK) {
            // 退场反馈
            selectSub.setVisibility(View.GONE);
            activity.getSupportActionBar().setTitle(R.string.title_feedback);
        }
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
                                String[] stringArray = new String[]{};
                                if (type == SettingUtil.TYPE_ANALYSIS) {
                                    // 计划跟踪
                                    stringArray = getResources().getStringArray(R.array.select_time);
                                } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION) {
                                    // 预验收评价
                                    stringArray = getResources().getStringArray(R.array.select_acceptance_time);
                                } else if (type == SettingUtil.TYPE_ACCEPTANCE_RANK) {
                                    // 供应商评价
                                    stringArray = getResources().getStringArray(R.array.select_acceptance_time);
                                } else if (type == SettingUtil.TYPE_EXIT_FEEDBACK) {
                                    // 退场反馈
                                    stringArray = getResources().getStringArray(R.array.select_acceptance_time);
                                }


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

                                                        if (type == SettingUtil.TYPE_ANALYSIS) {
                                                            /** 计划跟踪 */
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

                                                        } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION ||
                                                                type == SettingUtil.TYPE_ACCEPTANCE_RANK ||
                                                                type == SettingUtil.TYPE_EXIT_FEEDBACK) {
                                                            /** 预验收评价 供应商评价 退场反馈*/
                                                            switch (position) {
                                                                case 0:
                                                                    // 全部
                                                                    startTime = "";
                                                                    endTime = "";
                                                                    break;
                                                                case 1:
                                                                    // 上一周
                                                                    startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -7);
                                                                    endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                    break;
                                                                case 2:
                                                                    // 前一周
                                                                    startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -14);
                                                                    endTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.DATE, -7);
                                                                    break;
                                                                case 3:
                                                                    // 上一月
                                                                    startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.MONTH, -1);
                                                                    endTime = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);
                                                                    break;
                                                                case 4:
                                                                    // 前一月
                                                                    startTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.MONTH, -2);
                                                                    endTime = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD, Calendar.MONTH, -1);
                                                                    break;
                                                                case 5:
                                                                    // 自定义时间
                                                                    recycle_view.setVisibility(View.GONE);
                                                                    linearLayout.setVisibility(View.VISIBLE);
                                                                    break;
                                                            }

                                                            if (pop_time.isShowing() && position != 5) {
                                                                switch (type) {
                                                                    case SettingUtil.TYPE_ACCEPTANCE_EVALUATION:
                                                                        /** 预验收评价 */
                                                                        pageCount = 1;
                                                                        presenter.getEvaluation(20, pageCount, startTime, endTime, consShip, true);
                                                                        break;
                                                                    case SettingUtil.TYPE_ACCEPTANCE_RANK:
                                                                        /** 供应商评价 */
                                                                        presenter.getRank(startTime, endTime);
                                                                        break;
                                                                    case SettingUtil.TYPE_EXIT_FEEDBACK:
                                                                        /** 退场反馈 */
                                                                        pageCount = 1;
                                                                        presenter.getExitFeedBack(20, pageCount, startTime, endTime, consShip, true);
                                                                        break;
                                                                }
                                                                pop_time.dismiss();
                                                            }
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
                                        if (type == SettingUtil.TYPE_ANALYSIS) {
                                            // 计划跟踪
                                            presenter.search(startTime, endTime, subAccount, consShip);
                                        } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION) {
                                            // 预验收评价
                                            pageCount = 1;
                                            presenter.getEvaluation(20, pageCount, startTime, endTime, consShip, true);
                                        } else if (type == SettingUtil.TYPE_ACCEPTANCE_RANK) {
                                            // 供应商评价排行
                                            presenter.getRank(startTime, endTime);
                                        } else if (type == SettingUtil.TYPE_EXIT_FEEDBACK) {
                                            // 退场反馈
                                            presenter.getExitFeedBack(20, pageCount, startTime, endTime, consShip, true);
                                        }


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

        /** 选择供应商 */
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
                                        selectSub.setText("全部供应商");
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
                                                        if (type == SettingUtil.TYPE_ANALYSIS) {
                                                            // 计划跟踪
                                                            presenter.search(startTime, endTime, subAccount, consShip);
                                                        } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION) {
                                                            // 预验收评价
                                                            pageCount = 1;
                                                            presenter.getEvaluation(20, pageCount, startTime, endTime, consShip, true);
                                                        } else if (type == SettingUtil.TYPE_EXIT_FEEDBACK) {
                                                            // 退场反馈
                                                            pageCount = 1;
                                                            presenter.getExitFeedBack(20, pageCount, startTime, endTime, consShip, true);
                                                        }

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
                                        if (type == SettingUtil.TYPE_ANALYSIS) {
                                            // 计划跟踪
                                            presenter.search(startTime, endTime, subAccount, consShip);
                                        } else if (type == SettingUtil.TYPE_ACCEPTANCE_EVALUATION) {
                                            // 预验收评价
                                            pageCount = 1;
                                            presenter.getEvaluation(20, pageCount, startTime, endTime, consShip, true);
                                        } else if (type == SettingUtil.TYPE_EXIT_FEEDBACK) {
                                            // 退场反馈
                                            pageCount = 1;
                                            presenter.getExitFeedBack(20, pageCount, startTime, endTime, consShip, true);
                                        }
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
     * 进场计划跟踪搜索结果
     */
    @Override
    public void showSearchResult(List<ProgressTrack> list) {
        //        if (adapter == null) {
        adapter = new CommonAdapter<ProgressTrack>(getContext(), R.layout.item_analysis_list, list) {
            @Override
            protected void convert(ViewHolder holder, final ProgressTrack progressTrack, int position) {
                holder.setText(R.id.tv_sub_name, progressTrack.getShipName())
                        .setText(R.id.tv_plan_time, progressTrack.getPlanDay())
                        .setText(R.id.tv_ship_name, progressTrack.getSubcontractorName())
                        .setText(R.id.tv_state, progressTrack.getStatusValue())
                        .setOnClickListener(R.id.card_view, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // TODO 跳转到进场计划跟踪详情界面
                                AnalysisDetailActivity.startActivity(getContext(), progressTrack.getItemID());
                            }
                        });
            }
        };

        // 添加空数据界面显示
        EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(adapter);
        emptyWrapper.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, recycleView, false));
        recycleView.setAdapter(emptyWrapper);
        //        } else {
        //            adapter.setDates(list);
        //            adapter.notifyDataSetChanged();
        //        }
    }

    /**
     * 供应商预验收评价数据
     *
     * @param list
     */
    @Override
    public void showEvaluation(List<AcceptanceEvaluationList> list) {
        Log.d("==", "list长度: " + list.size());
        if (pageCount == 1) {
            lists.clear();
            lists.addAll(list);

            if (evaluation.isEmpty()) {
                evaluation.add("基础信息查看");
                evaluation.add("扫描件查看");
            }

            evaluationAdapter = new CommonAdapter<AcceptanceEvaluationList>(getContext(), R.layout.item_evaluation_list, lists) {
                @Override
                protected void convert(ViewHolder holder, final AcceptanceEvaluationList acceptanceEvaluationList, int position) {
                    holder.setText(R.id.tv_ship_name, acceptanceEvaluationList.getShipName())
                            .setText(R.id.tv_acceptance_time, acceptanceEvaluationList.getPreAcceptanceTime())
                            .setRating(R.id.rb_material_integrity, acceptanceEvaluationList.getMaterialIntegrity())
                            .setRating(R.id.rb_material_timeliness, acceptanceEvaluationList.getMaterialTimeliness())
                            .setText(R.id.tv_acceptance_status, acceptanceEvaluationList.getStatusRemark());

                    holder.setOnClickListener(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SelectUtil<String> selectUtil = new SelectUtil<>();
                            selectUtil.showSelectDialog(getContext(), evaluation, true, acceptanceEvaluationList.getShipName(), new OptionsPickerView.OnOptionsSelectListener() {
                                @Override
                                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                    // 选择后, 在这里回调
                                    switch (options1) {
                                        case 0:
                                            // 基础信息查看
                                            VoyageDetailActivity.startActivity(getActivity(), acceptanceEvaluationList.getSubcontractorInterimApproachPlanID(), 1);
                                            break;
                                        case 1:
                                            // 扫描件查看
                                            ScannerDetailActivity.startActivity(getActivity(), acceptanceEvaluationList.getSubcontractorInterimApproachPlanID(), 1);
                                            break;
                                    }
                                }
                            });
                        }
                    });
                }
            };

            // 添加空数据界面显示
            EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(evaluationAdapter);
            emptyWrapper.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, recycleView, false));

            // 添加加载更多
            loadmoreWrapper = new LoadmoreWrapper<>(emptyWrapper);
            loadmoreWrapper.setLoadMoreView(R.layout.item_load);
            loadmoreWrapper.setNoMoreView(R.layout.item_no_more);
            loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    pageCount += 1;
                    presenter.getEvaluation(20, pageCount, startTime, endTime, consShip, false);
                }
            });

            recycleView.setAdapter(loadmoreWrapper);

        } else if (pageCount > 1) {
            if (list.size() == 0) {
                // 没有更多数据
                loadmoreWrapper.setIsMoreDates(false);
            } else {
                loadmoreWrapper.setIsMoreDates(true);
                lists.addAll(list);
            }

            if (loadmoreWrapper != null) {
                loadmoreWrapper.notifyDataSetChanged();
            }
        }
    }

    /**
     * 显示供应商评分排行
     */
    @Override
    public void showRank(List<Rank> list) {
        rankAdapter = new CommonAdapter<Rank>(getContext(), R.layout.item_rank_list, list) {
            @Override
            protected void convert(ViewHolder holder, Rank rank, int position) {
                holder.setText(R.id.tv_sub_name, rank.getSubcontractorName())
                        .setText(R.id.tv_avg_score, rank.getAvgTotalScore())
                        .setText(R.id.tv_total_count, String.valueOf(rank.getTotalCount()))
                        .setRating(R.id.rb_material_integrity, TextUtils.isEmpty(rank.getAvgTotalMaterialIntegrity()) ? 0 : Float.valueOf(rank.getAvgTotalMaterialIntegrity()))
                        .setRating(R.id.rb_material_timeliness, TextUtils.isEmpty(rank.getAvgTotalMaterialTimeliness()) ? 0 : Float.valueOf(rank.getAvgTotalMaterialTimeliness()));
            }
        };

        // 添加空数据界面显示
        EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(rankAdapter);
        emptyWrapper.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, recycleView, false));
        recycleView.setAdapter(emptyWrapper);
    }

    /**
     * 显示退场反馈
     *
     * @param list
     */
    @Override
    public void showExitFeedBack(List<ExitFeedBack> list) {
        Log.d("==", "list长度: " + list.size());
        if (pageCount == 1) {
            exitList.clear();
            exitList.addAll(list);

            exitAdapter = new CommonAdapter<ExitFeedBack>(getContext(), R.layout.item_exit_feedback, exitList) {
                @Override
                protected void convert(ViewHolder holder, final ExitFeedBack feedBack, int position) {
                    holder.setText(R.id.tv_ship_name, feedBack.getShipName())
                            .setText(R.id.tv_exit_time, feedBack.getExitTime())
                            .setText(R.id.tv_sub_name, feedBack.getSubcontractorName())
                            .setText(R.id.tv_application_state, feedBack.getIsSumbitted() == 1 ? "已申请退场" : "未申请退场")
                            .setText(R.id.tv_assessor_state, feedBack.getIsExit() == 1 ? "已同意退场" : "未同意退场");

                    holder.setOnClickListener(R.id.card_view, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 跳转到退场审核界面, 不能修改界面数据
                            ExitApplicationAssessorActivity.startActivity(getContext(), feedBack.getSubcontractorInterimApproachPlanID(), true);
                        }
                    });
                }
            };

            // 添加空数据界面显示
            EmptyWrapper<Object> emptyWrapper = new EmptyWrapper<>(exitAdapter);
            emptyWrapper.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, recycleView, false));

            // 添加加载更多
            loadmoreWrapper = new LoadmoreWrapper<>(emptyWrapper);
            loadmoreWrapper.setLoadMoreView(R.layout.item_load);
            loadmoreWrapper.setNoMoreView(R.layout.item_no_more);
            loadmoreWrapper.setOnLoadMoreListener(new LoadmoreWrapper.OnLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    pageCount += 1;
                    presenter.getExitFeedBack(20, pageCount, startTime, endTime, consShip, false);
                }
            });

            recycleView.setAdapter(loadmoreWrapper);

        } else if (pageCount > 1) {
            if (list.size() == 0) {
                // 没有更多数据
                loadmoreWrapper.setIsMoreDates(false);
            } else {
                loadmoreWrapper.setIsMoreDates(true);
                exitList.addAll(list);
            }

            if (loadmoreWrapper != null) {
                loadmoreWrapper.notifyDataSetChanged();
            }
        }
    }
}