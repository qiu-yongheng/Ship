package com.kc.shiptransport.mvp.sampledetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.SampleShowDatesBean;
import com.kc.shiptransport.db.SampleImageList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.RxGalleryUtil;
import com.kc.shiptransport.util.SettingUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
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

        if (!imageList.isEmpty()) {
            // 显示图片
            RxGalleryUtil.showImage(context, (imageList.get(0).getFilePath() == null ? "" : imageList.get(0).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage1);

            if (imageList.size() > 1) {
                RxGalleryUtil.showImage(context, (imageList.get(1).getFilePath() == null ? "" : imageList.get(1).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage2);
            }

            if (imageList.size() > 2) {
                RxGalleryUtil.showImage(context, (imageList.get(2).getFilePath() == null ? "" : imageList.get(2).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage3);
            }

            if (imageList.size() > 3) {
                RxGalleryUtil.showImage(context, (imageList.get(3).getFilePath() == null ? "" : imageList.get(3).getFilePath()), null, null, ((NormalHolder) holder).mBtnImage4);
            }
        }

        // 设置取样编号
        ((NormalHolder) holder).mTvsamplenum.setText((numRecordListBean.getSamplingNum() == null || numRecordListBean.getSamplingNum().equals("0")) ? "" : numRecordListBean.getSamplingNum());

        // 设置施工船舶
        ((NormalHolder) holder).mTvConsShip.setText(numRecordListBean.getConstructionBoatAccountName());

        /* 点击后单选图片 */
        ((NormalHolder) holder).mBtnImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断取样编号是否填写
                String str = ((NormalHolder) holder).mTvsamplenum.getText().toString().trim();
                if (TextUtils.isEmpty(str) || str.equals("请填写")) {
                    Toast.makeText(context, "请先填写取样编号", Toast.LENGTH_SHORT).show();
                } else {
                    if (listener != null) {
                        listener.onItemClick(((NormalHolder) holder).mBtnImage1, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_1);
                    } else {
                        Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                    }
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
                    if (listener != null) {
                        listener.onItemClick(((NormalHolder) holder).mBtnImage2, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_2);
                    } else {
                        Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /* 点击后单选图片 */
        ((NormalHolder) holder).mBtnImage3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 判断取样编号是否填写
                String str = ((NormalHolder) holder).mTvsamplenum.getText().toString().trim();
                if (TextUtils.isEmpty(str) || str.equals("请填写")) {
                    Toast.makeText(context, "请先填写取样编号", Toast.LENGTH_SHORT).show();
                } else {
                    if (listener != null) {
                        listener.onItemClick(((NormalHolder) holder).mBtnImage3, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_3);
                    } else {
                        Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /* 点击后单选图片 */
        ((NormalHolder) holder).mBtnImage4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // 判断取样编号是否填写
                String str = ((NormalHolder) holder).mTvsamplenum.getText().toString().trim();
                if (TextUtils.isEmpty(str) || str.equals("请填写")) {
                    Toast.makeText(context, "请先填写取样编号", Toast.LENGTH_SHORT).show();
                } else {
                    if (listener != null) {
                        listener.onItemClick(((NormalHolder) holder).mBtnImage4, holder.getLayoutPosition(), SettingUtil.HOLDER_IMAGE_4);
                    } else {
                        Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        /* 点击跳转到施工船舶选择界面 */
        ((NormalHolder) holder).mRlConsShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(((NormalHolder) holder).mTvsamplenum, holder.getLayoutPosition(), SettingUtil.HOLDER_CONS_SHIP);
                } else {
                    Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* 长按删除 */
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                } else {
                    Toast.makeText(context, "已进行退场申请, 不能修改数据", Toast.LENGTH_SHORT).show();
                }
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
        private final RelativeLayout mRlConsShip;
        private final TextView mTvConsShip;
        private final ImageButton mBtnImage3;
        private final ImageButton mBtnImage4;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvsamplenum = (TextView) itemView.findViewById(R.id.tv_sample_num);
            mBtnImage1 = (ImageButton) itemView.findViewById(R.id.btn_image_1);
            mBtnImage2 = (ImageButton) itemView.findViewById(R.id.btn_image_2);
            mBtnImage3 = (ImageButton) itemView.findViewById(R.id.btn_image_3);
            mBtnImage4 = (ImageButton) itemView.findViewById(R.id.btn_image_4);
            mRlConsShip = (RelativeLayout) itemView.findViewById(R.id.rl_cons_ship);
            mTvConsShip = (TextView) itemView.findViewById(R.id.tv_cons_ship);
        }
    }

    @Override
    public int getItemCount() {
        return sandSamplingNumRecordList.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 添加数据
     *
     * @param pos
     */
    public void addData(int pos) {
        SampleShowDatesBean.SandSamplingNumRecordListBean sandSamplingNumRecordListBean = new SampleShowDatesBean.SandSamplingNumRecordListBean();

        // 取样编号自增
        String batch = sampleShowDates.getBatch();
        sandSamplingNumRecordListBean.setSamplingNum(batch + String.valueOf((char) (pos + 65)));

        sandSamplingNumRecordListBean.setSandSamplingAttachmentRecordList(new ArrayList<SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean>());

        sandSamplingNumRecordListBean.getSandSamplingAttachmentRecordList().add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());
        sandSamplingNumRecordListBean.getSandSamplingAttachmentRecordList().add(new SampleShowDatesBean.SandSamplingNumRecordListBean.SandSamplingAttachmentRecordListBean());

        sandSamplingNumRecordList.add(pos, sandSamplingNumRecordListBean);
        notifyItemInserted(pos);
    }

    /**
     * 删除数据
     *
     * @param pos
     */
    public void delete(int pos) {
        // 从集合中删除
        sandSamplingNumRecordList.remove(pos);

        // 删除position后数据, 调整position
        DataSupport.deleteAll(SampleImageList.class, "itemID = ? and position = ?", String.valueOf(sampleShowDates.getSubcontractorInterimApproachPlanID()), String.valueOf(pos));

        // 调整剩余数据的position
        List<SampleImageList> sampleImageLists = DataSupport.where("itemID = ? and position > ?", String.valueOf(sampleShowDates.getSubcontractorInterimApproachPlanID()), String.valueOf(pos)).find(SampleImageList.class);

        // 更新position
        for (SampleImageList list : sampleImageLists) {
            list.setPosition(list.getPosition() - 1);
            list.save();
        }

        notifyItemRemoved(pos);
    }
}
