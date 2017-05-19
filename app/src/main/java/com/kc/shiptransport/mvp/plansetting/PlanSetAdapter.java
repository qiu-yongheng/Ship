package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/18  20:30
 * @desc ${TODD}
 */

public class PlanSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<List<Ship>> value;
    private OnRecyclerviewItemClickListener listener;
    private List<WeekTask> rlDatas;

    public PlanSetAdapter(Context context, List<List<Ship>> value, List<WeekTask> list) {
        this.context = context;
        this.value = value;
        this.rlDatas = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_plan_add, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        List<Ship> lists = value.get(position);
        String type = lists.get(0).getShipType();
        // 设置类型
        ((NormalHolder) holder).mTvShipTypeName.setText("类型" + type);

        // 设置内部recyclerview显示
        ((NormalHolder) holder).mRecyclerViewShipType.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

        // adapter + 设置
        List<WeekTask> rllist = new ArrayList<>();
        for (WeekTask weekTask : rlDatas) {
            if (type.equals(weekTask.getItemID())) {
                rllist.add(weekTask);
            }
        }
        ((NormalHolder) holder).mRecyclerViewShipType.setAdapter(new PlanSetItemAdapter(context, rllist));

        // 设置按钮点击事件
        ((NormalHolder) holder).mBtnShipTypeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                listener.onItemClick(holder.itemView, pos);
            }
        });
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final AppCompatButton mBtnShipTypeSelect;
        private final AppCompatTextView mTvShipTypeName;
        private final RecyclerView mRecyclerViewShipType;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvShipTypeName = (AppCompatTextView) itemView.findViewById(R.id.tv_ship_type_name);
            mBtnShipTypeSelect = (AppCompatButton) itemView.findViewById(R.id.btn_ship_type_select);
            mRecyclerViewShipType = (RecyclerView) itemView.findViewById(R.id.recyclerview_ship_type);
        }
    }

    @Override
    public int getItemCount() {
        return value.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }

    /**
     * @param rlDatas
     */
    public void setRecyclerViewData(List<WeekTask> rlDatas) {
        this.rlDatas = rlDatas;
    }
}
