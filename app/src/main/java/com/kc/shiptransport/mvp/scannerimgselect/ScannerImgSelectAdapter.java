package com.kc.shiptransport.mvp.scannerimgselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.ScannerImgListByTypeBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.LogUtil;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/7/1 13:41
 * @desc ${TODO}
 */

public class ScannerImgSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    public List<ScannerImgListByTypeBean> list;
    private OnRecyclerviewItemClickListener listener;

    public ScannerImgSelectAdapter(Context context, List<ScannerImgListByTypeBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalHolder(inflater.inflate(R.layout.item_normal, parent, false));
            case TYPE_ADD:
                return new AddHolder(inflater.inflate(R.layout.item_add, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            // 获取图片地址, 显示
            ScannerImgListByTypeBean scannerImgListByTypeBean = list.get(position);

            String fileName = scannerImgListByTypeBean.getFileName();

            String[] split = fileName.split("\\.");
            String spnme = split[split.length - 1];

            LogUtil.d("扫描件类型: " + fileName);

            if (spnme.equals("pdf") || spnme.equals("PDF")) {
                //显示PDF图片
                RxGalleryUtil.showImage(context, R.mipmap.ic_pdf, null, null, ((NormalHolder) holder).mIvNormal);

                // 预览PDF
                ((NormalHolder) holder).mIvNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 1);
                        }
                    }
                });
            } else {
                // 显示图片
                RxGalleryUtil.showImage(context, scannerImgListByTypeBean.getFilePath(), null, null, ((NormalHolder) holder).mIvNormal);


                // 点击图片, 预览图片
                ((NormalHolder) holder).mIvNormal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 0);
                        }
                    }
                });
            }


            // 点击 X 删除图片 (发送网络请求)
            ((NormalHolder) holder).mIvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.ITEM_DELETE);
                    }
                }
            });


        } else if (holder instanceof AddHolder) {
            ((AddHolder) holder).mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 弹出图片选择器
                    if (listener != null) {
                        listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    }
                }
            });
        }
    }

    public void setDates(List<ScannerImgListByTypeBean> list) {
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
