package com.kc.shiptransport.mvp.threadsand;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.data.bean.PartitionSBBean;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.partition.PartitionActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/7  8:49
 * @desc ${TODD}
 */

public class ThreadSandFragment extends Fragment implements ThreadSandContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_ship_name)
    TextView tvShipName;
    @BindView(R.id.tv_ship_type)
    TextView tvShipType;
    @BindView(R.id.tv_tide_wather)
    TextView tvTideWather;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_construction_stratification)
    TextView tvConstructionStratification;
    @BindView(R.id.et_sand_voyage)
    EditText etSandVoyage;
    @BindView(R.id.tv_construction_devision)
    TextView tvConstructionDevision;
    @BindView(R.id.tv_devision_num)
    TextView tvDevisionNum;
    @BindView(R.id.et_engineering_quantity)
    EditText etEngineeringQuantity;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    private ThreadSandActivity activity;
    private ThreadSandContract.Presenter presenter;
    private CommonPopupWindow popupWindow;
    private int measuredWidth;
    private int measuredHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_sand, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        List<User> all = DataSupport.findAll(User.class);
        // 获取开始时间
        presenter.getDates(CalendarUtil.getCurrentDate("yyyy-MM-dd HH:mm"), all.get(0).getUserID());

        // 获取施工分区
        presenter.getPartition(all.get(0).getUserID());
        return view;
    }

    @Override
    public void initViews(View view) {
        // 设置标题
        tvTitle.setText(R.string.title_thread_sand);

        activity = (ThreadSandActivity) getActivity();
    }

    @Override
    public void initListener() {
        //        /** 开始时间 */
        //        tvStartTime.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                CalendarUtil.showPickerDialog(getContext(), tvStartTime, "HH:mm", new OnTimePickerSureClickListener() {
        //                    @Override
        //                    public void onSure(String str) {
        //
        //                    }
        //                });
        //            }
        //        });
        /** 结束时间 */
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showPickerDialog(getContext(), tvEndTime, "yyyy-MM-dd HH:mm", new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });
        /** 施工分层 */
        tvConstructionStratification.post(new Runnable() {
            @Override
            public void run() {
                measuredWidth = tvConstructionStratification.getMeasuredWidth();
                measuredHeight = tvConstructionStratification.getMeasuredHeight();

                int width = tvConstructionStratification.getWidth();
                int height = tvConstructionStratification.getHeight();
            }
        });


        // 单选
        tvConstructionStratification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    return;
                }

                popupWindow = new CommonPopupWindow.Builder(getContext())
                        .setView(R.layout.popup_down)
                        .setWidthAndHeight(measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimationStyle(R.style.AnimDown)
                        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                            @Override
                            public void getChildView(View view, int layoutResId) {
                                // 初始化控件
                                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);

                                recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));

                                final List<String> arr = new ArrayList<String>();

                                for (int i = 0; i < 9; i++) {
                                    arr.add(String.valueOf(i + 1));
                                }

                                ThreadSandAdapter adapter = new ThreadSandAdapter(getContext(), arr);
                                adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, int... type) {
                                        tvConstructionStratification.setText(arr.get(position));
                                        if (popupWindow != null) {
                                            popupWindow.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });

                                recycle_view.setAdapter(adapter);
                            }
                        })
                        .setOutsideTouchable(true)
                        .create();
                popupWindow.showAsDropDown(view);
            }
        });

        /** 施工分区 */
        // 跳转
        tvConstructionDevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PartitionActivity.startActivity(getContext());
            }
        });
    }

    @Override
    public void setPresenter(ThreadSandContract.Presenter presenter) {
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

    @Override
    public void showStartDate(LogCurrentDateBean bean) {
        // 修改开始日期
        tvStartTime.setText(bean.getStartTime());
        // 施工船舶
        //tvShipName.setText(bean.getShipName());
    }

    @Override
    public void showPartition(PartitionSBBean bean) {
        tvConstructionDevision.setText(bean.getPartition());
        tvDevisionNum.setText(String.valueOf(bean.getSize()));
    }
}
