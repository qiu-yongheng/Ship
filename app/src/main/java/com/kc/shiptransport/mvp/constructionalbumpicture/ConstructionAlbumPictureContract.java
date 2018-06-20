package com.kc.shiptransport.mvp.constructionalbumpicture;

import com.kc.shiptransport.data.bean.CommitPictureBean;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.kc.shiptransport.mvp.BasePresenter;
import com.kc.shiptransport.mvp.BaseView;

import java.util.List;
import java.util.Set;

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
         * 加载更多
         * @param bean
         */
        void showImgMore(ConstructionAlbumPictureBean bean);

        /**
         * 删除结果
         * @param isSuccess
         * @param position
         */
        void showDeleteResult(boolean isSuccess, Set<Integer> position);

        /**
         * 上传图片结果
         * @param isSuccess
         */
        void showCommitPictureResult(boolean isSuccess);

        /**
         * 显示进度条
         * @param size
         */
        void showProgress(int size);

        /**
         * 更新进度条
         */
        void updateProgress();
    }

    interface Presenter extends BasePresenter {
        /**
         * 根据相册ID获取图片列表
         * @param albumItemID 相册ID
         */
        void getImgList(int pageSize, int pageCount, int albumItemID);

        /**
         * 删除照片
         * @param Table
         * @param albumCreator
         */
        void deleteImg(String Table, String albumCreator, List<ConstructionAlbumPictureBean.DataBean> datas, Set<Integer> positionSet);

        /**
         * 提交图片
         * @param albumID
         * @param list
         */
        void commitPicture(int albumID, List<CommitPictureBean> list);
    }
}
