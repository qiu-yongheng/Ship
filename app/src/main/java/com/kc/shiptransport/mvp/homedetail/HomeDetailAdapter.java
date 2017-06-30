package com.kc.shiptransport.mvp.homedetail;

import android.content.Context;
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

public class HomeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<AppList> list;
    private OnRecyclerviewItemClickListener listener;

    public HomeDetailAdapter(Context context, List<AppList> list) {
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

        // 设置点击事件
        ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition(), Integer.valueOf(appList.getAppID()));
            }
        });

        //        // 权限管理, 设置要显示的模块
        //        List<AppList> appLists = DataSupport.where("SortNum = ? and AppPID = ?", String.valueOf(Double.valueOf(position + 1)), String.valueOf(2)).find(AppList.class);
        //        if (appLists != null && !appLists.isEmpty()) {
        //            // 需要显示的功能
        //
        //            ((ItemHolder) holder).mIvHomeItem.setColorFilter(null); // 如果想恢复彩色显示，设置为null即可
        //
        //            // 设置点击事件
        //            ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View view) {
        //                    listener.onItemClick(holder.itemView, holder.getLayoutPosition());
        //                }
        //            });
        //        } else {
        //            // 没有权限使用的功能
        //
        //            ColorMatrix cm = new ColorMatrix();
        //            cm.setSaturation(0); // 设置饱和度
        //            ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
        //            ((ItemHolder) holder).mIvHomeItem.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可
        //
        //            // 点击提示没有权限使用, 懒得添加接口啦
        //            ((ItemHolder) holder).mIvHomeItem.setOnClickListener(new View.OnClickListener() {
        //                @Override
        //                public void onClick(View view) {
        //                    listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
        //                }
        //            });
        //        }


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

    public void setDates(List<AppList> list) {
        this.list = list;
    }
}
