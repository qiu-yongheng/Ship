package com.kc.shiptransport.app;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;


/**
 * 本地异常处理类
 *
 * @author PLUTO
 */
public class LocalFileHandler extends BaseExceptionHandler {

    private Context context;

    public LocalFileHandler(Context context) {
        this.context = context;
    }

    /**
     * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return
     */
    @Override
    public boolean handleException(Throwable ex) {
        if (ex == null)
            return false;

        new Thread() {
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "很抱歉，程序出现异常，正在从错误中恢复", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //保存错误日志
        saveLog(ex);

        return true;
    }

    /**
     * 保存错误日志到本地
     *
     * @param ex
     */
    private void saveLog(Throwable ex) {
        try {

//            File path = new File(FileUtil.getDiskCacheDir(context) + File.separator + "log");
//
//            if (!path.exists()) {
//                path.mkdirs();
//            }
//            File errorFile = new File(path + File.separator + "crash.txt");
//
//            if (!errorFile.exists()) {
//                errorFile.createNewFile();
//            }

            File file = null;
            String fileDstPath = "";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 保存到sd卡  File.separator是分隔符的意思, 在不同的系统中, 分隔符不一样的, 这样写可以达到适配的效果
                fileDstPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "AsFile" + File.separator + "crash.txt";

                File homeDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "AsFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdirs();
                }
            } else {
                // 保存到file目录
                fileDstPath = context.getFilesDir().getAbsolutePath()
                        + File.separator + "AsFile" + File.separator + "crash.txt";

                File homeDir = new File(context.getFilesDir().getAbsolutePath()
                        + File.separator + "AsFile" + File.separator);
                if (!homeDir.exists()) {
                    homeDir.mkdir();
                }
            }
            file = new File(fileDstPath);
            OutputStream out = new FileOutputStream(file, true);
            out.write(("\n\n-----错误分割线" + dateFormat.format(new Date()) + "-----\n\n").getBytes());
            PrintStream stream = new PrintStream(out);
            ex.printStackTrace(stream);
            stream.flush();
            out.flush();
            stream.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
