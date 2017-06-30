package com.kc.shiptransport.mvp.recordedsandshow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordedSandShowList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/28  13:45
 * @desc ${TODD}
 */

public class RecordedSandTabFragment extends Fragment {
    private static List<RecordedSandShowList> datas;
    @BindView(R.id.tv_supply_ship)
    TextView tvSupplyShip;
    @BindView(R.id.rl_record_supply_ship)
    RelativeLayout rlRecordSupplyShip;
    @BindView(R.id.tv_cons_ship)
    TextView tvConsShip;
    @BindView(R.id.rl_record_receive_ship)
    RelativeLayout rlRecordReceiveShip;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.rl_record_start_time)
    RelativeLayout rlRecordStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_record_end_time)
    RelativeLayout rlRecordEndTime;
    @BindView(R.id.et_before_1)
    TextView etBefore1;
    @BindView(R.id.et_before_2)
    TextView etBefore2;
    @BindView(R.id.et_before_3)
    TextView etBefore3;
    @BindView(R.id.et_before_4)
    TextView etBefore4;
    @BindView(R.id.et_after_1)
    TextView etAfter1;
    @BindView(R.id.et_after_2)
    TextView etAfter2;
    @BindView(R.id.et_after_3)
    TextView etAfter3;
    @BindView(R.id.et_after_4)
    TextView etAfter4;
    @BindView(R.id.et_actual_amount_sand)
    TextView etActualAmountSand;
    @BindView(R.id.radiobtn_finish_recorde)
    CheckBox radiobtnFinishRecorde;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;

    public static Fragment newInstance(int position, List<RecordedSandShowList> lists) {
        datas = lists;
        RecordedSandTabFragment tabLayoutFragment = new RecordedSandTabFragment();
        /* 创建fragment时, 保存position */
        Bundle bundle = new Bundle();
        bundle.putInt("tab", position);
        tabLayoutFragment.setArguments(bundle);
        return tabLayoutFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedsand_show, container, false);
        unbinder = ButterKnife.bind(this, view);

        /* 获取保存的position */
        int tab = getArguments().getInt("tab");

        // 获取数据
        RecordedSandShowList recordedSandShowList = datas.get(tab);

        initView(recordedSandShowList);
        initListener();
        return view;
    }

    public void initListener() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initView(RecordedSandShowList list) {
        tvSupplyShip.setText(list.getSandHandlingShipID());
        tvConsShip.setText(list.getConstructionShipID());
        tvStartTime.setText(list.getStartTime());
        tvEndTime.setText(list.getEndTime());
        etBefore1.setText(String.valueOf(list.getBeforeOverSandDraft1()));
        etBefore2.setText(String.valueOf(list.getBeforeOverSandDraft2()));
        etBefore3.setText(String.valueOf(list.getBeforeOverSandDraft3()));
        etBefore4.setText(String.valueOf(list.getBeforeOverSandDraft4()));

        etAfter1.setText(String.valueOf(list.getAfterOverSandDraft1()));
        etAfter2.setText(String.valueOf(list.getAfterOverSandDraft2()));
        etAfter3.setText(String.valueOf(list.getAfterOverSandDraft3()));
        etAfter4.setText(String.valueOf(list.getAfterOverSandDraft4()));

        etActualAmountSand.setText(String.valueOf(list.getActualAmountOfSand()));
        radiobtnFinishRecorde.setChecked(list.getIsFinish() == 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
