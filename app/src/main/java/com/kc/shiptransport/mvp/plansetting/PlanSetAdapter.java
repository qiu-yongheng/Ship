package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/18  20:30
 * @desc ${TODD}
 */

public class PlanSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<List<Ship>> value;
    private String date;
    private OnRecyclerviewItemClickListener listener;


    public PlanSetAdapter(Context context, List<List<Ship>> value, String date) {
        this.context = context;
        this.value = value;
        this.date = date;
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
        ((NormalHolder) holder).mTvShipTypeName.setText("类型:" + type);


        /*----------------------查询对应type的计划数据-----------------------*/
        List<WeekTask> weekTasks = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(WeekTask.class);
        if (weekTasks != null && !weekTasks.isEmpty()) {
            // 设置内部recyclerview显示
            ((NormalHolder) holder).mRecyclerViewShipType.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            ((NormalHolder) holder).mRecyclerViewShipType.setAdapter(new PlanSetItemAdapter(context, weekTasks));
            ((NormalHolder) holder).mRecyclerViewShipType.setVisibility(View.VISIBLE);
        } else {
            ((NormalHolder) holder).mRecyclerViewShipType.setVisibility(View.GONE);
        }
        /*----------------------查询对应type的计划数据-----------------------*/




        // 设置按钮点击事件
        ((NormalHolder) holder).mBtnShipTypeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                listener.onItemClick(holder.itemView, pos);
            }
        });
    }

    public void setDates(List<List<Ship>> value, String date) {
        this.value = value;
        this.date = date;
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

    // 根据子控件的个数动态设置高度
    private void setListviewHeightOnChildren (ListView lv) {
        // 1. 获取 listView的 adapter
        ListAdapter adapter = lv.getAdapter() ;

        // 判断是否有数据
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;

        for ( int i = 0 ; i < adapter.getCount() ; i++) {
            View item = adapter.getView(i , null, lv) ;

            // 激活测量
            item.measure( 0 , 0 ) ;

            // 获取控件的高度 , 相加
            totalHeight += item.getMeasuredHeight() ;
        }

        // 获取 listView的参数
        ViewGroup.LayoutParams params = lv.getLayoutParams();

        // 设置 listView的高
        params. height = totalHeight + (lv.getDividerHeight() * (lv.getCount() - 1 )) ;

        params. height += 5 ;

        lv.setLayoutParams(params) ;
    }

}
