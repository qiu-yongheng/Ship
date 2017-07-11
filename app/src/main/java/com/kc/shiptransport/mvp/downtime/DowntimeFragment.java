package com.kc.shiptransport.mvp.downtime;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.LogCurrentDateBean;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.mvp.downtimelog.DowntimeLogActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/6  17:19
 * @desc ${TODD}
 */

public class DowntimeFragment extends Fragment implements DowntimeContract.View {

    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.text_start_time)
    TextView textStartTime;
    @BindView(R.id.text_end_time)
    TextView textEndTime;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_down_time)
    Button btnDownTime;
    @BindView(R.id.btn_down_log)
    Button btnDownLog;
    private DowntimeActivity activity;
    private DowntimeAdapter adapter;
    private DowntimeContract.Presenter presenter;
    private int stopType = -1;
    private ConstructionBoat boat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_down_time, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();

        // 设置当前施工船舶
        List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
        int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);

        boat = all.get(position - 1);

        // 获取数据
        presenter.getStopOptions();
        presenter.getStartDate(CalendarUtil.getCurrentDate("yyyy-MM-dd"), boat.getShipNum());
        return view;
    }

    @Override
    public void initViews(View view) {

        if (boat != null) {
            tvTitle.setText("施工船舶: " + boat.getShipName());
        } else {
            List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
            int position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);

            boat = all.get(position - 1);
            tvTitle.setText("施工船舶: " + boat.getShipName());
        }

        activity = (DowntimeActivity) getActivity();

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {
        // 停工
        btnDownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 开始时间
                String startTime = textStartTime.getText().toString();
                // 结束时间
                String endTime = textEndTime.getText().toString();
                List<User> users = DataSupport.findAll(User.class);

                if (stopType != -1 && !TextUtils.isEmpty(endTime) && !endTime.equals("请选择时间")) {
                    presenter.stop(0, boat.getShipNum(), startTime, endTime, users.get(0).getUserID(), stopType);
                } else {
                    Toast.makeText(getContext(), "停工因素或结束时间未选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showPickerDialog(getContext(), textEndTime, "yyyy-MM-dd HH:mm", new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });

        /** 跳转到停工日志 */
        btnDownLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                DowntimeLogActivity.startActivity(getContext(), SettingUtil.TYPE_STOP);
            }
        });
    }

    @Override
    public void setPresenter(DowntimeContract.Presenter presenter) {
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
    public void showGetStopOptions(final List<StopOption> stopOptions) {
        if (adapter == null) {
            adapter = new DowntimeAdapter(getContext(), stopOptions);
            adapter.setOnRecyclerViewClickListener(new OnRecyclerviewItemClickListener() {
                @Override
                public void onItemClick(View view, int position, int... type) {
                    // 刷新
                    adapter.notifyItemChanged(type[0]);
                    // 保存选中类型
                    stopType = stopOptions.get(position).getItemID();
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
            recyclerview.setAdapter(adapter);
        } else {
            adapter.setDates(stopOptions);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showStopResult(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext(), "停工成功", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), "停工失败, 请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showStartDate(LogCurrentDateBean bean) {
        textStartTime.setText(bean.getStartTime());
    }
}
