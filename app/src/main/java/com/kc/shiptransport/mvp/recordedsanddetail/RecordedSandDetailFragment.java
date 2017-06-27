package com.kc.shiptransport.mvp.recordedsanddetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/19  11:28
 * @desc ${TODD}
 */

public class RecordedSandDetailFragment extends Fragment implements RecordedSandDetailContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_supply_ship)
    TextView tvSupplyShip;
    @BindView(R.id.rl_record_supply_ship)
    RelativeLayout rlRecordSupplyShip;
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
    EditText etBefore1;
    @BindView(R.id.et_before_2)
    EditText etBefore2;
    @BindView(R.id.et_before_3)
    EditText etBefore3;
    @BindView(R.id.et_before_4)
    EditText etBefore4;
    @BindView(R.id.et_after_1)
    EditText etAfter1;
    @BindView(R.id.et_after_2)
    EditText etAfter2;
    @BindView(R.id.et_after_3)
    EditText etAfter3;
    @BindView(R.id.et_after_4)
    EditText etAfter4;
    @BindView(R.id.radiobtn_finish_recorde)
    CheckBox radiobtnFinishRecorde;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.btn_return)
    Button btnReturn;
    Unbinder unbinder;
    @BindView(R.id.sp_receive_ship)
    Spinner spReceiveShip;
    private RecordedSandDetailActivity activity;
    private RecordedSandDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordedsand_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        presenter.subscribe();
        return view;
    }

    private void initListener() {
        // 开始时间
        rlRecordStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvStartTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });

        // 结束时间
        rlRecordEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarUtil.showTimePickerDialog(getContext(), tvEndTime, new OnTimePickerSureClickListener() {
                    @Override
                    public void onSure(String str) {

                    }
                });
            }
        });
    }

    @Override
    public void initViews(View view) {
        setHasOptionsMenu(true);
        activity = (RecordedSandDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(R.string.recorded_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(RecordedSandDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 显示spinner
     * @param list
     */
    @Override
    public void showSpinner(List<String> list) {
        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spReceiveShip.setAdapter(arr_adapter);
        // 根据上一个界面传过来的position设置当前显示的item
        //        mSpMaterialOrdar.setSelection(0);

        // 点击后, 筛选分包商的数据
        spReceiveShip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    // TODO 保存数据
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showShip() {

    }
}
