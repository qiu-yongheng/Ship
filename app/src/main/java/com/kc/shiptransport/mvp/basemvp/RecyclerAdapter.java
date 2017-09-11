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
import com.kc.shiptransport.db.SandSample;
import com.kc.shiptransport.db.WeekTask;
import com.kc.shiptransport.db.exitapplication.ExitList;
import com.kc.shiptransport.db.exitassessor.ExitAssessor;
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
                /** 过砂记录 */
                // 根据position查询数据
                List<RecordList> recordLists = DataSupport.where("position = ?", position + "").find(RecordList.class);

                if (!recordLists.isEmpty()) {
                    RecordList recordList = recordLists.get(0);
                    ((NormalHolder) holder).mTvShip.setText(recordList.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(recordList.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

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

            } else if (type == SettingUtil.TYPE_AMOUNT) {
                /** 量方 */
                // 根据position查询数据
                List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);

                if (!weekTasks.isEmpty()) {
                    WeekTask weekTask = weekTasks.get(0);
                    ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(weekTask.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    // 判断信息是否已完善
                    if (weekTask.getIsTheAmountOfTime() == 1) {
                        // 已过砂
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getIsTheAmountOfTime() == 0) {
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

            } else if (type == SettingUtil.TYPE_VOYAGEINFO) {
                /**航次信息完善*/
                // 根据position查询数据
                List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);

                if (!weekTasks.isEmpty()) {
                    WeekTask weekTask = weekTasks.get(0);
                    ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText("(" + String.valueOf(weekTask.getPerfectBoatItemCount()) + "/15)");
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.VISIBLE);


                    if (weekTask.getIsSumbittedPerfectBoat() == 1) {
                        /** 已提交, 蓝色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.BLUE);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.BLUE);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ALREADY_COMMIT);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getPreAcceptanceEvaluationStatus() == -1) {
                        /** 未提交, 且预验收不通过, 紫色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(context.getResources().getColor(R.color.purple));
                        ((NormalHolder) holder).mTvQuantum.setTextColor(context.getResources().getColor(R.color.purple));

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.RETURNED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getIsPerfect() == 1) {
                        /** 已完善, 红色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getIsPerfect() == 0) {
                        /** 未完善, 黑色 */
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


            } else if (type == SettingUtil.TYPE_SCANNER) {
                /** 扫描件 */
                // 根据position查询数据
                List<WeekTask> weekTasks = DataSupport.where("position = ?", position + "").find(WeekTask.class);

                if (!weekTasks.isEmpty()) {
                    WeekTask weekTask = weekTasks.get(0);
                    ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(weekTask.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    if (weekTask.getIsSumbittedPerfectBoatScanner() == 1) {
                        /** 已提交, 蓝色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.BLUE);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.BLUE);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ALREADY_COMMIT);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getPreAcceptanceEvaluationStatus() == -1) {
                        /** 未提交, 且预验收不通过, 紫色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(context.getResources().getColor(R.color.purple));
                        ((NormalHolder) holder).mTvQuantum.setTextColor(context.getResources().getColor(R.color.purple));

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.RETURNED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getIsFinshPerfectBoatScannerAttachment() == 1) {
                        /** 已过砂, 红色 */
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (weekTask.getIsFinshPerfectBoatScannerAttachment() == 0) {
                        /** 未过砂, 黑色 */
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

            } else if (type == SettingUtil.TYPE_SAMPLE) {
                /**验砂取样*/

                // 根据position查询数据
                List<SandSample> recordLists = DataSupport.where("position = ?", position + "").find(SandSample.class);

                if (!recordLists.isEmpty()) {
                    SandSample recordList = recordLists.get(0);
                    ((NormalHolder) holder).mTvShip.setText(recordList.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(recordList.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    ((NormalHolder) holder).mLlTask.setVisibility(View.VISIBLE);

                    // 判断是否已过砂
                    if (recordList.getIsSandSampling() == 1) {
                        // 已过砂
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (recordList.getIsSandSampling() == 0) {
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
            } else if (type == SettingUtil.TYPE_EXIT_APPLICATION) {
                /** 退场申请 */
                // 根据position查询数据
                List<ExitList> recordLists = DataSupport.where("position = ?", position + "").find(ExitList.class);

                if (!recordLists.isEmpty()) {
                    ExitList recordList = recordLists.get(0);
                    ((NormalHolder) holder).mTvShip.setText(recordList.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(recordList.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    ((NormalHolder) holder).mLlTask.setVisibility(View.VISIBLE);

                    // 判断是否已过砂
                    if (recordList.getIsExit() == 1) {
                        // 已退场
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (recordList.getIsExit() == 0) {
                        // 未退场
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
            } else if (type == SettingUtil.TYPE_EXIT_ASSESSOR) {
                /** 退场审核 */
                // 根据position查询数据
                List<ExitAssessor> recordLists = DataSupport.where("position = ?", position + "").find(ExitAssessor.class);

                if (!recordLists.isEmpty()) {
                    ExitAssessor recordList = recordLists.get(0);
                    ((NormalHolder) holder).mTvShip.setText(recordList.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(recordList.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    ((NormalHolder) holder).mLlTask.setVisibility(View.VISIBLE);

                    // 判断是否已审核
                    if (recordList.getIsExit() == 1) {
                        // 已审核
                        ((NormalHolder) holder).mTvShip.setTextColor(Color.RED);
                        ((NormalHolder) holder).mTvQuantum.setTextColor(Color.RED);

                        // 根据单选判断是否显示
                        boolean isAccepted = SharePreferenceUtil.getBoolean(context, SettingUtil.ACCEPTED);
                        ((NormalHolder) holder).mLlTask.setVisibility(isAccepted ? View.VISIBLE : View.INVISIBLE);
                    } else if (recordList.getIsExit() == 0) {
                        // 未退场
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

                // 如果有数据就显示
                if (weekTasks != null && !weekTasks.isEmpty()) {
                    WeekTask weekTask = weekTasks.get(0);
                    ((NormalHolder) holder).mTvShip.setText(weekTask.getShipName());
                    ((NormalHolder) holder).mTvQuantum.setText(String.valueOf(weekTask.getSandSupplyCount()));
                    ((NormalHolder) holder).mTvQuantum.setVisibility(View.GONE);

                    String time = "";
                    // 判断是否已审核
                    if (type == SettingUtil.TYPE_ACCEPT) { // 验收
                        time = weekTask.getPreAcceptanceTime();
                    } else if (type == SettingUtil.TYPE_SUPPLY) { // 验砂
                        time = weekTask.getReceptionSandTime();
                    } else if (type == SettingUtil.TYPE_AMOUNT) { // 量方
                        time = (weekTask.getIsTheAmountOfTime() == 1) ? "2017" : null;
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
