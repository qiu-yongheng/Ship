package com.kc.shiptransport.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.util.RxGalleryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/3/9  15:42
 * @desc ImgViewPageAdapter
 */
public class ImgViewPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImgList> mData;
    private LayoutInflater layoutInflater;
    private View mCurrentView;

    public ImgViewPageAdapter(Context context, ArrayList<ImgList> data) {
        mContext = context;
        mData = data;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final String imageUrl = mData.get(position).getPath();
        String remark = mData.get(position).getRemark();
        View view = layoutInflater.inflate(R.layout.activity_image_list, container, false);
        PhotoView imageView = (PhotoView) view.findViewById(R.id.img);
        TextView tvRemark = (TextView) view.findViewById(R.id.tv_remark);
        if (!TextUtils.isEmpty(remark)) {
            tvRemark.setVisibility(View.VISIBLE);
            tvRemark.setText("摘要: " + remark);
        } else {
            tvRemark.setVisibility(View.GONE);
        }

        imageView.enable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) ImgViewPageAdapter.this.mContext;
                activity.finish();
            }
        });
        RxGalleryUtil.showImage(mContext, imageUrl, mContext.getResources().getDrawable(R.mipmap.load), null, imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }
}
