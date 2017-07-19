package com.kc.shiptransport.mvp.voyagedetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.VoyageDetailBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.SettingUtil;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/4  20:38
 * @desc 信息完善列表
 */

public class VoyageDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    public VoyageDetailBean bean;
    public List<VoyageDetailBean.ColumnsBean> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public VoyageDetailAdapter(Context context, VoyageDetailBean bean) {
        this.context = context;
        this.bean = bean;
        this.list = bean.getColumns();
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_voyage_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 获取数据
        VoyageDetailBean.ColumnsBean columnsBean = list.get(position);
        // 获取类型, 根据类型处理不同的点击事件
        final String type = columnsBean.getControlType();

        // 显示标签名
        ((NormalHolder) holder).mTvTag.setText(columnsBean.getLabel());


        if (type.equals("select")) {
            String value = TextUtils.isEmpty(columnsBean.getValue()) ? "" : columnsBean.getValue();
            String[] split = value.split(";");
            if (split.length != 0) {
                ((NormalHolder) holder).mTvValue.setText(split[0]);
            } else {
                ((NormalHolder) holder).mTvValue.setText("");
            }
        } else {
            // 显示字段参数
            ((NormalHolder) holder).mTvValue.setText(TextUtils.isEmpty(columnsBean.getValue()) ? "" : columnsBean.getValue());
        }


        // 设置点击事件
        ((NormalHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("text".equals(type)) {
                    /** 文本输入 */
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.TYPE_TEXT);
                } else if ("datetime".equals(type)) {
                    /** 时间输入 */
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.TYPE_DATA);
                } else if ("select".equals(type)) {
                    /** 下拉框 */
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.TYPE_ARRAY);
                } else if ("ReadOnly".equals(type)) {
                    /** 只读 */
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.TYPE_READ_ONLY);
                } else if ("number".equals(type)) {
                    /** 只能输入数字 */
                    listener.onItemClick(holder.itemView, holder.getLayoutPosition(), SettingUtil.TYPE_NUMBER);
                }
            }
        });

    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mRl;
        private final TextView mTvTag;
        private final Spinner mSp;
        private final TextView mTvValue;

        public NormalHolder(View itemView) {
            super(itemView);
            mRl = (RelativeLayout) itemView.findViewById(R.id.rl);
            mTvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            mSp = (Spinner) itemView.findViewById(R.id.sp);
            mTvValue = (TextView) itemView.findViewById(R.id.tv_value);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void setDates(VoyageDetailBean bean) {
        this.bean = bean;
        this.list = bean.getColumns();
    }
}
