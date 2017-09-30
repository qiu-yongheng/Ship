package com.kc.shiptransport.mvp.partition;

import android.content.Context;
import android.os.Handler;
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
import com.kc.shiptransport.util.ToastUtil;

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
    private int tagPosition = 0;
    private boolean isAllowFlush = true;

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
        /** 判断是否有重命名panel */
        ((NormalHolder) holder).mEtNum.setTextColor(num.getNameTag() == 1 ? context.getResources().getColor(R.color.blue) : context.getResources().getColor(R.color.black));

        ((NormalHolder) holder).mBtnBed.setText(TextUtils.isEmpty(num.getLayoutName()) ? "施工分层" : num.getLayoutName());
        /** 根据panel长度判断显示状态 */
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

        // 施工分层
        ((NormalHolder) holder).mBtnBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(((NormalHolder) holder).mBtnBed, holder.getLayoutPosition(), 0);
            }
        });

        /** edit编辑 */
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

                // TODO: 问题太多, 先不考虑
                // 判断是否重名
                List<PartitionNum> list = DataSupport.where("num = ? and userAccount = ?", data, userAccount).find(PartitionNum.class);
                if (list.isEmpty()) {
                    ((NormalHolder) holder).mEtNum.setTextColor(context.getResources().getColor(R.color.black));
                    num.setNameTag(1);
                    num.save();
                } else {
                    // 有相同命名
                    ((NormalHolder) holder).mEtNum.setTextColor(context.getResources().getColor(R.color.blue));
                    num.setNameTag(0);
                    num.save();
                    ToastUtil.tip(context, "已有相同命名的panel, 请修改");
                }


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

                // 如果是第一个数据, 且是第一次修改, 保存长度
                //                if (position == 0) {
                //                    panelLength = data.length();
                //                    notifyDataSetChanged();
                //                }
            }
        });


        /** 失去焦点回调 */
        ((NormalHolder) holder).mEtNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    /** 失去焦点 */
                    if (position == 0 && num.getNum() != null) {
                        if (panelLength == num.getNum().length()) {
                            // 长度相同, 不刷新
                            LogUtil.d("长度相同, 不刷新");
                            return;
                        }

                        panelLength = num.getNum().length();

                        Handler handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                //notifyDataSetChanged();
                                // 失去焦点时, 更新数据是否重复状态
                                listener.onItemClick(holder.itemView, position, 1);
                                LogUtil.d("position = 0 正在刷新界面");
                            }
                        };
                        handler.post(r);
                    } else if (position != 0 && num.getNum() != null) {
                        if (isAllowFlush && tagPosition == position) {
                            isAllowFlush = false;
                            Handler handler = new Handler();
                            final Runnable r = new Runnable() {
                                public void run() {
                                    //notifyDataSetChanged();
                                    // 失去焦点时, 更新数据是否重复状态
                                    listener.onItemClick(holder.itemView, position, 1);
                                    LogUtil.d("position: " + position + " 正在刷新界面");
                                }
                            };
                            handler.post(r);
                        }
                    }
                } else {
                    if (tagPosition != position) {
                        // 允许刷新
                        isAllowFlush = true;
                    }

                    // 记录获取焦点的position
                    tagPosition = position;
                    LogUtil.d("获取焦点position: " + position);

                }
            }
        });

        /** 刷新后, 还原焦点 */
        if (tagPosition == position) {
            ((NormalHolder) holder).mEtNum.setFocusable(true);
            ((NormalHolder) holder).mEtNum.setFocusableInTouchMode(true);
            ((NormalHolder) holder).mEtNum.requestFocus();
            //((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }

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

    @Override
    public long getItemId(int position) {
        return position;
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
        String s = list.get(pos).getNum();
        // 从列表移除
        this.list.remove(pos);

        // 查询数据库中是否还有同名数据
        List<PartitionNum> numList = DataSupport.where("userAccount = ? and num = ?", userAccount, s).find(PartitionNum.class);
        if (numList.size() > 1) {
            // 还有同名数据, 不做操作
        } else {
            // 没有同名数据了, 更新装填
            for (PartitionNum num : list) {
                if (num.getNum().equals(s)) {
                    num.setNameTag(0);
                    num.save();
                }
            }
        }


        //notifyItemRemoved(pos);
        notifyDataSetChanged();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
