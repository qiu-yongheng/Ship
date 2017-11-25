package com.kc.shiptransport.mvp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.interfaze.OnDailogCancleClickListener;
import com.kc.shiptransport.interfaze.OnDailogOKClickListener;
import com.kc.shiptransport.interfaze.OnProgressFinishListener;
import com.kc.shiptransport.util.LogUtil;
import com.umeng.analytics.MobclickAgent;

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
    private AlertDialog.Builder editBuilder;
    private AlertDialog editDialog;
    private AlertDialog.Builder singleBuilder;
    private int singleSelectedId;
    private AlertDialog singleDialog;
    private AlertDialog.Builder singleNoButtonBuilder;
    private int singleNoButtonSelectedId;
    private AlertDialog singleNoButtonDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        // 实现背景图与状态栏融合
        //        if (Build.VERSION.SDK_INT >= 21) {
        //            View decorView = getWindow().getDecorView();
        //            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //            getWindow().setStatusBarColor(Color.TRANSPARENT);
        //        }
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
                listener.onCancel(progressDialog);
            }
        });
    }

    /**
     * 进度条
     */
    public void progressDialog(String title, int max, DialogInterface.OnClickListener listenter) {
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

    /**
     * 更新进度
     * @param listener
     */
    public void updataProgress(OnProgressFinishListener listener) {
        progress++;
        mProgress.setProgress(progress);

        if (progress >= mProgress.getMax()) {
            mProgress.dismiss();
            Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
            listener.onFinish();
        }
    }

    public void hideProgress() {
        if (mProgress != null) {
            if (mProgress.isShowing()) {
                mProgress.dismiss();
            }
        }
    }

    /**
     * 改变进度对话框信息
     *
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
     * @param okListener
     */
    public void showDailog(String title, String msg, DialogInterface.OnClickListener okListener) {
        showDailog(title, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        }, okListener);
    }

    /**
     * 显示对话框
     *
     * @param msg
     */
    public void showDailog(String title, String msg, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener okListener) {
        showDailog(title, msg, "取消", "确认", cancelListener, okListener);
    }

    /**
     * 显示对话框
     *
     * @param msg
     */
    public void showDailog(String title, String msg, String cancel, String ok, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener okListener) {
        alertDialog = new AlertDialog.Builder(BaseActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, cancel, cancelListener);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ok, okListener);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    public void setDialogDismissListener(DialogInterface.OnDismissListener listener) {
        if (alertDialog != null) {
            alertDialog.setOnDismissListener(listener);
        } else {
            LogUtil.e("alertDialog can not null");
        }
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
     * 显示输入框弹窗
     *
     * @param view
     * @param title
     * @param cancel
     * @param ok
     * @param cancelListener
     * @param okListener
     */
    public void showEditDailog(View view, String title, String cancel, String ok, DialogInterface.OnClickListener cancelListener, final OnDailogOKClickListener okListener) {
        editBuilder = new AlertDialog.Builder(BaseActivity.this);

        if (view == null) {
            // 如果没有指定布局, 使用默认布局
            view = LayoutInflater.from(this).inflate(R.layout.dialog_edit, null);
        }

        // 设置加载的布局
        editBuilder.setView(view);

        final EditText userInput = (EditText) view.findViewById(R.id.editTextDialogUserInput);
        final TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);

        tvTitle.setText(title);

        // set dialog message
        editBuilder.setCancelable(false);

        // create alert dialog
        editDialog = editBuilder.create();

        editDialog.setButton(AlertDialog.BUTTON_NEUTRAL, cancel, cancelListener);
        editDialog.setButton(AlertDialog.BUTTON_POSITIVE, ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                okListener.onOK(userInput.getText().toString().trim());
            }
        });

        // show it
        editDialog.show();
    }

    /**
     * 显示单选框
     * @param data
     * @param title
     * @param cancel
     * @param ok
     * @param cancelListener
     * @param okListener
     */
    public void showSingleDailog(String[] data, String title, String cancel, String ok, DialogInterface.OnClickListener cancelListener, final OnDailogOKClickListener okListener) {
        singleBuilder = new AlertDialog.Builder(BaseActivity.this);
        singleSelectedId = -1;

        singleBuilder.setTitle(title);
        singleBuilder.setSingleChoiceItems(data, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                singleSelectedId = which;
            }
        });


        // set dialog message
        singleBuilder.setCancelable(false);

        // create alert dialog
        singleDialog = singleBuilder.create();

        singleDialog.setButton(AlertDialog.BUTTON_NEUTRAL, cancel, cancelListener);
        singleDialog.setButton(AlertDialog.BUTTON_POSITIVE, ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (singleSelectedId >= 0) {
                    okListener.onOK(singleSelectedId);
                } else {
                    singleSelectedId = 0;
                    okListener.onOK(singleSelectedId);
                    // 业务逻辑
                }
            }
        });

        // show it
        singleDialog.show();
    }

    /**
     * 显示单选框, 没有确定按钮
     * @param data
     * @param title
     * @param cancelListener
     * @param okListener
     */
    public void showSingleNoButtonDailog(String[] data, String title, DialogInterface.OnClickListener cancelListener, final OnDailogOKClickListener okListener) {
        singleNoButtonBuilder = new AlertDialog.Builder(BaseActivity.this);
        singleNoButtonSelectedId = -1;

        singleNoButtonBuilder.setTitle(title);
        singleNoButtonBuilder.setItems(data, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                singleNoButtonSelectedId = which;
                okListener.onOK(singleNoButtonSelectedId);

                singleNoButtonDialog.hide();
            }
        });

        // set dialog message
        singleNoButtonBuilder.setCancelable(true);

        // create alert dialog
        singleNoButtonDialog = singleNoButtonBuilder.create();

        singleNoButtonDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", cancelListener);

        // show it
        singleNoButtonDialog.show();
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

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    @Override
//    public void onBackPressed() {
//        if (!BackHandlerHelper.handleBackPress(this)) {
//            super.onBackPressed();
//        }
//    }
}
