package com.kc.shiptransport.mvp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;

/**
 * @author 邱永恒
 * @time 2016/9/1  11:28
 * @desc ${TODD}
 */
public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressDialog;
    private AlertDialog alertDialog;
    protected Toast mToast;
    private ProgressDialog mProgress;
    private int progress;


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
    public void showProgressDailog(String title, String msg, final OnDailogCancleClickListener listener) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(BaseActivity.this);
        }
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        // 设置dialog外面不可点击
        progressDialog.setCanceledOnTouchOutside(false);
        // 设置返回键不可取消显示
        //progressDialog.setCancelable(false);
        // 设置进度条样式 (水平)
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("==", "取消显示");
                // TODO 取消rxjava的任务
                listener.onCancle(progressDialog);
            }
        });
    }

    /**
     * 进度条
     */
    public void progressDialog(String title, int max, DialogInterface.OnClickListener listenter){
        progress = 0;
        mProgress = new ProgressDialog(BaseActivity.this);
        // 设置图标
        mProgress.setIcon(R.mipmap.ic_launcher);
        mProgress.setTitle(title);
        mProgress.setMax(max);
        // 设置进度条样式 (水平)
        mProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgress.setButton(AlertDialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mProgress.dismiss();
            }
        });
        mProgress.setButton(AlertDialog.BUTTON_NEUTRAL, "取消", listenter);

        mProgress.show();
//        new Thread(new Runnable() {
//            int progress = 0;
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (progress <= max) {
//                    mProgress.setProgress(progress);
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    progress++;
//                }
//            }
//        }).start();
    }

    public void updataProgress(OnProgressFinishListener listener) {
        progress ++;
        mProgress.setProgress(progress);

        if (progress >= mProgress.getMax()) {
            mProgress.dismiss();
            Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
            listener.onFinish();
        }
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
