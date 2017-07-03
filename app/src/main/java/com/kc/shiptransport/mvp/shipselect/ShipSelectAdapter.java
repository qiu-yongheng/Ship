package com.kc.shiptransport.mvp.shipselect;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.CommitShip;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.db.ship.ShipList;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/19  8:59
 * @desc ${TODD}
 */

public class ShipSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<ShipList> list;
    private final String currentSelectDate;
    private final String type;
    private OnRecyclerviewItemClickListener listener;

    public ShipSelectAdapter(Context context, Ship list, String currentSelectDate) {
        this.context = context;
        this.list = list.getShipList();
        this.type = list.getShipType();
        this.currentSelectDate = currentSelectDate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_ship_select, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        // 传过来的船舶数据都是已经分好类的, 但是没有按照时间分类
        final ShipList shipList = list.get(position);
        final String shipID = shipList.getShipID();


        /* 1. 设置数据 */
        ((NormalHolder) holder).mTvShipSelectName.setText(shipList.getShipName());
        ((NormalHolder) holder).mTvShipSelectNom.setText(shipList.getMaxSandSupplyCount());

        /* 2. 选择状态回显(select = 1, 日期相同) */
        if (!DataSupport.where("ShipAccount = ? and PlanDay = ?", shipList.getShipAccount(), currentSelectDate).find(CommitShip.class).isEmpty()  // 判断提交缓存中是否有该ship
                || !DataSupport.where("ShipAccount = ? and PlanDay = ?", shipList.getShipAccount(), currentSelectDate).find(WeekTask.class).isEmpty()) {  // 判断任务计划中是否有该ship
            ((NormalHolder) holder).mCbShipSelect.setChecked(true);
        } else {
            ((NormalHolder) holder).mCbShipSelect.setChecked(false);
        }


        /* 3. 设置点击事件(选中: select=1 取消选中: select=0) */
        ((NormalHolder) holder).mCbShipSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    /* 如果任务计划中已经有该船, 设置选中 */
                    if (!DataSupport.where("ShipAccount = ? and PlanDay = ?", shipList.getShipAccount(), currentSelectDate).find(WeekTask.class).isEmpty()) {
                        ContentValues values = new ContentValues();
                        values.put("isSelected", 1);
                        DataSupport.updateAll(ShipList.class, values, "ShipID = ?", shipID);
                    } else {

                        /* 保存计划到提交缓存中 */
                        CommitShip commitShip = new CommitShip();
                        commitShip.setItemID("");
                        commitShip.setShipAccount(shipList.getShipAccount());
                        commitShip.setMaxSandSupplyCount(shipList.getMaxSandSupplyCount());
                        commitShip.setPlanDay(currentSelectDate);
                        commitShip.setShipType(type);
                        commitShip.save();
                        Log.d("==", "选择长度: " + DataSupport.where("PlanDay = ?", currentSelectDate).find(CommitShip.class).size());
                    }
                } else {
                    /* 如果任务计划中有该船, 设置不选中 TODO 如果取消选中后, 退出怎么办? */
                    if (!DataSupport.where("ShipAccount = ? and PlanDay = ?", shipList.getShipAccount(), currentSelectDate).find(WeekTask.class).isEmpty()) {
                        ContentValues values = new ContentValues();
                        values.put("isSelected", 0);
                        DataSupport.updateAll(ShipList.class, values, "ShipID = ?", shipID);
                    } else {
                        /* 从提交缓存中删除 */
                        DataSupport.deleteAll(CommitShip.class, "ShipAccount = ?", shipList.getShipAccount());
                        Log.d("==", "选择长度: " + DataSupport.where("PlanDay = ?", currentSelectDate).find(CommitShip.class).size());
                    }
                }
            }
        });
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final CheckBox mCbShipSelect;
        private final AppCompatTextView mTvShipSelectName;
        private final AppCompatTextView mTvShipSelectNom;

        public NormalHolder(View itemView) {
            super(itemView);
            mCbShipSelect = (CheckBox) itemView.findViewById(R.id.cb_ship_select);
            mTvShipSelectName = (AppCompatTextView) itemView.findViewById(R.id.tv_ship_select_name);
            mTvShipSelectNom = (AppCompatTextView) itemView.findViewById(R.id.tv_ship_select_num);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
