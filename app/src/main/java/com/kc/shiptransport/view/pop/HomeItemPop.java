package com.kc.shiptransport.view.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnRecyclerviewItemClickListener;
import com.kc.shiptransport.mvp.acceptance.AcceptanceActivity;
import com.kc.shiptransport.mvp.home.PopAdapter;
import com.kc.shiptransport.mvp.plan.PlanActivity;
import com.kc.shiptransport.mvp.supply.SupplyActivity;


/**
 * @author 邱永恒
 * @time 2016/9/4  23:23
 * @desc ${TODD}
 */
public class HomeItemPop extends BasePopWindown {


    private static HomeItemPop mInstanst;
    private PopupWindow mPopupWindow;
    private RecyclerView mRvHomeSupply;
    private ImageView mIvHomePop;

    private HomeItemPop(Context context) {
        super(context);
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static HomeItemPop getInstanst(Context context) {
        if (mInstanst == null) {
            synchronized (HomeItemPop.class) {
                if (mInstanst == null) {
                    mInstanst = new HomeItemPop(context);
                }
            }
        }
        return mInstanst;
    }

    /**
     * 创建对话框
     *
     * @param context
     */
    protected void initViews(final Context context) {
        /**------------1. 初始化布局------------**/
        View view = View.inflate(context, R.layout.item_supply, null);


        /**------------2. 创建对话框, 绑定view------------**/
        //创建对话框(设置布局文件, 宽高)
        mPopupWindow = new PopupWindow(view, -1, -1);
        //让内部控件获取焦点
        mPopupWindow.setFocusable(true);
        //内部控件获取焦点后, 外部的控件就没有焦点了, 设置外部可以获取焦点
        mPopupWindow.setOutsideTouchable(true);
        //如果不马上显示popupwindow, 建议刷新界面
        mPopupWindow.update();
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());//设置一个空白的bitmap
        mPopupWindow.setAnimationStyle(R.anim.alpha);

        /**------------3. 初始化子控件------------**/
        mRvHomeSupply = (RecyclerView) view.findViewById(R.id.rv_home_supply);
        mRvHomeSupply.setLayoutManager(new GridLayoutManager(context, 3));
        PopAdapter adapter = new PopAdapter(context, new Integer[]{R.mipmap.plan, R.mipmap.acceptance, R.mipmap.supply_sand}, new String[]{"分包商进场计划", "待验收航次", "待验砂船次"});
        adapter.setOnItemClickListener(new OnRecyclerviewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        PlanActivity.navigateToPlanActivity(context);
                        break;
                    case 1:
                        AcceptanceActivity.navigateToAcceptanceActivity(context);
                        break;
                    case 2:
                        SupplyActivity.navigateToSupplyActivity(context);
                        break;
                }
                onDismiss();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        mRvHomeSupply.setAdapter(adapter);

        mIvHomePop = (ImageView) view.findViewById(R.id.iv_home_pop);
        mIvHomePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDismiss();
            }
        });

        /**------------4. 初始化数据------------**/


    }


    /**
     * 在某个容器中显示
     *
     * @param anchorView
     */
    public void onShow(View anchorView) {
        mPopupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }


    /**
     * 隐藏对话框
     */
    public void onDismiss() {
        mPopupWindow.dismiss();
    }

    private void tip(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}