package com.kc.shiptransport.mvp.constructionlog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.user.User;
import com.kc.shiptransport.mvp.downtime.DowntimeActivity;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

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
    @BindView(R.id.text_ship_name)
    TextView textShipName;
    @BindView(R.id.text_time)
    TextView textTime;
    @BindView(R.id.rl_stop)
    RelativeLayout rlStop;
    @BindView(R.id.rl_throw_sand)
    RelativeLayout rlThrowSand;
    Unbinder unbinder;
    private ConstructionLogContract.Presenter presenter;
    private ConstructionLogActivity activity;

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
        List<User> all = DataSupport.findAll(User.class);
        String userName = all.get(0).getUserName();
        textShipName.setText(userName);

        // 设置时间
        String currentDate = CalendarUtil.getCurrentDate("yyyy-MM-dd");
        textTime.setText(currentDate);
    }

    @Override
    public void initListener() {
        /** 停工 */
        rlStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DowntimeActivity.startActivity(getContext());
            }
        });

        /** 抛砂 */
        rlThrowSand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
