package com.zhy.adapter.recyclerview.base;


import java.util.List;

/**
 * @author qiuyongheng
 * @time 16/6/22  13:44
 * @desc 新建item类型, 需要继承此接口
 */
public interface ItemViewDelegate<T> {

    /**
     * 获取item布局ID
     * @return
     */
    int getItemViewLayoutId();

    /**
     * 判断item的类型
     * @param position
     * @return
     */
    boolean isForViewType(List<T> dates, int position);

    /**
     * 给控件绑定数据
     * @param holder
     * @param t 数据
     * @param position
     */
    void convert(ViewHolder holder, T t, int position);

}
