package com.kc.shiptransport.mvp.acceptance;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
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

public class AcceptanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private List<String> dates;
    private List<WeekTask> weekLists;
    private OnRecyclerviewItemClickListener listener;
    private String account;
    private List<WeekTask> weekTasks;

    public AcceptanceAdapter(Context context, List<String> dates, List<WeekTask> weekLists) {
        this.context = context;
        this.dates = dates;
        this.weekLists = weekLists;
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

            if (account == null || account.equals("")) {
                weekTasks = DataSupport.where("position = ?", String.valueOf(position)).find(WeekTask.class);
            } else {
                weekTasks = DataSupport.where("position = ? and SubcontractorAccount = ?", String.valueOf(position), account).find(WeekTask.class);
            }

            if (weekTasks != null && !weekTasks.isEmpty()) {
                WeekTask weekTask = weekTasks.get(0);
                ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(weekTask.getSandSupplyCount()));
                ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);


                // 判断是否已审核通过, 通过, 显示红色
                int preAcceptanceEvaluationStatus = weekTask.getPreAcceptanceEvaluationStatus();
                if (preAcceptanceEvaluationStatus == 1) {
                    // 通过
                    ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                    ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                    // 根据单选判断是否显示
                    boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                    ((NormalHolder) holder).mLlTask.setVisibility(isAccepted? View.VISIBLE : View.INVISIBLE);
                } else {
                    // 保存或不通过
                    ((NormalHolder) holder).mTvShip.setTextColor(Color.BLACK);
                    ((NormalHolder) holder).mTvQuantum.setTextColor(Color.BLACK);

                    // 根据单选判断是否显示
                    boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.NO_ACCEPTED);
                    ((NormalHolder) holder).mLlTask.setVisibility(isAccepted? View.VISIBLE : View.INVISIBLE);
                }

                // 根据不同的position设置点击事件
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

    public void setAccount(String account) {
        this.account = account;
    }

}
