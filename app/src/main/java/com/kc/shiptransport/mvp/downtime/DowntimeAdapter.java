package com.kc.shiptransport.mvp.downtime;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.down.StopOption;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/6  19:44
 * @desc ${TODD}
 */

public class DowntimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<StopOption> list;
    private final LayoutInflater inflate;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_HEADER = 1;
    private OnRecyclerviewItemClickListener listener;
    private int checkedIndex = -1;

    public DowntimeAdapter(Context context, List<StopOption> list) {
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalHolder(inflate.inflate(R.layout.item_stop_normal, parent, false));
        }
        return new HeardHolder(inflate.inflate(R.layout.item_stop_head, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        StopOption stopOption = list.get(position);
        if (holder instanceof NormalHolder) {
            ((NormalHolder) holder).mCheckBox.setText(stopOption.getName());

            ((NormalHolder) holder).mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        // 把之前选中的position传递出去,
                        listener.onItemClick(holder.itemView, holder.getLayoutPosition(), checkedIndex);
                        // 保存当前选中的checkbox
                        checkedIndex = holder.getLayoutPosition();
                    } else {

                    }
                }
            });

            if (checkedIndex == position) {
                ((NormalHolder) holder).mCheckBox.setChecked(true);
            } else {
                ((NormalHolder) holder).mCheckBox.setChecked(false);
            }

        } else if (holder instanceof HeardHolder) {
            ((HeardHolder) holder).mTvStopName.setText(stopOption.getOptionType());
        }
    }

    public void setDates(List<StopOption> list) {
        this.list = list;
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final CheckBox mCheckBox;

        public NormalHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    class HeardHolder extends RecyclerView.ViewHolder {

        private final TextView mTvStopName;

        public HeardHolder(View itemView) {
            super(itemView);
            mTvStopName = (TextView) itemView.findViewById(R.id.tv_stop_name);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 获取类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0 || !list.get(position).getOptionType().equals(list.get(position - 1).getOptionType())) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 根据type, 修改item宽度
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            /** span size表示一个item的跨度，跨度了多少个span */
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (viewType == TYPE_HEADER) {
                        /** 如果是标题, 设置宽度占满所有span */
                        return gridLayoutManager.getSpanCount();
                    }

                    if (spanSizeLookup != null) {
                        /** 普通item, 设置宽度是一个span的宽度 */
                        return spanSizeLookup.getSpanSize(position);
                    }

                    return 1;
                }
            });

            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }
}
