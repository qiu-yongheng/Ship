package com.kc.shiptransport.mvp.downtimelog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.downlog.DownLogBean;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/10  17:08
 * @desc ${TODD}
 */

public class DowntimeLogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    public List<DownLogBean> list;
    private final LayoutInflater inflate;
    private OnRecyclerviewItemClickListener listener;

    public DowntimeLogAdapter(Context context, List<DownLogBean> list) {
        this.context = context;
        this.list = list;
        this.inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(inflate.inflate(R.layout.item_down_log, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DownLogBean downLogBean = list.get(position);

        String endTime = downLogBean.getEndTime();
        String realDate = "";
        try {
            if (endTime.contains("23:59:59")) {
                realDate = CalendarUtil.getOffsetDate(CalendarUtil.YYYY_MM_DD_HH_MM_SS, endTime, Calendar.SECOND, 1);
            } else {
                realDate = endTime;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((NormalHolder)holder).mTvShipAccount.setText(downLogBean.getShipName());
        ((NormalHolder)holder).mTvStopType.setText(downLogBean.getStopTypeName());
        ((NormalHolder)holder).mTvCreator.setText(downLogBean.getCreatorName());
        ((NormalHolder)holder).mTvStartTime.setText(downLogBean.getStartTime());
        ((NormalHolder)holder).mTvEndTime.setText(realDate);
        ((NormalHolder)holder).mTvSystemTime.setText(downLogBean.getSystemDate());
        ((NormalHolder)holder).mTvRemark.setText(downLogBean.getRemark());

    }

    public void setDates(List<DownLogBean> list) {
        this.list = list;
    }


    class NormalHolder extends RecyclerView.ViewHolder {

        private final TextView mTvShipAccount;
        private final TextView mTvStopType;
        private final TextView mTvCreator;
        private final TextView mTvStartTime;
        private final TextView mTvEndTime;
        private final TextView mTvSystemTime;
        private final TextView mTvRemark;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvShipAccount = (TextView) itemView.findViewById(R.id.text_ship_account);
            mTvStopType = (TextView) itemView.findViewById(R.id.text_stop_type);
            mTvCreator = (TextView) itemView.findViewById(R.id.text_creator);
            mTvStartTime = (TextView) itemView.findViewById(R.id.text_start_time);
            mTvEndTime = (TextView) itemView.findViewById(R.id.text_end_time);
            mTvSystemTime = (TextView) itemView.findViewById(R.id.text_system_time);
            mTvRemark = (TextView) itemView.findViewById(R.id.text_remark);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnRecyclerViewClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
