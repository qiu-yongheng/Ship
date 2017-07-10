package com.kc.shiptransport.mvp.downtimelog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.ConstructionBoat;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/7 21:26
 * @desc ${TODO}
 */

public class ConstructionBoatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<ConstructionBoat> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public ConstructionBoatAdapter(Context context, List<ConstructionBoat> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_thread_sand, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ConstructionBoat boat = list.get(position);

        ((NormalHolder)holder).mTv.setText(boat.getShipName());

        ((NormalHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public void setDates(List<ConstructionBoat> list) {
        this.list = list;
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public NormalHolder(View itemView) {
            super(itemView);
            mTv = (TextView)itemView.findViewById(R.id.tv);
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
