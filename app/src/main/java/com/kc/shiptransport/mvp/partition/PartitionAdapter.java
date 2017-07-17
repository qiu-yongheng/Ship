package com.kc.shiptransport.mvp.partition;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/7  16:34
 * @desc ${TODD}
 */

public class PartitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<PartitionNum> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public PartitionAdapter(Context context, List<PartitionNum> list) {
        this.context = context;
        this.inflate = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_partition_num, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final PartitionNum num = list.get(position);

        ((NormalHolder)holder).mTvTag.setText("分区" + (position + 1));

        String s = TextUtils.isEmpty(num.getNum()) ? "" : num.getNum();
        ((NormalHolder)holder).mEtNum.setText(s);

        // 设置光标位置
        ((NormalHolder)holder).mEtNum.setSelection(s.length());

        ((NormalHolder)holder).mEtNum.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                return false;
            }
        });

        ((NormalHolder)holder).mEtNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString();
                data = (TextUtils.isEmpty(data)) ? "" : data;

                // 保存num
                num.setNum(data);
                num.save();
            }
        });


        /** 设置最后一个edittext获取焦点 */
        if (list.size() == (position + 1)) {
            ((NormalHolder)holder).mEtNum.setFocusable(true);
            ((NormalHolder)holder).mEtNum.setFocusableInTouchMode(true);
            ((NormalHolder)holder).mEtNum.requestFocus();
            ((Activity)context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void setDates(List<PartitionNum> list) {
        this.list = list;
    }


    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTag;
        private final EditText mEtNum;

        public NormalHolder(View itemView) {
            super(itemView);

            mTvTag = (TextView) itemView.findViewById(R.id.tv_tag);
            mEtNum = (EditText) itemView.findViewById(R.id.et_num);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 添加
     * @param pos
     */
    public void add (int pos) {
        // 创建一个新的数据, 保存用户名
        PartitionNum num = new PartitionNum();
        num.setUserAccount(list.get(0).getUserAccount());

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
            }
            list.add(pos, num);
        }

        DataSupport.saveAll(list);

        notifyItemInserted(pos);
    }

    /**
     * 删除
     * @param pos
     */
    public void delete(int pos) {
        list.get(pos).delete();
        list.remove(pos);


        List<PartitionNum> all = DataSupport.findAll(PartitionNum.class);

        notifyItemRemoved(pos);
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}