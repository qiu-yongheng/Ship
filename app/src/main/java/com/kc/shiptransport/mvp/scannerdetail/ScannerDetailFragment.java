package com.kc.shiptransport.mvp.scannerdetail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImagePathBean;
import com.kc.shiptransport.db.ScannerImage;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.view.actiivty.ImageSelectActivity;

import java.util.List;

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
    private ScannerDetailActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViews(view);
        initListener();
        // TODO
        presenter.start(activity.position);
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
        setHasOptionsMenu(true);
        activity = (ScannerDetailActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        // TODO 回显数据(从数据库获取)
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
        ImageSelectActivity.startActivity(getActivity(), "gaga", 1);
    }

    /**
     * 显示标题
     * @param title
     */
    @Override
    public void showTitle(String title) {
        activity.getSupportActionBar().setTitle(title);
    }

    @Override
    public void showLoading(boolean isShow) {
        if (isShow) {
            activity.showProgressDailog(getResources().getString(R.string.dialog_loading), getResources().getString(R.string.dialog_loading), new OnDailogCancleClickListener() {
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

    /**
     * 根据itemID获取数据 (数据库, 网络请求)
     * @param scannerImage
     */
    @Override
    public void showDatas(ScannerImage scannerImage) {
        /** 更新界面item显示 */

        // 装舱现场照片
        String stowage = scannerImage.getStowage();
        setItemData(stowage, tvScanner11, tvScanner12, "3");

        // 舱单
        String ship_bill = scannerImage.getShip_bill();
        setItemData(ship_bill, tvScanner21, tvScanner22, "1");

        // 托运单
        String consignment_bill = scannerImage.getConsignment_bill();
        setItemData(consignment_bill, tvScanner31, tvScanner32, "1");

        // 碎石粉装创记录表
        String gravel = scannerImage.getGravel();
        setItemData(gravel, tvScanner41, tvScanner42, "1");

        // 选择计划航线图
        String strip_plot_select = scannerImage.getStrip_plot_select();
        setItemData(strip_plot_select, tvScanner51, tvScanner52, "1");

        // 计划航线图
        String strip_plot = scannerImage.getStrip_plot();
        setItemData(strip_plot, tvScanner61, tvScanner62, "2");

        // 送货单
        String delivery_bill = scannerImage.getDelivery_bill();
        setItemData(delivery_bill, tvScannerRight11, tvScannerRight12, "1");

        // 预验收质量记录表
        String qc = scannerImage.getQc();
        setItemData(qc, tvScannerRight21, tvScannerRight22, "1");

        // 海关放行通知照片
        String customs = scannerImage.getCustoms();
        setItemData(customs, tvScannerRight31, tvScannerRight32, "1");

        // 航线对比图
        String contrast = scannerImage.getContrast();
        setItemData(contrast, tvScannerRight41, tvScannerRight42, "1");

        // 海关单
        String customs_bill = scannerImage.getCustoms_bill();
        setItemData(customs_bill, tvScannerRight51, tvScannerRight52, "1");
    }

    /**
     *
     * @param data
     * @param view_1
     * @param view_2
     * @param num
     */
    public void setItemData (String data, TextView view_1, TextView view_2, String num) {
        if (TextUtils.isEmpty(data)) {
            view_1.setText("(" + num + "/0)");
            view_2.setText(R.string.text_scan_not);
        } else {
            List<ScannerImagePathBean> list = new Gson().fromJson(data, new TypeToken<List<ScannerImagePathBean>>() {}.getType());
            view_1.setText("(" + num + "/" + String.valueOf(list.size()) + ")");
            if (list.size() == 1) {
                view_2.setText(R.string.text_scan_alr);
            } else {
                view_2.setText(R.string.text_scan_not);
            }
        }
    }
}
