package com.kc.shiptransport.mvp.exitapplicationassessor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.kc.shiptransport.R;
import com.kc.shiptransport.db.exitapplication.ExitDetail;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/1 13:41
 * @desc ${TODO}
 */

public class ExitApplicationAssessorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    /**
     * item类型: 图片
     */
    private static final int TYPE_NORMAL = 0;
    /**
     * item类型: 添加图片
     */
    private static final int TYPE_ADD = 1;
    public List<ExitDetail.AttachmentListBean> list;
    private OnRecyclerviewItemClickListener listener;

    public ExitApplicationAssessorAdapter(Context context, List<ExitDetail.AttachmentListBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflater.inflate(R.layout.item_normal, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 获取图片地址, 显示
        ExitDetail.AttachmentListBean listBean = list.get(position);
        RxGalleryUtil.showImage(context, listBean.getFilePath(), null, null, ((NormalHolder) holder).mIvNormal);

        ((NormalHolder) holder).mIvDelete.setVisibility(View.GONE);

        // 点击图片, 预览图片
        ((NormalHolder) holder).mIvNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 0);
            }
        });

        // 点击 X 删除图片 (发送网络请求)
        ((NormalHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.ITEM_DELETE);
            }
        });


    }

    public void setDates(List<ExitDetail.AttachmentListBean> list) {
        this.list = list;
    }

    /**
     * 显示图片
     */
    class NormalHolder extends RecyclerView.ViewHolder {

        private final ImageView mIvNormal;
        private final ImageView mIvDelete;

        public NormalHolder(View itemView) {
            super(itemView);
            mIvNormal = (ImageView) itemView.findViewById(R.id.iv_normal);
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
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 设置点击事件
     */
    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void add(String path) {

        notifyDataSetChanged();
    }

    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int position, String path) {

        notifyDataSetChanged();
    }

    public String tojson() {
        return new Gson().toJson(list);
    }
}
