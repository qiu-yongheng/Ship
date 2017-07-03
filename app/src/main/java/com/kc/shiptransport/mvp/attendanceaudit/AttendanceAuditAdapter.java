package com.kc.shiptransport.mvp.attendanceaudit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/28 23:34
 * @desc ${TODO}
 */

public class AttendanceAuditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<AttendanceRecordList> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public AttendanceAuditAdapter(Context context, List<AttendanceRecordList> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_attendance_audit, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        AttendanceRecordList attendanceRecordList = list.get(position);
        String creatorName = attendanceRecordList.getCreatorName();
        String attendanceTypeName = attendanceRecordList.getAttendanceTypeName();
        String attendanceTime = attendanceRecordList.getAttendanceTime();
        String remark = attendanceRecordList.getRemark();

        ((NormalHolder)holder).mTextName.setText(TextUtils.isEmpty(creatorName) ? "" : creatorName);
        ((NormalHolder)holder).mTextState.setText(TextUtils.isEmpty(attendanceTypeName) ? "" : attendanceTypeName);
        ((NormalHolder)holder).mTextDate.setText(TextUtils.isEmpty(attendanceTime) ? "" : attendanceTime);
        ((NormalHolder)holder).mTextRemark.setText(TextUtils.isEmpty(remark) ? "" : remark);

        String audit_remark = ((NormalHolder) holder).mEtRemark.getText().toString().trim();
        audit_remark = (TextUtils.isEmpty(audit_remark) || "添加备注".equals(audit_remark)) ? "" : audit_remark;

        attendanceRecordList.setRemarkForCheck(audit_remark);

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

        public NormalHolder(View itemView) {
            super(itemView);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextState = (TextView) itemView.findViewById(R.id.text_state);
            mTextDate = (TextView) itemView.findViewById(R.id.text_date);
            mTextRemark = (TextView) itemView.findViewById(R.id.text_remark);
            mBtnAgree = (Button) itemView.findViewById(R.id.btn_agree);
            mBtnNoAgree = (Button) itemView.findViewById(R.id.btn_no_agree);
            mEtRemark = (EditText) itemView.findViewById(R.id.et_remark);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
