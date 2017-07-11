package com.kc.shiptransport.mvp.downtimelog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.threadsandlog.ThreadSandLogBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/10  17:08
 * @desc ${TODD}
 */

public class ThreadLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<ThreadSandLogBean> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public ThreadLogAdapter(Context context, List<ThreadSandLogBean> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_thread_sand_log, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ThreadSandLogBean logBean = list.get(position);

        ((NormalHolder)holder).mTvShipAccount.setText(logBean.getShipName());
        ((NormalHolder)holder).mTvShipItemNum.setText(logBean.getShipItemNum());
        ((NormalHolder)holder).mTvPartition.setText(logBean.getPartitionNameArr());
        ((NormalHolder)holder).mTvLayer.setText(logBean.getLayerName());
        ((NormalHolder)holder).mTvQuantity.setText(logBean.getQuantity() + "m³");
        ((NormalHolder)holder).mTvRemark.setText(logBean.getRemark());



        ((NormalHolder)holder).mTvStartTime.setText(logBean.getStartTime());
        ((NormalHolder)holder).mTvEndTime.setText(logBean.getEndTime());
        ((NormalHolder)holder).mTvSystemTime.setText(logBean.getSystemDate());

        ((NormalHolder)holder).mTvCreator.setText(logBean.getCreatorName());

        ((NormalHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到详情界面
                listener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public void setDates(List<ThreadSandLogBean> list) {
        this.list = list;
    }


    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvShipAccount;
        private final TextView mTvStartTime;
        private final TextView mTvEndTime;
        private final TextView mTvSystemTime;
        private final TextView mTvShipItemNum;
        private final TextView mTvPartition;
        private final TextView mTvLayer;
        private final TextView mTvQuantity;
        private final TextView mTvRemark;
        private final TextView mTvCreator;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvShipAccount = (TextView) itemView.findViewById(R.id.text_ship_account);
            mTvShipItemNum = (TextView) itemView.findViewById(R.id.text_ship_item_num);
            mTvPartition = (TextView) itemView.findViewById(R.id.tv_partition);
            mTvLayer = (TextView) itemView.findViewById(R.id.text_layer);
            mTvQuantity = (TextView) itemView.findViewById(R.id.text_quantity);
            mTvRemark = (TextView) itemView.findViewById(R.id.text_remark);

            mTvStartTime = (TextView) itemView.findViewById(R.id.text_start_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
            mTvSystemTime = (TextView) itemView.findViewById(R.id.text_system_time);
            mTvCreator = (TextView) itemView.findViewById(R.id.text_creator);


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
