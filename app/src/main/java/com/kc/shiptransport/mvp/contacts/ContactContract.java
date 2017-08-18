package com.kc.shiptransport.mvp.contacts;

import com.kc.shiptransport.db.contacts.Contacts;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/8/14  8:37
 * @desc ${TODD}
 */

public interface ContactContract {
    interface View extends BaseView<Presenter> {
        void showContact(List<Contacts> list);
    }

    interface Presenter extends BasePresenter {
        void getContact(int pageSize, int pageNum, String json);
    }
}
