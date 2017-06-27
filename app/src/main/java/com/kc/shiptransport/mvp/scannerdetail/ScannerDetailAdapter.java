package com.kc.shiptransport.mvp.scannerdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerListBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/27  16:06
 * @desc ${TODD}
 */

public class ScannerDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final List<ScannerListBean> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public ScannerDetailAdapter(Context context, List<ScannerListBean> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_scanner, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ScannerListBean bean = list.get(position);
        ((NormalHolder)holder).mTvItemTitle.setText(bean.getName());
        // TODO 接口添加进度, 状态字段

        // TODO 设置点击事件, 根据进场ID发送网络请求获取图片

    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvItemTitle;
        private final TextView mTvProgress;
        private final TextView mTvState;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvItemTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            mTvProgress = (TextView) itemView.findViewById(R.id.tv_progress);
            mTvState = (TextView) itemView.findViewById(R.id.tv_state);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
