package com.kc.shiptransport.mvp.upcoming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.mvp.analysis.AnalysisActivity;
import com.kc.shiptransport.util.SettingUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author 邱永恒
 * @time 2017/6/6 20:40
 * @desc ${TODO}
 */

public class UpcomingFragment extends Fragment {
    @BindView(R.id.tv)
    TextView tv;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        unbinder = ButterKnife.bind(this, view);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 测试专用
                AnalysisActivity.startActivity(getContext(), SettingUtil.TYPE_ACCEPTANCE_EVALUATION);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
