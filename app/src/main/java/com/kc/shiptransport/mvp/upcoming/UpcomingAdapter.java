package com.kc.shiptransport.mvp.upcoming;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.backlog.BackLog;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * @author 邱永恒
 * @time 2017/9/8  17:30
 * @desc 待办
 */

public class UpcomingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<BackLog> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public UpcomingAdapter(Context context, List<BackLog> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(inflate.inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        BackLog backLog = list.get(position);
        ((ItemHolder) holder).textView.setText(backLog.getPendingType());
        ((ItemHolder) holder).badge.setBadgeNumber(backLog.getList().size());
        ImageView imageView = (ImageView) ((ItemHolder) holder).badge.getTargetView();
        imageView.setImageResource(R.mipmap.plan);
        ((ItemHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }

    public void setDates(List<BackLog> list) {
        this.list = list;
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Badge badge;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_home_item);
            badge = new QBadgeView(context).bindTarget(itemView.findViewById(R.id.iv_home_item));
            badge.setBadgeTextSize(14, true);
            badge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (dragState == STATE_SUCCEED) {
                        Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
