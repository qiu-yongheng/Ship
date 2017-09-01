package com.kc.shiptransport.util;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kc.shiptransport.interfaze.OnSpinnerClickListener;

import java.util.List;


/**
 * @author qiuyongheng
 * @time 2017/7/10  12:04
 * @desc 加载spinner
 */

public class SpinnerUtil {
    public static void showSpinner(Context context, final List<String> list, Spinner spinner, int position, final OnSpinnerClickListener listener) {
        // 适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        // 设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 加载适配器
        spinner.setAdapter(arr_adapter);

        // 根据上一个界面传过来的position设置当前显示的item
        if (position != 0) {
            spinner.setSelection(position);
        }

        // 点击后, 筛选供应商的数据
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO 保存数据
                listener.onItemSelected(adapterView, view, i, l);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
