package com.kc.shiptransport.mvp.attendancerecord;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceRecordList;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/6/28 23:34
 * @desc ${TODO}
 */

public class AttendanceRecordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private List<AttendanceRecordList> list;
    private final LayoutInflater inflate;

    public AttendanceRecordAdapter(Context context, List<AttendanceRecordList> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_attendance_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AttendanceRecordList attendanceRecordList = list.get(position);

        ((NormalHolder)holder).mTextCreator.setText(attendanceRecordList.getCreator());
        ((NormalHolder)holder).mTextTime.setText(attendanceRecordList.getSystemDate());
        ((NormalHolder)holder).mTextType.setText(attendanceRecordList.getAttendanceTypeName());
        ((NormalHolder)holder).mTextRemark.setText(attendanceRecordList.getRemark());

    }

    public void setDates(List<AttendanceRecordList> list) {
        this.list = list;
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTextCreator;
        private final TextView mTextTime;
        private final TextView mTextRemark;
        private final TextView mTextType;

        public NormalHolder(View itemView) {
            super(itemView);
            mTextCreator = (TextView) itemView.findViewById(R.id.text_creator);
            mTextTime = (TextView) itemView.findViewById(R.id.text_time);
            mTextType = (TextView) itemView.findViewById(R.id.text_type);
            mTextRemark = (TextView) itemView.findViewById(R.id.text_remark);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
