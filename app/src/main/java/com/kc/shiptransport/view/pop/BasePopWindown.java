package com.kc.shiptransport.view.pop;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;


/**
 * @author 邱永恒
 * @time 2016/9/8  17:19
 * @desc ${TODD}
 */
public abstract class BasePopWindown {

    protected final Context mContext;
    protected PopupWindow mPopupWindow;


    public BasePopWindown(Context context) {
        mContext = context;
        initViews(context);
    }

    protected abstract void initViews(Context context);

    /**
     * 设置对话框的显示位置
     * @param anchorView
     */
    public void onShow(View anchorView) {
        mPopupWindow.showAsDropDown(anchorView, 0, 0);
    }


    /**
     * 隐藏对话框
     */
    public void onDismiss() {
        mPopupWindow.dismiss();
    }
}
