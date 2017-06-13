package com.kc.shiptransport.mvp.voyagedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kc.shiptransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/13  17:14
 * @desc ${TODD}
 */

public class VoyageDetailFragment extends Fragment implements VoyageDetailContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_ship_location)
    RelativeLayout rlShipLocation;
    @BindView(R.id.rl_ship_date)
    RelativeLayout rlShipDate;
    @BindView(R.id.rl_sample_num)
    RelativeLayout rlSampleNum;
    @BindView(R.id.rl_material_from)
    RelativeLayout rlMaterialFrom;
    @BindView(R.id.rl_start_date)
    RelativeLayout rlStartDate;
    @BindView(R.id.rl_end_date)
    RelativeLayout rlEndDate;
    @BindView(R.id.rl_come_date)
    RelativeLayout rlComeDate;
    @BindView(R.id.rl_exit_date)
    RelativeLayout rlExitDate;
    @BindView(R.id.rl_come_anchor_date)
    RelativeLayout rlComeAnchorDate;
    @BindView(R.id.rl_clean_date)
    RelativeLayout rlCleanDate;
    @BindView(R.id.rl_material_ordar)
    RelativeLayout rlMaterialOrdar;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    Unbinder unbinder;
    private VoyageDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voyage_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(VoyageDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
