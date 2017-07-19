package com.kc.shiptransport.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.kc.shiptransport.R;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/19  14:56
 * @desc 条件选择器
 */

public class SelectUtil<T> {


    private OptionsPickerView optionsPickerView;

    /**
     * 显示数据选择器
     * @param context
     * @param list
     * @param isDialog
     * @param listener
     */
    public void showSelectDialog(Context context, List<T> list, boolean isDialog, OptionsPickerView.OnOptionsSelectListener listener) {
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        /** 回调 */ //返回的分别是三个级别的选中位置
        optionsPickerView = new OptionsPickerView.Builder(context, listener)
                .setLayoutRes(R.layout.pickerview_options_diy, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        /** 确定 */
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                optionsPickerView.returnData();
                                optionsPickerView.dismiss();
                            }
                        });

                        /** 取消 */
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                optionsPickerView.dismiss();
                            }
                        });

                        tvAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                    }
                })
                .isDialog(isDialog)
                .build();

        optionsPickerView.setPicker(list);//添加数据

        optionsPickerView.show();
    }
}
