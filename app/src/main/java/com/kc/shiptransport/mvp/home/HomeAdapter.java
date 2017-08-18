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

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/6 21:03
 * @desc ${TODO}
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private OnRecyclerviewItemClickListener listener;
    private List<AppList> list;

    public HomeAdapter(Context context, List<AppList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 获取数据
        final AppList appList = list.get(position);
        ((ItemHolder) holder).mTvHomeItem.setText(appList.getAppName());
        ((ItemHolder) holder).mIvHomeItem.setImageResource(appList.getIcon_id());


        // 未开发的功能, 图标显示灰色
        Integer appID = Integer.valueOf(appList.getAppID());
        if (appID != 2 && appID != 18 && appID != 3 && appID != 9) {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0); // 设置饱和度
            ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
            ((ItemHolder) holder).mIvHomeItem.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可

            // 设置点击事件
            ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), -1);
                }
            });
        } else {
            ((ItemHolder) holder).mIvHomeItem.setColorFilter(null); // 如果想恢复彩色显示，设置为null即可

            // 设置点击事件
            ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), Integer.valueOf(appList.getAppID()));
                }
            });
        }
    }

    public void setDates(List<AppList> list) {
        this.list = list;
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
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
