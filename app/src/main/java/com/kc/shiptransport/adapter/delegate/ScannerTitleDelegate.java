package com.kc.shiptransport.adapter.delegate;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.analysis.AnalysisDetail;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * @author 邱永恒
 * @time 2017/7/28  10:26
 * @desc ${TODD}
 */

public final class ScannerTitleDelegate implements ItemViewDelegate<AnalysisDetail.PerfectBoatScannerRecordListBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_analysis_recycler;
    }

    @Override
    public boolean isForViewType(AnalysisDetail.PerfectBoatScannerRecordListBean item, int position) {
        return false;
    }

    @Override
    public void convert(ViewHolder holder, AnalysisDetail.PerfectBoatScannerRecordListBean perfectBoatScannerRecordListBean, int position) {

    }
}
