package com.kc.shiptransport.mvp.attendance;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.AttendanceType;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/6/28  16:40
 * @desc ${TODD}
 */

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private List<AttendanceType> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;
    private int checkedIndex = -1;

    public AttendanceAdapter(Context context, List<AttendanceType> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_attendance, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        // 获取数据
        AttendanceType type = list.get(position);
        ((NormalHolder)holder).mCheckBox.setText(type.getName());

        ((NormalHolder)holder).mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkedIndex = holder.getLayoutPosition();
                    //listener.onItemClick(holder.itemView, holder.getLayoutPosition());
                    notifyItemChanged(checkedIndex);
                }
            }
        });

        if (checkedIndex == position) {
            ((NormalHolder)holder).mCheckBox.setChecked(true);
        } else {
            ((NormalHolder)holder).mCheckBox.setChecked(false);
        }
    }



    class NormalHolder extends RecyclerView.ViewHolder {

        private final CheckBox mCheckBox;

        public NormalHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void setDates(List<AttendanceType> list) {
        this.list = list;
    }
}
