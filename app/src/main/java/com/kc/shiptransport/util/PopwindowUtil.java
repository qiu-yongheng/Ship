package com.kc.shiptransport.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnTimePickerLastDateClickListener;
import com.kc.shiptransport.interfaze.OnTimePickerSureClickListener;
import com.kc.shiptransport.view.PopupWindow.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/11/22  16:20
 * @desc ${TODD}
 */

public class PopwindowUtil {

    private static CommonPopupWindow pupWindow;
    private static int width;
    private static int height;
    private static String startTime;
    private static String endTime;
    public static RecyclerView recyclerView;
    public static LinearLayout ll;

    public static <T> void showPopwindow(Context context, T[] list, View view, boolean isMeasure, InitHolder<T> initHolder, OnPopOKClickListener listener, OnHeadClickListener headClickListener) {
        showPopwindow(context, Arrays.asList(list), view, isMeasure, initHolder, listener, headClickListener);
    }

    public static <T> void showPopwindow(final Context context, final List<T> list, final View view, boolean isMeasure, final InitHolder<T> initHolder, final OnPopOKClickListener listener, final OnHeadClickListener headClickListener) {
        if (pupWindow != null && pupWindow.isShowing()) {
            return;
        }

        if (list == null) {
            return;
        }

        if (isMeasure) {
            width = view.getMeasuredWidth();
            height = view.getMeasuredHeight();
        } else {
            width = ViewGroup.LayoutParams.MATCH_PARENT;
        }

        pupWindow = new CommonPopupWindow.Builder(context)
                .setView(R.layout.popwindow)
                .setWidthAndHeight(width, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(final View view, int layoutResId) {
                        // 初始化控件
                        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));

                        // 自定义时间
                        ll = (LinearLayout) view.findViewById(R.id.ll);
                        TextView tvStartTime = (TextView) view.findViewById(R.id.tv_start_time);
                        TextView tvEndTime = (TextView) view.findViewById(R.id.tv_end_time);
                        Button btnReturn = (Button) view.findViewById(R.id.btn_return);
                        Button btnOk = (Button) view.findViewById(R.id.btn_ok);

                        CommonAdapter<T> commonAdapter = new CommonAdapter<T>(context, R.layout.item_spinner, list) {
                            @Override
                            protected void convert(ViewHolder holder, T t, int position) {
                                initHolder.initHolder(holder, t, position);
                            }
                        };

                        /** 添加头部 */
                        HeaderAndFooterWrapper<Object> headWrapper = new HeaderAndFooterWrapper<>(commonAdapter);
                        View inflate = LayoutInflater.from(context).inflate(R.layout.item_analysis, recyclerView, false);
                        TextView v = (TextView) inflate.findViewById(R.id.tv);
                        v.setText("全部");
                        headWrapper.addHeaderView(inflate);

                        headWrapper.setHeadAndFootClickListener(new HeaderAndFooterWrapper.OnHeadAndFootClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                if (headClickListener != null) {
                                    headClickListener.onHeadClick(view, holder, position);
                                }

                                PopwindowUtil.hidePopwindow();
                            }
                        });


                        /** 开始时间 */
                        tvStartTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    CalendarUtil.showDatePickerDialog(context, (TextView) view, new OnTimePickerSureClickListener() {
                                        @Override
                                        public void onSure(String str) {
                                            startTime = str;
                                        }
                                    }, new OnTimePickerLastDateClickListener() {
                                        @Override
                                        public void onLastDate() {

                                        }
                                    }, true, false);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        /** 结束时间 */
                        tvEndTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    CalendarUtil.showDatePickerDialog(context, (TextView) view, new OnTimePickerSureClickListener() {
                                        @Override
                                        public void onSure(String str) {
                                            endTime = str;
                                        }
                                    }, new OnTimePickerLastDateClickListener() {
                                        @Override
                                        public void onLastDate() {

                                        }
                                    }, true, false);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        /** 返回 */
                        btnReturn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recyclerView.setVisibility(View.VISIBLE);
                                ll.setVisibility(View.GONE);
                            }
                        });

                        /** 确定 */
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                try {
                                    boolean isLast = CalendarUtil.isLastDate_YYYY_MM_DD(startTime, endTime);
                                    if (!isLast) {
                                        if (listener != null) {
                                            listener.onOkClick(startTime, endTime);
                                        }
                                        PopwindowUtil.hidePopwindow();
                                    } else {
                                        ToastUtil.tip(context, "结束时间不能小于开始时间, 请重新选择");
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        if (headClickListener == null) {
                            recyclerView.setAdapter(commonAdapter);
                        } else {
                            recyclerView.setAdapter(headWrapper);
                        }
                    }
                })
                .setOutsideTouchable(true)
                .create();
        pupWindow.showAsDropDown(view);
    }

    public static void hidePopwindow() {
        if (pupWindow != null && pupWindow.isShowing()) {
            pupWindow.dismiss();

            recyclerView = null;
            ll = null;
        }
    }

    public interface InitHolder<T> {
        void initHolder(ViewHolder holder, T t, int position);
    }

    public interface OnPopOKClickListener {
        void onOkClick(String startTime, String endTime);
    }

    public interface OnHeadClickListener {
        void onHeadClick(View view, RecyclerView.ViewHolder holder, int position);
    }
}
