package com.kc.shiptransport.interfaze;

import android.view.View;

/**
 * @author qiuyongheng
 * @time 2017/5/17  16:22
 * @desc ${TODD}
 */

public interface OnRecyclerviewItemClickListener {
    void onItemClick(View view, int position, int... type);
    void onItemLongClick(View view , int position);

}
