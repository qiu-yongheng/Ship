package com.kc.shiptransport.mvp.constructionalbum;

import com.kc.shiptransport.data.bean.album.ConstructionAlbumBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

/**
 * @author 邱永恒
 * @time 2018/3/8  9:58
 * @desc ${TODD}
 */

public interface ConstructionAlbumContract {
    interface View extends BaseView<Presenter> {
        /**
         * 展示相册列表
         * @param bean
         */
        void ShowAlbumList(ConstructionAlbumBean bean);

        /**
         * 展示新增结果
         * @param isSuccess
         * @param albumName
         * @param remark
         * @param position
         */
        void showInsertResult(boolean isSuccess, String albumName, String remark, int position);

        /**
         * 展示修改结果
         * @param isSuccess
         */
        void showUpdateResult(boolean isSuccess);

        /**
         * 展示删除结果
         * @param isSuccess
         * @param position
         */
        void showDeleteResult(boolean isSuccess, int position);
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取相册列表
         * @param pageSize
         * @param pageCount
         */
        void getAlbumList(int pageSize, int pageCount);

        /**
         * 修改相册
         * @param itemID
         * @param albumName
         * @param remark
         * @param position
         */
        void updateAlbum(int itemID, String albumName, String remark, int position);

        /**
         * 新增相册
         * @param albumName
         * @param remark
         */
        void insertAlbum(String albumName, String remark);

        /**
         * 删除相册
         * @param Table
         * @param ItemID
         * @param SubTable
         * @param AssociatedColumn
         * @param position
         */
        void deleteAlbum(String Table, String ItemID, String SubTable, String AssociatedColumn, int position);
    }
}
