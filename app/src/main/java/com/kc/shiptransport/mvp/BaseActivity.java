package com.kc.shiptransport.mvp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 邱永恒
 * @time 2016/9/1  11:28
 * @desc ${TODD}
 */
public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    protected Toast mToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void tip(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDailog(String title, String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * 改变进度对话框信息
     * @param title
     * @param msg
     */
    public void changeProgressDailogInfo(String title, String msg) {
        if (progressDialog != null) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(msg);
        }
    }

    /**
     * 隐藏进度对话框
     */
    public void hideProgressDailog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    /**
     * 显示对话框
     *
     * @param msg
     * @param listenter
     */
    public void showDailog(String title, String msg, DialogInterface.OnClickListener listenter) {
        alertDialog = new AlertDialog.Builder(BaseActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确认", listenter);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 隐藏对话框
     */
    public void hideDailog() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }

    /**
     * 取消土司显示
     */
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 点击返回时, 取消土司显示
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelToast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        if (alertDialog != null) {
            alertDialog.dismiss();
            progressDialog = null;
        }
        //单例模式不能关闭, 数据库会打不开
        //        mDao.close();
        //        mDao = null;

        Log.d("==", "销毁");
    }
}
