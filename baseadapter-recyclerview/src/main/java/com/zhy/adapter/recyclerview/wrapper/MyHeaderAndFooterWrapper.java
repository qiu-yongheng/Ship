package com.zhy.adapter.recyclerview.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.base.ViewHolder;


/**
 * @author 邱永恒
 * @time 16/6/23  12:17
 * @desc 装饰者模式, 给传入的adapter添加头或尾
 */
public class MyHeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    /**
     * SparseArrayCompat : map集合, key只能为int类型
     */
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private RecyclerView.Adapter mInnerAdapter;
    private OnHeadAndFootClickListener listener;

    public MyHeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    /**
     * 创建viewHolder
     * <p>
     * 1. 判断是否是header
     * 2. 判断是否是foot
     * 3. 如果都不是, 返回adapter的viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 获取控件类型
     * <p>
     * 1. 判断是否是header
     * 2. 判断是否是foot
     * 3. 如果不是header与foot, 返回传入adapter的viewHolder
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, holder, holder.getAdapterPosition());
                    }
                }
            });
            return;
        }
        if (isFooterViewPos(position)) {
            Log.d("==", "设置foot点击事件");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("==", "点击foot");
                    listener.onItemClick(v, holder, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("==", "长按");
                    return false;
                }
            });
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    /**
     * 获取控件长度
     * <p>
     * header长度 + foot长度 + 传入adapter长度
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }


    /**
     * 判断position对应的view是否header
     *
     * @param position
     * @return
     */
    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    /**
     * 判断position对应的view是否foot
     *
     * @param position
     * @return
     */
    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    /**
     * 添加header到map集合中, key = 固定值 + 当前集合长度; value = view
     * <p>
     * 好处: key不会重复
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    /**
     * 添加foot到map集合中, key = 固定值 + 当前集合长度; value = view
     * <p>
     * 好处: key不会重复
     *
     * @param view
     */
    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    /**
     * 获取header数量
     *
     * @return
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取foot数量
     *
     * @return
     */
    public int getFootersCount() {
        return mFootViews.size();
    }

    /**
     * item点击监听接口
     */
    public interface OnHeadAndFootClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder, int position);
    }

    public void setHeadAndFootClickListener(OnHeadAndFootClickListener listener) {
        this.listener = listener;
    }
}
