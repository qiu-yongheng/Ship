package com.kc.shiptransport.mvp.attendanceaudit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

import static org.litepal.LitePalApplication.getContext;

/**
 * @author 邱永恒
 * @time 2017/6/28 23:34
 * @desc ${TODO}
 */

public class AttendanceAuditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final Animation showAnim;
    private final Animation hideAnim;
    public List<AttendanceRecordList> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public AttendanceAuditAdapter(Context context, List<AttendanceRecordList> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);

        /** 初始化动画 */
        showAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_show);
        hideAnim = AnimationUtils.loadAnimation(getContext(), R.anim.view_hide);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_attendance_audit, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final AttendanceRecordList attendanceRecordList = list.get(position);
        String creatorName = attendanceRecordList.getCreatorName();
        String attendanceTypeName = attendanceRecordList.getAttendanceTypeName();
        String attendanceTime = attendanceRecordList.getAttendanceTime();
        String remark = attendanceRecordList.getRemark();

        // 设置数据
        ((NormalHolder)holder).mTextName.setText(TextUtils.isEmpty(creatorName) ? "" : creatorName);
        ((NormalHolder)holder).mTextState.setText(TextUtils.isEmpty(attendanceTypeName) ? "" : attendanceTypeName);
        ((NormalHolder)holder).mTextDate.setText(TextUtils.isEmpty(attendanceTime) ? "" : attendanceTime);
        ((NormalHolder)holder).mTextRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);

        // 保存备注信息
        attendanceRecordList.setRemarkForCheck("");

        /** 根据isSelect, 判断是否需要显示同意与不同意按钮 */
        if (attendanceRecordList.getIsSelect() == 1) {
            // 选中
            ((NormalHolder)holder).mCbSelect.setChecked(true);
//            ((NormalHolder)holder).mBtnAgree.setVisibility(View.GONE);
//            ((NormalHolder)holder).mBtnNoAgree.setVisibility(View.GONE);
        } else {
            // 未选中
            ((NormalHolder)holder).mCbSelect.setChecked(false);
//            ((NormalHolder)holder).mBtnAgree.setVisibility(View.VISIBLE);
//            ((NormalHolder)holder).mBtnNoAgree.setVisibility(View.VISIBLE);
        }

        /** 监听checkbar */
        ((NormalHolder)holder).mCbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    attendanceRecordList.setIsSelect(1);
                } else {
                    attendanceRecordList.setIsSelect(0);
                }

                attendanceRecordList.save();

                listener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
            }
        });



        // 监听EditText
        ((NormalHolder) holder).mEtRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString();
                data = (TextUtils.isEmpty(data) || "添加备注".equals(data)) ? "" : data;

                attendanceRecordList.setRemarkForCheck(data);
            }
        });

        // 点击事件
        ((NormalHolder)holder).mBtnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition(), 1);
            }
        });

        ((NormalHolder)holder).mBtnNoAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(holder.itemView, holder.getLayoutPosition(), -1);
            }
        });
    }

    public void setDates(List<AttendanceRecordList> list) {
        this.list = list;
    }

    class NormalHolder extends RecyclerView.ViewHolder {


        private final TextView mTextName;
        private final TextView mTextState;
        private final TextView mTextDate;
        private final Button mBtnAgree;
        private final Button mBtnNoAgree;
        private final EditText mEtRemark;
        private final TextView mTextRemark;
        private final CheckBox mCbSelect;

        public NormalHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextState = (TextView) itemView.findViewById(R.id.text_state);
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextRemark = (TextView) itemView.findViewById(R.id.text_remark);
            mBtnAgree = (Button) itemView.findViewById(R.id.btn_agree);
            mBtnNoAgree = (Button) itemView.findViewById(R.id.btn_no_agree);
            mEtRemark = (EditText) itemView.findViewById(R.id.et_remark);
            mCbSelect = (CheckBox) itemView.findViewById(R.id.cb_select);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * 删除数据
     *
     * @param pos
     */
    public void delete(int pos) {
        // 从集合中删除
        list.remove(pos);

        //notifyItemRemoved(pos);
        notifyDataSetChanged();
    }
}
