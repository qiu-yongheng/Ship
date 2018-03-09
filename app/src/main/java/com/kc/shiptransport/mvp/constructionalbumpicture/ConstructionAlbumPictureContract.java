package com.kc.shiptransport.mvp.constructionalbumpicture;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

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

        /**
         * 删除结果
         * @param isSuccess
         * @param position
         */
        void showDeleteResult(boolean isSuccess, int position);
    }

    interface Presenter extends BasePresenter {
        /**
         * 根据相册ID获取图片列表
         * @param albumItemID 相册ID
         */
        void getImgList(int albumItemID);

        /**
         * 删除照片
         * @param Table
         * @param ItemID
         * @param SubTable
         * @param AssociatedColumn
         * @param position
         */
        void deleteImg(String Table, String ItemID, String SubTable, String AssociatedColumn, int position);

        /**
         * 提交图片
         * @param albumID
         * @param remark
         * @param imageMultipleResultEvent
         */
        void commitPicture(int albumID, String remark, ImageMultipleResultEvent imageMultipleResultEvent);
    }
}
