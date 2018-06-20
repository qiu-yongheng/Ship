package com.kc.shiptransport.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.kc.shiptransport.adapter.delegate.AlbumPictureDelegate;
import com.kc.shiptransport.data.bean.album.ConstructionAlbumPictureBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * @author 邱永恒
 * @time 2017/8/16  9:50
 * @desc 相册图片adapter
 */

public class AlbumAdapter extends MultiItemTypeAdapter<ConstructionAlbumPictureBean.DataBean>{
    public AlbumAdapter(Context context, Fragment fragment, int albumItem, List<ConstructionAlbumPictureBean.DataBean> datas) {
        super(context, datas);

        addItemViewDelegate(new AlbumPictureDelegate(context, albumItem, fragment));
        //addItemViewDelegate(new AlbumAddDelegate(context, albumItem, fragment));
    }

//    @Override
//    public int getItemCount() {
//        return getDatas().size() + 1;
//    }
}
