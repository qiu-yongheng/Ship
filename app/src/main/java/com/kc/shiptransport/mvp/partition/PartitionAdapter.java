package com.kc.shiptransport.mvp.partition;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/7  16:34
 * @desc ${TODD}
 */

public class PartitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final String userAccount;
    public List<PartitionNum> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;
    public int panelLength = 0;

    public PartitionAdapter(Context context, List<PartitionNum> list, String shipNum) {
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.list = list;
        this.userAccount = shipNum;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_partition_num, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final PartitionNum num = list.get(position);

        if (panelLength == 0 && position == 0) {
            // 记录第一个panel的长度
            panelLength = TextUtils.isEmpty(num.getNum()) ? 0 : num.getNum().length();
            ((NormalHolder) holder).mEtNum.setBackgroundColor(context.getResources().getColor(R.color.et_bg));
            num.setTag(1);
            num.save();
        } else {
            // 判断长度是否跟自动生成的一致
            if (panelLength != num.getNum().length()) {
                ((NormalHolder) holder).mEtNum.setBackgroundColor(context.getResources().getColor(R.color.red));
                num.setTag(0);
                num.save();
            } else {
                ((NormalHolder) holder).mEtNum.setBackgroundColor(context.getResources().getColor(R.color.et_bg));
                num.setTag(1);
                num.save();
            }
        }

        ((NormalHolder) holder).mTvTag.setText("分区" + (position + 1));

        String s = TextUtils.isEmpty(num.getNum()) ? "" : num.getNum();
        ((NormalHolder) holder).mEtNum.setText(s);

        ((NormalHolder) holder).mBtnBed.setText(TextUtils.isEmpty(num.getLayoutName()) ? "施工分层" : num.getLayoutName());
        ((NormalHolder) holder).mBtnBed.setBackgroundColor(TextUtils.isEmpty(num.getLayoutName()) ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.et_bg));

        // 设置光标位置
        ((NormalHolder) holder).mEtNum.setSelection(s.length());

        ((NormalHolder) holder).mEtNum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                return true;
            }
        });

        ((NormalHolder) holder).mBtnBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(((NormalHolder) holder).mBtnBed, holder.getLayoutPosition());
            }
        });

        ((NormalHolder) holder).mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                LogUtil.d("施工分区adapter EditText 数据改变");
                String data = editable.toString();
                data = (TextUtils.isEmpty(data)) ? "" : data;
                // 保存num
                num.setNum(data);
                num.save();

                // 更新状态
                int length = TextUtils.isEmpty(num.getNum()) ? 0 : num.getNum().length();
                if (length != panelLength && position != 0) {
                    ((NormalHolder) holder).mEtNum.setBackgroundColor(context.getResources().getColor(R.color.red));
                    num.setTag(0);
                    num.save();
                } else {
                    ((NormalHolder) holder).mEtNum.setBackgroundColor(context.getResources().getColor(R.color.et_bg));
                    num.setTag(1);
                    num.save();
                }
            }
        });

        /** 设置最后一个edittext获取焦点 */
//        if (list.size() == (position + 1)) {
//            ((NormalHolder) holder).mEtNum.setFocusable(true);
//            ((NormalHolder) holder).mEtNum.setFocusableInTouchMode(true);
//            ((NormalHolder) holder).mEtNum.requestFocus();
//            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        }
    }

    public void setDates(List<PartitionNum> list) {
        this.list = list;
    }


    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTag;
        private final EditText mEtNum;
        private final Button mBtnBed;

        public NormalHolder(View itemView) {
            super(itemView);

            mTvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            mEtNum = (EditText) itemView.findViewById(R.id.et_num);
            mBtnBed = (Button) itemView.findViewById(R.id.btn_bed);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 添加
     *
     * @param pos
     * @param layoutID
     * @param layoutName
     */
    public void add(int pos, int layoutID, String layoutName) {
        // 创建一个新的数据, 保存用户名
        PartitionNum num = new PartitionNum();
        num.setUserAccount(userAccount);
        num.setLayoutID(layoutID);
        num.setLayoutName(layoutName);
        num.setNum("");

        List<PartitionNum> all = DataSupport.findAll(PartitionNum.class);

        if (all.isEmpty()) {
            list.add(pos, num);
        } else {
            // 获取最后一个
            PartitionNum partitionNum = all.get(all.size() - 1);
            String s = partitionNum.getNum();

            if (!TextUtils.isEmpty(s)) {
                StringBuffer sb = new StringBuffer(s);

                sb.deleteCharAt(sb.length() - 1);

                num.setNum(sb.toString());
//                num.setLayoutID(partitionNum.getLayoutID());
//                num.setLayoutName(partitionNum.getLayoutName());
                num.save();
            }
            list.add(pos, num);
        }

        notifyItemInserted(pos);
    }

    /**
     * 删除
     *
     * @param pos
     */
    public void delete(int pos) {
        // 从数据库删除
        list.get(pos).delete();
        // 从列表移除
        list.remove(pos);
        //notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
