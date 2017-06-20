package com.kc.shiptransport.mvp.basemvp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.RecordList;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.SettingUtil;
import com.kc.shiptransport.util.SharePreferenceUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/17  15:13
 * @desc ${TODD}
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<String> dates;
    private OnRecyclerviewItemClickListener listener;
    private int type;

    public RecyclerAdapter(Context context, List<String> dates, int type) {
        this.context = context;
        this.dates = dates;
        this.type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_plan, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 设置数据
        if (position < 7) {
            ((NormalHolder) holder).mLlDate.setVisibility(View.VISIBLE);
            ((NormalHolder) holder).mLlTask.setVisibility(View.INVISIBLE);
            ((NormalHolder) holder).mTvDate.setText(dates.get(position));
        } else {
            ((NormalHolder) holder).mLlDate.setVisibility(View.INVISIBLE);

            if (type == SettingUtil.TYPE_RECORDEDSAND) {
                // 根据position查询数据
                List<RecordList> recordLists = DataSupport.where("position = ?", position + "").find(RecordList.class);

                if (!recordLists.isEmpty()) {
                    RecordList recordList = recordLists.get(0);
                    ((NormalHolder) holder).mTvShip.setText(recordList.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(recordList.getSandSupplyCount()));

                    // 判断是否已过砂
                    if (recordList.getIsFinish() == 1) {
                        // 已过砂
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (recordList.getIsFinish() == 0) {
                        // 未过砂
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.BLACK);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.BLACK);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.NO_ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    }

                    // 设置点击事件
                    if (listener != null) {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = holder.getLayoutPosition();
                                listener.onItemClick(holder.itemView, pos);
                            }
                        });
                    }
                } else {
                    ((NormalHolder) holder).mLlTask.setVisibility(View.INVISIBLE);
                }
            } else {

                /*------------------------------------------------------------------------------------------------*/
                // 根据position查询数据
                List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);

                // 必须验收后才能显示
                if (weekTasks != null && !weekTasks.isEmpty()) {
                    WeekTask weekTask = weekTasks.get(0);
                    ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(weekTask.getSandSupplyCount()));

                    String time = "";
                    // 判断是否已审核
                    if (type == SettingUtil.TYPE_ACCEPT) { // 验收
                        time = weekTask.getPreAcceptanceTime();
                    } else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                        time = weekTask.getReceptionSandTime();
                    } else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                        time = weekTask.getTheAmountOfTime();
                    }

                    if (time != null && !time.equals("")) {
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else {
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.BLACK);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.BLACK);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.NO_ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    }

                    // 设置点击事件
                    if (listener != null) {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int pos = holder.getLayoutPosition();
                                listener.onItemClick(holder.itemView, pos);
                            }
                        });
                    }
                } else {
                    ((NormalHolder) holder).mLlTask.setVisibility(View.INVISIBLE);
                }
                /*------------------------------------------------------------------------------------------------*/
            }

        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {
        private final TextView mTvShip;
        private final TextView mTvQuantum;
        private final LinearLayout mLlTask;
        private final LinearLayout mLlDate;
        private final TextView mTvDate;

        public NormalHolder(View itemView) {
            super(itemView);
            mLlTask = (LinearLayout) itemView.findViewById(R.id.ll_task);
            mTvShip = (TextView) itemView.findViewById(R.id.tv_ship);
            mTvQuantum = (TextView) itemView.findViewById(R.id.tv_quantum);

            mLlDate = (LinearLayout) itemView.findViewById(R.id.ll_date);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }

    @Override
    public int getItemCount() {
        return SettingUtil.Recycler_item_num;
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

}