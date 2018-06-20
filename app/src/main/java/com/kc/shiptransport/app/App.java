package com.kc.shiptransport.app;

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.kc.shiptransport.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.umeng.analytics.MobclickAgent;
import com.vise.log.ViseLog;
import com.vise.log.inner.LogcatTree;
import com.vise.xsnow.common.ViseConfig;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.interceptor.HttpLogInterceptor;

import org.litepal.LitePalApplication;

import java.io.File;
import java.util.HashMap;

import okhttp3.Cache;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zlc.season.rxdownload2.RxDownload;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:30
 * @desc ${TODD}
 */

public class App extends MultiDexApplication {

    private static Context context;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        LitePalApplication.initialize(this);

        // 集成测试
        MobclickAgent.setDebugMode(true);

        // 错误统计 (默认开启)
        MobclickAgent.setCatchUncaughtExceptions(true);

        // 初始化XLog
        LogConfiguration config = new LogConfiguration.Builder()
                .tag("SHIP_TAG") // TAG
                .t() // 运行打印线程信息
                .b() // 允许答应日志边框
                .st(3) // 允许打印深度为3的调用栈信息
                .build();
        XLog.init(LogLevel.ALL, config);

        // 初始化下载
        RxDownload.getInstance(context)
                .defaultSavePath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()) //设置默认的下载路径
                .maxThread(3);

        // 初始化XSnow
        initXSnow();

        initLog();

        initUtils();
    }

    private void initXSnow() {
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl("https://cchk3.kingwi.org/AppService/cchk3WebService.asmx/")
                //配置全局请求头
                .globalHeaders(new HashMap<String, String>())
                //配置全局请求参数
                .globalParams(new HashMap<String, String>())
                //配置读取超时时间，单位秒
                .readTimeout(30)
                //配置写入超时时间，单位秒
                .writeTimeout(30)
                //配置连接超时时间，单位秒
                .connectTimeout(30)
                //配置请求失败重试次数
                .retryCount(3)
                //配置请求失败重试间隔时间，单位毫秒
                .retryDelayMillis(1000)
                //配置是否使用cookie
                .setCookie(true)
                //配置自定义cookie
                //                .apiCookie(new ApiCookie(this))
                //配置是否使用OkHttp的默认缓存
                .setHttpCache(true)
                //配置OkHttp缓存路径
                //                .setHttpCacheDirectory(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR))
                //配置自定义OkHttp缓存
                //                .httpCache(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义离线缓存
                .cacheOffline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置自定义在线缓存
                .cacheOnline(new Cache(new File(ViseHttp.getContext().getCacheDir(), ViseConfig.CACHE_HTTP_DIR), ViseConfig.CACHE_MAX_SIZE))
                //配置开启Gzip请求方式，需要服务器支持
                //                .postGzipInterceptor()
                //配置应用级拦截器
                .interceptor(new HttpLogInterceptor()
                        .setLevel(HttpLogInterceptor.Level.BODY))
                //配置网络拦截器
                //                .networkInterceptor(new NoCacheInterceptor())
                //配置转换工厂
                .converterFactory(GsonConverterFactory.create())
                //配置适配器工厂
                .callAdapterFactory(RxJava2CallAdapterFactory.create())
        //配置连接池
        //                .connectionPool(new ConnectionPool())
        ;
    }

    private void initLog() {
        ViseLog.getLogConfig()
                .configAllowLog(true)//是否输出日志
                .configShowBorders(true);//是否排版显示
        ViseLog.plant(new LogcatTree());//添加打印日志信息到Logcat的树
    }

    private void initUtils() {
        Utils.init(this);
    }
}
