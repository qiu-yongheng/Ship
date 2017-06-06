package com.kc.shiptransport.mvp.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.shiptransport.R;

/**
 * @author 邱永恒
 * @time 2017/6/6 21:03
 * @desc ${TODO}
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final Integer[] icon;
    private final String[] tag;

    public HomeAdapter(Context context, Integer[] icon, String[] tag) {
        this.context = context;
        this.icon = icon;
        this.tag = tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //((ItemHolder) holder).mIvHomeItem.setImageResource(icon[position]);
        ((ItemHolder) holder).mTvHomeItem.setText(tag[position]);
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private final ImageView mIvHomeItem;
        private final TextView mTvHomeItem;

        public ItemHolder(View itemView) {
            super(itemView);
            mIvHomeItem = (ImageView) itemView.findViewById(R.id.iv_home_item);
            mTvHomeItem = (TextView) itemView.findViewById(R.id.tv_home_item);
        }
    }

    @Override
    public int getItemCount() {
        return tag.length;
    }
}
