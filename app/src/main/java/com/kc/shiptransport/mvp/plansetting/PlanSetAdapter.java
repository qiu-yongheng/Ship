package com.kc.shiptransport.mvp.plansetting;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.TaskVolume;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.ship.Ship;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.util.CalendarUtil;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/5/18  20:30
 * @desc ${TODD}
 */

public class PlanSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final int jumpWeek;
    private List<Ship> value;
    private String date;
    private OnRecyclerviewItemClickListener listener;
    private int isUpdate = 1;

    public PlanSetAdapter(Context context, List<Ship> value, String date, int jumpWeek) {
        this.context = context;
        this.value = value;
        this.date = date;
        this.jumpWeek = jumpWeek;

        isUpdatable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalHolder(LayoutInflater.from(context).inflate(R.layout.item_plan_add, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Ship ship = value.get(position);
        String type = ship.getShipType();
        String remark = ship.getRemark();
        // 设置类型
        ((NormalHolder) holder).mTvShipTypeName.setText(type + " " + remark);


        /*----------------------查询对应type的计划数据-----------------------*/
        List<WeekTask> weekTasks = DataSupport.where("PlanDay = ? and ShipType = ?", date, type).find(WeekTask.class);
        if (weekTasks != null && !weekTasks.isEmpty()) {
            // 设置内部recyclerview显示
            ((NormalHolder) holder).mRecyclerViewShipType.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            ((NormalHolder) holder).mRecyclerViewShipType.setAdapter(new PlanSetItemAdapter(context, weekTasks));
            ((NormalHolder) holder).mRecyclerViewShipType.setVisibility(View.VISIBLE);

            // 设置统计控件显示
            ((NormalHolder) holder).mTvPlanAddTotal.setVisibility(View.VISIBLE);
            // 统计
            double d = 0;
            for (WeekTask weekTask : weekTasks) {
                d += Double.valueOf(weekTask.getSandSupplyCount());
            }
            ((NormalHolder) holder).mTvPlanAddTotal.setText(type + "统计: " + d);
        } else {
            ((NormalHolder) holder).mRecyclerViewShipType.setVisibility(View.GONE);
            ((NormalHolder) holder).mTvPlanAddTotal.setVisibility(View.GONE);
        }
        /*----------------------查询对应type的计划数据-----------------------*/




        /*----------------------查询对应日期的计划需求-----------------------*/
        List<TaskVolume> taskVolumes = DataSupport.where("Date like ?", date + "%").find(TaskVolume.class);
        if (taskVolumes != null && !taskVolumes.isEmpty()) {
            if ("A类".equals(type)) {
                ((NormalHolder) holder).mTvPlanAddDemand.setText(type + "需求: " + taskVolumes.get(0).getBoatA());
            } else if ("B类".equals(type)) {
                ((NormalHolder) holder).mTvPlanAddDemand.setText(type + "需求: " + taskVolumes.get(0).getBoatB());
            } else if ("C类".equals(type)) {
                ((NormalHolder) holder).mTvPlanAddDemand.setText(type + "需求: " + taskVolumes.get(0).getBoatC());
            } else if ("D类".equals(type)) {
                ((NormalHolder) holder).mTvPlanAddDemand.setText(type + "需求: " + taskVolumes.get(0).getBoatD());
            }
        } else {
            // 如果在数据库中找不到, 默认为0
            ((NormalHolder) holder).mTvPlanAddDemand.setText(type + "需求: " + 0);
        }
        /*----------------------查询对应日期的计划需求-----------------------*/



        if (isUpdate == 1) {
            // 可以修改
            ((NormalHolder) holder).mBtnShipTypeSelect.setText("选择");
            ((NormalHolder) holder).mBtnShipTypeSelect.setBackgroundResource(R.color.red);
        } else{
            // 不可以修改
            ((NormalHolder) holder).mBtnShipTypeSelect.setText("不可修改");
            ((NormalHolder) holder).mBtnShipTypeSelect.setBackgroundResource(R.color.gray);
        }


        // 设置按钮点击事件
        ((NormalHolder) holder).mBtnShipTypeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getLayoutPosition();
                listener.onItemClick(((NormalHolder) holder).mBtnShipTypeSelect, pos, isUpdate);
            }
        });
    }

    public void setDates(List<Ship> value, String date) {
        this.value = value;
        this.date = date;

        isUpdatable();
    }

    /**
     * 判断是否可以修改数据
     */
    public void isUpdatable() {
        // 1. 今天中午时间
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        SimpleDateFormat df = new SimpleDateFormat(CalendarUtil.YYYY_MM_DD_HH_MM);
        String startTime = df.format(cal.getTime());

        // 2. 当前时间
        String currentDate = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD_HH_MM);

        // 3. 当前时间与今天中午时间对比
        try {
            boolean lastDate = CalendarUtil.isLastDate(startTime, currentDate);

            if (!lastDate) {
                // 中午后, 明天与之前的数据不能修改

                // 获取明天的日期
                Calendar tomorrowTime = Calendar.getInstance();
                tomorrowTime.add(Calendar.DAY_OF_MONTH, 1);
                SimpleDateFormat d = new SimpleDateFormat(CalendarUtil.YYYY_MM_DD);
                String tomorrow = d.format(cal.getTime());

                // 与选择日期进行判断
                if (!CalendarUtil.isLastDate_YYYY_MM_DD(tomorrow, date)) {
                    // 选择日期在明天后, 可以修改
                    isUpdate = 1;
                } else {
                    // 不能修改
                    isUpdate = 0;
                }

            } else {
                // 中午前, 今天与之前的数据不能修改
                String today = CalendarUtil.getCurrentDate(CalendarUtil.YYYY_MM_DD);

                // 与选择日期进行判断
                if (!CalendarUtil.isLastDate_YYYY_MM_DD(today, date)) {
                    // 选择日期在明天后, 可以修改
                    isUpdate = 1;
                } else {
                    // 不能修改
                    isUpdate = 0;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    class NormalHolder extends RecyclerView.ViewHolder {

        private final AppCompatButton mBtnShipTypeSelect;
        private final AppCompatTextView mTvShipTypeName;
        private final RecyclerView mRecyclerViewShipType;
        private final TextView mTvPlanAddTotal;
        private final TextView mTvPlanAddDemand;

        public NormalHolder(View itemView) {
            super(itemView);
            mTvShipTypeName = (AppCompatTextView) itemView.findViewById(R.id.tv_ship_type_name);
            mTvPlanAddTotal = (TextView) itemView.findViewById(R.id.tv_plan_add_total);
            mTvPlanAddDemand = (TextView) itemView.findViewById(R.id.tv_plan_add_demand);
            mBtnShipTypeSelect = (AppCompatButton) itemView.findViewById(R.id.btn_ship_type_select);
            mRecyclerViewShipType = (RecyclerView) itemView.findViewById(R.id.recyclerview_ship_type);
        }
    }

    @Override
    public int getItemCount() {
        // 分类的个数
        return value.size();
    }

    public void setOnItemClickListener(OnRecyclerviewItemClickListener listener) {
        this.listener = listener;
    }
}
