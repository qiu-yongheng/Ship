package com.kc.shiptransport.mvp.about;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.shiptransport.R;
import com.kc.shiptransport.db.versionupdate.VersionUpdate;
import com.kc.shiptransport.download.DownloadService;
import com.kc.shiptransport.mvp.BaseActivity;
import com.kc.shiptransport.util.AppInfoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author qiuyongheng
 * @time 2017/7/18  13:47
 * @desc ${TODD}
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_update)
    RelativeLayout rlUpdate;
    @BindView(R.id.tv_version_code)
    TextView tvVersionCode;
    private AboutPresenter presenter;

    private DownloadService.DownloadBinder downloadBinder;

    /**
     * 绑定服务的回调
     */
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        presenter = new AboutPresenter(this);

        initView();
        initListener();

        /** 启动服务, 绑定服务 */
        Intent intent = new Intent(this, DownloadService.class);
        startService(intent); // 启动服务
        bindService(intent, connection, BIND_AUTO_CREATE); // 绑定服务
    }

    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back);
        getSupportActionBar().setTitle("关于综合生产调度管理系统");

        String versionName = AppInfoUtils.getVersionName(this);
        int versionCode = AppInfoUtils.getVersionCode(this);

        tvVersion.setText("v " + versionName);
        tvVersionCode.setText(String.valueOf(versionCode));
    }

    private void initListener() {
        // 检查更新
        rlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this, "正在检查新版本", Toast.LENGTH_SHORT).show();

                presenter.checkVersionCode();
            }
        });
    }

    public void showDialog(final VersionUpdate versionUpdate) {
        showDailog("版本更新", "有新版本发布, 是否更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 取消
                presenter.cancelDownload(downloadBinder);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 更新
                presenter.startDownload(downloadBinder, versionUpdate);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent, bundle);
    }

    /**
     * 销毁时, 取消服务绑定
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
