package com.kc.shiptransport.mvp.scannerdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author qiuyongheng
 * @time 2017/6/14  8:57
 * @desc ${TODD}
 */

public class ScannerDetailFragment extends Fragment implements ScannerDetailContract.View, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_scanner_1)
    TextView tvScanner1;
    @BindView(R.id.tv_scanner_1_1)
    TextView tvScanner11;
    @BindView(R.id.tv_scanner_1_2)
    TextView tvScanner12;
    @BindView(R.id.tv_scanner_2)
    TextView tvScanner2;
    @BindView(R.id.tv_scanner_2_1)
    TextView tvScanner21;
    @BindView(R.id.tv_scanner_2_2)
    TextView tvScanner22;
    @BindView(R.id.tv_scanner_3)
    TextView tvScanner3;
    @BindView(R.id.tv_scanner_3_1)
    TextView tvScanner31;
    @BindView(R.id.tv_scanner_3_2)
    TextView tvScanner32;
    @BindView(R.id.tv_scanner_4)
    TextView tvScanner4;
    @BindView(R.id.tv_scanner_4_1)
    TextView tvScanner41;
    @BindView(R.id.tv_scanner_4_2)
    TextView tvScanner42;
    @BindView(R.id.tv_scanner_5)
    TextView tvScanner5;
    @BindView(R.id.tv_scanner_5_1)
    TextView tvScanner51;
    @BindView(R.id.tv_scanner_5_2)
    TextView tvScanner52;
    @BindView(R.id.tv_scanner_6)
    TextView tvScanner6;
    @BindView(R.id.tv_scanner_6_1)
    TextView tvScanner61;
    @BindView(R.id.tv_scanner_6_2)
    TextView tvScanner62;
    @BindView(R.id.tv_scanner_right_1)
    TextView tvScannerRight1;
    @BindView(R.id.tv_scanner_right_1_1)
    TextView tvScannerRight11;
    @BindView(R.id.tv_scanner_right_1_2)
    TextView tvScannerRight12;
    @BindView(R.id.tv_scanner_right_2)
    TextView tvScannerRight2;
    @BindView(R.id.tv_scanner_right_2_1)
    TextView tvScannerRight21;
    @BindView(R.id.tv_scanner_right_2_2)
    TextView tvScannerRight22;
    @BindView(R.id.tv_scanner_right_3)
    TextView tvScannerRight3;
    @BindView(R.id.tv_scanner_right_3_1)
    TextView tvScannerRight31;
    @BindView(R.id.tv_scanner_right_3_2)
    TextView tvScannerRight32;
    @BindView(R.id.tv_scanner_right_4)
    TextView tvScannerRight4;
    @BindView(R.id.tv_scanner_right_4_1)
    TextView tvScannerRight41;
    @BindView(R.id.tv_scanner_right_4_2)
    TextView tvScannerRight42;
    @BindView(R.id.tv_scanner_right_5)
    TextView tvScannerRight5;
    @BindView(R.id.tv_scanner_right_5_1)
    TextView tvScannerRight51;
    @BindView(R.id.tv_scanner_right_5_2)
    TextView tvScannerRight52;
    @BindView(R.id.tv_scanner_right_6)
    TextView tvScannerRight6;
    @BindView(R.id.tv_scanner_right_6_1)
    TextView tvScannerRight61;
    @BindView(R.id.tv_scanner_right_6_2)
    TextView tvScannerRight62;
    Unbinder unbinder;
    @BindView(R.id.cardview_1)
    CardView cardview1;
    @BindView(R.id.cardview_2)
    CardView cardview2;
    @BindView(R.id.cardview_3)
    CardView cardview3;
    @BindView(R.id.cardview_4)
    CardView cardview4;
    @BindView(R.id.cardview_5)
    CardView cardview5;
    @BindView(R.id.cardview_6)
    CardView cardview6;
    @BindView(R.id.cardview_right_1)
    CardView cardviewRight1;
    @BindView(R.id.cardview_right_2)
    CardView cardviewRight2;
    @BindView(R.id.cardview_right_3)
    CardView cardviewRight3;
    @BindView(R.id.cardview_right_4)
    CardView cardviewRight4;
    @BindView(R.id.cardview_right_5)
    CardView cardviewRight5;
    @BindView(R.id.cardview_right_6)
    CardView cardviewRight6;
    private ScannerDetailContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        return view;
    }

    private void initListener() {
        cardview1.setOnClickListener(this);
        cardview2.setOnClickListener(this);
        cardview3.setOnClickListener(this);
        cardview4.setOnClickListener(this);
        cardview5.setOnClickListener(this);
        cardview6.setOnClickListener(this);

        cardviewRight1.setOnClickListener(this);
        cardviewRight2.setOnClickListener(this);
        cardviewRight3.setOnClickListener(this);
        cardviewRight4.setOnClickListener(this);
        cardviewRight5.setOnClickListener(this);
    }

    @Override
    public void initViews(View view) {
        // 给item设置值
        tvScanner1.setText(R.string.scanner_stowage);

        tvScanner2.setText(R.string.scanner_ship_bill);

        tvScanner3.setText(R.string.scanner_consignment_bill);

        tvScanner4.setText(R.string.scanner_gravel);

        tvScanner5.setText(R.string.scanner_strip_plot);

        tvScanner6.setText(R.string.scanner_strip_plot_plan);

        tvScannerRight1.setText(R.string.scanner_delivery_bill);

        tvScannerRight2.setText(R.string.scanner_qc);

        tvScannerRight3.setText(R.string.scanner_customs);

        tvScannerRight4.setText(R.string.scanner_contrast);

        tvScannerRight5.setText(R.string.scanner_customs_bill);
    }

    @Override
    public void setPresenter(ScannerDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 处理点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cardview_1:
                break;
            case R.id.cardview_2:
                break;
            case R.id.cardview_3:
                break;
            case R.id.cardview_4:
                break;
            case R.id.cardview_5:
                break;
            case R.id.cardview_6:
                break;

            case R.id.cardview_right_1:
                break;
            case R.id.cardview_right_2:
                break;
            case R.id.cardview_right_3:
                break;
            case R.id.cardview_right_4:
                break;
            case R.id.cardview_right_5:
                break;
        }
    }
}
