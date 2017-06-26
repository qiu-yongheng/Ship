package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.SampleRecordListBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/17  15:13
 * @desc ${TODD}
 */

public class SampleDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    public final List<SampleRecordListBean> list;
    private final int itemID;
    private OnRecyclerviewItemClickListener listener;

    public SampleDetailAdapter(Context context, List<SampleRecordListBean> list, int itemID) {
        this.context = context;
        this.list = list;
        this.itemID = itemID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 从集合中获取图片显示, 如果没有图片, 点击后跳转到图片选择界面, 如果有图片, 跳转到预览界面
        // 单选图片后, 保存地址到集合中, 保存到数据库, 记录position

        // 显示图片
        final SampleRecordListBean sampleRecordList = list.get(position);
        RxGalleryUtil.showImage(context, sampleRecordList.getImage_1(), null, null, ((NormalHolder) holder).mBtnImage1);
        RxGalleryUtil.showImage(context, sampleRecordList.getImage_2(), null, null, ((NormalHolder) holder).mBtnImage2);
        ((NormalHolder) holder).mTvsamplenum.setText(sampleRecordList.getSample_num() == null ? "" : sampleRecordList.getSample_num());


        /* 点击后单选图片 */
        ((NormalHolder) holder).mBtnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断取样编号是否填写
                String str = ((NormalHolder) holder).mTvsamplenum.getText().toString().trim();
                if (TextUtils.isEmpty(str) || str.equals("请填写")) {
                    Toast.makeText(context, "请先填写取样编号", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onItemClick(((NormalHolder) holder).mBtnImage1, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_1);
                }

            }
        });

        /* 点击后单选图片 */
        ((NormalHolder) holder).mBtnImage2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 判断取样编号是否填写
                String str = ((NormalHolder) holder).mTvsamplenum.getText().toString().trim();
                if (TextUtils.isEmpty(str) || str.equals("请填写")) {
                    Toast.makeText(context, "请先填写取样编号", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onItemClick(((NormalHolder) holder).mBtnImage2, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_2);
                }
            }
        });

        /* 点击编辑验砂取样码 */
        ((NormalHolder) holder).mTvsamplenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(((NormalHolder) holder).mTvsamplenum, holder.getLayoutPosition(), SettingUtil.HOLDER_NUM);
            }
        });

        /* 长按删除 */
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                return false;
            }
        });
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvsamplenum;
        private final ImageButton mBtnImage1;
        private final ImageButton mBtnImage2;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvsamplenum = (TextView) itemView.findViewById(R.id.tv_sample_num);
            mBtnImage1 = (ImageButton) itemView.findViewById(R.id.btn_image_1);
            mBtnImage2 = (ImageButton) itemView.findViewById(R.id.btn_image_2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void addData(int pos) {
        SampleRecordListBean sampleRecordList = new SampleRecordListBean();
        sampleRecordList.setItemID(itemID);

        list.add(pos, sampleRecordList);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        // 从集合中删除
        list.remove(pos);
        notifyItemRemoved(pos);
    }
}
