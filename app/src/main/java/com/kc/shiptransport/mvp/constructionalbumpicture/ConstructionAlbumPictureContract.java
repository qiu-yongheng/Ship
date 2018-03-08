package com.kc.shiptransport.mvp.constructionalbumpicture;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2018/3/8  17:09
 * @desc ${TODD}
 */

public interface ConstructionAlbumPictureContract {
    interface View extends BaseView<Presenter> {
        /**
         * 展示相册图片
         * @param bean
         */
        void showImgList(ConstructionAlbumPictureBean bean);
    }

    interface Presenter extends BasePresenter {
        /**
         * 根据相册ID获取图片列表
         * @param albumItemID 相册ID
         */
        void getImgList(int albumItemID);
    }
}
