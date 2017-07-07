package com.kc.shiptransport.mvp.partition;

import com.kc.shiptransport.db.partition.PartitionNum;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author qiuyongheng
 * @time 2017/7/7  10:19
 * @desc ${TODD}
 */

public interface PartitionContract{
    interface View extends BaseView<Presenter> {
        void showList(List<PartitionNum> list);
    }

    interface Presenter extends BasePresenter {
        void getList(String userID);
    }
}
