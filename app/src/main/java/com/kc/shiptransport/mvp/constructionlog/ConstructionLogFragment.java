package com.kc.shiptransport.mvp.constructionlog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.interfaze.OnSpinnerClickListener;
import com.kc.shiptransport.mvp.downtime.DowntimeActivity;
import com.kc.shiptransport.mvp.threadsand.ThreadSandActivity;
import com.kc.shiptransport.util.CalendarUtil;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;
import com.kc.shiptransport.util.SpinnerUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/7/6  15:45
 * @desc ${TODD}
 */

public class ConstructionLogFragment extends Fragment implements ConstructionLogContract.View {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.rl_stop)
    RelativeLayout rlStop;
    @BindView(R.id.rl_throw_sand)
    RelativeLayout rlThrowSand;
    Unbinder unbinder;
    @BindView(R.id.text_ship_name)
    Spinner textShipName;
    private ConstructionLogContract.Presenter presenter;
    private ConstructionLogActivity activity;
    private int spinner_position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_construction, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        return view;
    }

    @Override
    public void initViews(View view) {
        // 设置标题
        tvTitle.setText(R.string.title_construction);
        activity = (ConstructionLogActivity) getActivity();

        // 设置船名
        //        List<User> all = DataSupport.findAll(User.class);
        //        String userName = all.get(0).getUserName();
        //        textShipName.setText(userName);

        // 设置时间
        String currentDate = CalendarUtil.getCurrentDate("yyyy-MM-dd");
        textTime.setText(currentDate);

        // 获取上次选择施工船舶的position
        spinner_position = SharePreferenceUtil.getInt(getContext(), SettingUtil.LOG_SHIP_POSITION);
    }

    @Override
    public void initListener() {
        /** 停工 */
        rlStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner_position == 0) {
                    Toast.makeText(getContext(), "请选择施工船舶", Toast.LENGTH_SHORT).show();
                } else {
                    DowntimeActivity.startActivity(getContext());
                }
            }
        });

        /** 抛砂 */
        rlThrowSand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spinner_position == 0) {
                    Toast.makeText(getContext(), "请选择施工船舶", Toast.LENGTH_SHORT).show();
                } else {
                    ThreadSandActivity.startActivity(getContext());
                }
            }
        });

        /** 初始化船名选择器 */
        List<ConstructionBoat> all = DataSupport.findAll(ConstructionBoat.class);
        List<String> list = new ArrayList<>();
        list.add("请选择施工船舶");
        for (ConstructionBoat boat : all) {
            list.add(boat.getShipName());
        }

        SpinnerUtil.showSpinner(getContext(), list, textShipName, spinner_position, new OnSpinnerClickListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    spinner_position = position;
                    // 记录position
                    SharePreferenceUtil.saveInt(getContext(), SettingUtil.LOG_SHIP_POSITION, position);
                }
            }
        });
    }

    @Override
    public void setPresenter(ConstructionLogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showLoading(boolean isShow) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
