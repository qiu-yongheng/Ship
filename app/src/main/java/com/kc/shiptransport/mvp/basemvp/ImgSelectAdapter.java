package com.kc.shiptransport.mvp.basemvp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/1 13:41
 * @desc 图片选择Adapter
 */

public class ImgSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final LayoutInflater inflater;
    /**
     * item类型: 文字 + 图片
     */
    private static final int TYPE_NORMAL = 0;
    /**
     * item类型: footer，加载更多
     */
    private static final int TYPE_ADD = 1;
    private boolean isCanChange;
    public List<ImgList> list;
    private OnRecyclerviewItemClickListener listener;

    /**
     * 构造器
     * @param context 上下文
     * @param list 数据源集合
     * @param isCanChange 是否可以修改图片 true显示添加图片和删除图片UI  false不显示添加图片和删除图片的UI
     */
    public ImgSelectAdapter(Context context, List<ImgList> list, boolean isCanChange) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.isCanChange = isCanChange;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalHolder(inflater.inflate(R.layout.item_img, parent, false));
            case TYPE_ADD:
                return new AddHolder(inflater.inflate(R.layout.item_add, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            // 获取图片地址, 显示
            ImgList bean = list.get(position);
            RxGalleryUtil.showImage(context, bean.getPath(), null, null, ((NormalHolder) holder).mIvImg);

            // 点击图片, 预览图片
            ((NormalHolder) holder).mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 0);
                }
            });

            // 是否可以显示
            ((NormalHolder) holder).mIvDelete.setVisibility(isCanChange ? View.VISIBLE : View.GONE);
            // 点击 X 删除图片 (发送网络请求)
            ((NormalHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.ITEM_DELETE);
                }
            });


        } else if (holder instanceof AddHolder) {
            ((AddHolder) holder).mBtnAdd.setVisibility(isCanChange ? View.VISIBLE : View.GONE);
            ((AddHolder) holder).mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 弹出图片选择器
                    listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    public void setDatas(List<ImgList> list) {
        this.list = list;
    }

    public List<ImgList> getDatas() {
        return list;
    }

    public void setIsCanChange(boolean isCanChange) {
        this.isCanChange = isCanChange;
    }

    public boolean getIsCanChange() {
        return isCanChange;
    }

    /**
     * 显示图片
     */
    class NormalHolder extends RecyclerView.ViewHolder {

        private final ImageView mIvImg;
        private final ImageView mIvDelete;

        public NormalHolder(View itemView) {
            super(itemView);
            mIvImg = (ImageView) itemView.findViewById(R.id.iv_img);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

    /**
     * 添加图片
     */
    class AddHolder extends RecyclerView.ViewHolder {

        private final ImageButton mBtnAdd;

        public AddHolder(View itemView) {
            super(itemView);
            mBtnAdd = (ImageButton) itemView.findViewById(R.id.btn_add);
        }
    }

    /**
     * 获取item类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_ADD;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    /**
     * 设置点击事件
     */
    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 删除条目, 解决刷新错乱, 数组越界问题
     * @param position
     */
    public void removeItem(int position) {
        getDatas().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    /**
     * 加载更多数据
     * @param datas
     */
    public void loadmore(List<ImgList> datas) {
        getDatas().addAll(datas);
        notifyDataSetChanged();
    }

}
