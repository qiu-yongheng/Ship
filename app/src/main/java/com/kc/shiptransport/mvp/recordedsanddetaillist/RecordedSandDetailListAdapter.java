package com.kc.shiptransport.mvp.recordedsanddetaillist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordedSandShowList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/20  14:03
 * @desc ${TODD}
 */

class RecordedSandDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<RecordedSandShowList> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public RecordedSandDetailListAdapter(Context context, List<RecordedSandShowList> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_recorded, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        RecordedSandShowList showList = list.get(position);
        /** 施工船 */
        ((NormalHolder)holder).mTvShipName.setText(showList.getConstructionShipName());
        ((NormalHolder)holder).mTvStartTime.setText(showList.getStartTime());
        ((NormalHolder)holder).mTvEndTime.setText(showList.getEndTime());
        ((NormalHolder)holder).mTvSand.setText(String.valueOf(showList.getActualAmountOfSand()));

        /** 点击跳转到编辑界面(修改) */
        ((NormalHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvShipName;
        private final TextView mTvStartTime;
        private final TextView mTvEndTime;
        private final TextView mTvSand;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvShipName = (TextView) itemView.findViewById(R.id.tv);
            mTvStartTime = (TextView) itemView.findViewById(R.id.tv_start_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.tv_end_time);
            mTvSand = (TextView) itemView.findViewById(R.id.tv_sand);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDates(List<RecordedSandShowList> list) {
        this.list = list;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
