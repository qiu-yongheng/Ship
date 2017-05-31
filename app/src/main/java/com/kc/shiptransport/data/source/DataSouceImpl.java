package com.kc.shiptransport.data.source;

import com.kc.shiptransport.data.bean.WeekTaskBean;
import com.kc.shiptransport.db.Ship;
import com.kc.shiptransport.db.WeekTask;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author 邱永恒
 * @time 2017/5/31 20:56
 * @desc 提供数据model的操作接口
 */

public interface DataSouceImpl {
    /**
     * 获取分包商信息, 并缓存到数据库
     */
    void getSubcontractorInfo(String username);

    /**
     * 获取船舶信息, 并缓存到数据库
     */
    void getShipInfo(String username);

    /**
     * 获取标题 日期 + 分包商
     *
     * @param jumpWeek
     * @return
     */
    Observable<String> getTitle(int jumpWeek);

    /**
     * 获取当前月时间
     *
     * @param jumpWeek
     * @return
     */
    Observable<String> getCurrentMouthDate(int jumpWeek);

    /**
     * 从本地数据库获取任务
     *
     * @return
     */
    Observable<List<WeekTask>> getWeekTask();

    /**
     * 计算每天任务总量
     *
     * @return
     */
    Observable<Integer[]> getDayCount();

    /**
     * 网络请求
     * 请求选中周的任务计划
     * @param SubcontractorAccount
     * @param StartDay
     * @param EndDay
     * @param jumpWeek
     * @return
     */
    Observable<List<WeekTaskBean>> doRefresh(String SubcontractorAccount, String StartDay, String EndDay, int jumpWeek);

    /**
     * 根据船的类型对数据进行分类
     * @return
     */
    Observable<List<List<Ship>>> getShipCategory();
}
