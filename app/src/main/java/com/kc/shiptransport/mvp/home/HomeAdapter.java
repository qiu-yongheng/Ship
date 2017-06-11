package com.kc.shiptransport.mvp.home;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AppList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/6 21:03
 * @desc ${TODO}
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final Integer[] icon;
    private final String[] tag;
    private OnRecyclerviewItemClickListener listener;

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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder) holder).mIvHomeItem.setImageResource(icon[position]);
        ((ItemHolder) holder).mTvHomeItem.setText(tag[position]);

        // 未开发的功能, 图标显示灰色
        if (position != 1) {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0); // 设置饱和度
            ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
            ((ItemHolder) holder).mIvHomeItem.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可
        } else {
            ((ItemHolder) holder).mIvHomeItem.setColorFilter(null); // 如果想恢复彩色显示，设置为null即可
        }

        // 权限设置, 设置要显示的模块
        List<AppList> appLists = DataSupport.where("AppID = ?", String.valueOf(position + 1)).find(AppList.class);
        if (appLists != null && !appLists.isEmpty()) {
            ((ItemHolder) holder).itemView.setVisibility(View.VISIBLE);
        } else {
            ((ItemHolder) holder).itemView.setVisibility(View.GONE);
        }

        // 设置点击事件
        ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
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

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
