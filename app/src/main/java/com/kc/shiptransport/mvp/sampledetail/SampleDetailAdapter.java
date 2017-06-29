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
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
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
    // 全部数据
    public SampleShowDatesBean sampleShowDates;
    // 数据列表
    public List<SampleShowDatesBean.SandSamplingNumRecordListBean> sandSamplingNumRecordList;
    private OnRecyclerviewItemClickListener listener;

    public SampleDetailAdapter(Context context, SampleShowDatesBean sampleShowDates) {
        this.context = context;
        this.sampleShowDates = sampleShowDates;
        // 取样数据
        this.sandSamplingNumRecordList = sampleShowDates.getSandSamplingNumRecordList();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 获取取样编号数据
        SampleShowDatesBean.SandSamplingNumRecordListBean numRecordListBean = sandSamplingNumRecordList.get(position);

        // 获取取样编号内的图片
        List<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean> imageList = numRecordListBean.getSandSamplingAttachmentRecordList();

        // 显示图片
        RxGalleryUtil.showImage(context, (imageList.get(0).getFilePath() == null ? "" : imageList.get(0).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage1);
        RxGalleryUtil.showImage(context, (imageList.get(1).getFilePath() == null ? "" : imageList.get(1).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage2);
        ((NormalHolder) holder).mTvsamplenum.setText((numRecordListBean.getSamplingNum() == null || numRecordListBean.getSamplingNum().equals("0")) ? "" : numRecordListBean.getSamplingNum());


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

    public void setDates(SampleShowDatesBean sampleShowDates) {
        this.sampleShowDates = sampleShowDates;
        // 取样数据
        this.sandSamplingNumRecordList = sampleShowDates.getSandSamplingNumRecordList();
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
        return sandSamplingNumRecordList.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void addData(int pos) {
//        SampleRecordListBean sampleRecordList = new SampleRecordListBean();
//        sampleRecordList.setItemID(itemID);
//
//        list.add(pos, sampleRecordList);
//        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        // 从集合中删除
//        list.remove(pos);
//        notifyItemRemoved(pos);
    }
}
