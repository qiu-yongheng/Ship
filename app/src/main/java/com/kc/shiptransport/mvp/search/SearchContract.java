package com.kc.shiptransport.mvp.search;

import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/9/19  9:54
 * @desc ${TODD}
 */

public interface SearchContract {
    interface View extends BaseView<Presenter> {
        void showResult(List<Contacts> list);
    }

    interface Presenter extends BasePresenter {
        void search(String keyWords);
    }
}
