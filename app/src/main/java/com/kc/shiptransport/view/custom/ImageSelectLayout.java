package com.kc.shiptransport.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kc.shiptransport.R;
import com.kc.shiptransport.data.bean.img.ImgList;
import com.kc.shiptransport.util.helper.ImgSelectAdapterHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/6/17  17:27
 * @desc
 */

public class ImageSelectLayout extends FrameLayout {
    private String title;
    private TextView tvTitle;
    private RecyclerView recyclerView;
    private ImgSelectAdapterHelper imgSelectAdapterHelper;

    public ImageSelectLayout(@NonNull Context context) {
        this(context, null);
    }

    public ImageSelectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageSelectLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImageSelectLayout);
        title = typedArray.getString(R.styleable.ImageSelectLayout_title);
        typedArray.recycle();
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_input_img, this, false);
        tvTitle = (TextView) linearLayout.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        recyclerView = (RecyclerView) linearLayout.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        imgSelectAdapterHelper = new ImgSelectAdapterHelper();
        recyclerView.setAdapter(imgSelectAdapterHelper.getSelectAdapter(getContext(), new ArrayList<ImgList>(), true));

        addView(linearLayout);
    }

    /**
     * 是否可以修改图片
     * @param isCanChange
     * @return
     */
    public void isCanChange(boolean isCanChange) {
        imgSelectAdapterHelper.isCanChange(isCanChange);
    }

    /**
     * 添加图片数据源
     * @param list
     */
    public void setImgList(List<ImgList> list) {
        imgSelectAdapterHelper.addData(list);
    }
}
