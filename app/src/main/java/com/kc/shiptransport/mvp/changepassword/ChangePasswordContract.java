package com.kc.shiptransport.mvp.changepassword;

import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author qiuyongheng
 * @time 2017/7/12  16:08
 * @desc ${TODD}
 */

public interface ChangePasswordContract{
    interface View extends BaseView<Presenter> {
        void showChangeResult(boolean isSuccess);
    }

    interface Presenter extends BasePresenter {
        void changePassword(String LoginName, String OldPassword, String NewPassword);
    }
}
