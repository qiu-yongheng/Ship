package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.WeekTask;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/19  10:55
 * @desc ${TODD}
 */

public class PlanSetItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private final List<WeekTask> list;

    public PlanSetItemAdapter(Context context, List<WeekTask> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_plan_add_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeekTask weekTask = list.get(position);

        ((NormalHolder)holder).mTvPlanSetItem.setText(weekTask.getShipName());
        ((NormalHolder)holder).mTvPlanSetItemMax.setText(weekTask.getSandSupplyCount());
    }

    class NormalHolder extends RecyclerView.ViewHolder {


        private final AppCompatTextView mTvPlanSetItem;
        private final AppCompatTextView mTvPlanSetItemMax;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvPlanSetItem = (AppCompatTextView) itemView.findViewById(R.id.tv_plan_set_item);
            mTvPlanSetItemMax = (AppCompatTextView) itemView.findViewById(R.id.tv_plan_set_item_max);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
