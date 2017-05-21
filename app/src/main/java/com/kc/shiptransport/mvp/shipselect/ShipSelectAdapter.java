package com.kc.shiptransport.mvp.shipselect;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/19  8:59
 * @desc ${TODD}
 */

public class ShipSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Ship> list;
    private final String currentSelectDate;
    private OnRecyclerviewItemClickListener listener;
    private List<Ship> cacheList = new ArrayList<>();

    public ShipSelectAdapter(Context context, List<Ship> list, String currentSelectDate) {
        this.context = context;
        this.list = list;
        this.currentSelectDate = currentSelectDate;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_ship_select, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Ship ship = list.get(position);
        final String shipName = ship.getShipName();
        /* 1. 设置数据 */
        ((NormalHolder) holder).mTvShipSelectName.setText(ship.getShipName());
        ((NormalHolder) holder).mTvShipSelectNom.setText(ship.getMaxSandSupplyCount());

        /* 2. 选择状态回显(select = 1, 日期相同) */
        ((NormalHolder) holder).mCbShipSelect.setChecked(ship.getSelected() != null
                && ship.getSelected().equals("1")
                && !DataSupport.where("ShipAccount = ? and PlanDay = ?", ship.getShipAccount(), currentSelectDate).find(WeekTask.class).isEmpty());

        /* 3. 设置点击事件(选中: select=1 取消选中: select=0) */
        ((NormalHolder) holder).mCbShipSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ContentValues values = new ContentValues();
                    values.put("Selected", "1");
                    DataSupport.updateAll(Ship.class, values, "ShipName = ?", shipName);
                } else {
                    ContentValues values = new ContentValues();
                    values.put("Selected", "0");
                    DataSupport.updateAll(Ship.class, values, "ShipName = ?", shipName);
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
