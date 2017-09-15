package com.kc.shiptransport.badge;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.kc.shiptransport.R;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 发送通知, 给桌面图标添加角标
 */
public class BadgeIntentService extends IntentService {

    private int notificationId = 0;

    public BadgeIntentService() {
        super("BadgeIntentService");
    }

    private NotificationManager mNotificationManager;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int badgeCount = intent.getIntExtra("badgeCount", 0);

            if (mNotificationManager == null) {
                return;
            }

            mNotificationManager.cancel(notificationId);
            notificationId++;

            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                .setContentTitle("待办")
                .setContentText("当前有" + badgeCount + "个待办事项需要处理")
                .setSmallIcon(R.mipmap.app_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            Notification notification = builder.build();


            // 发送通知
            ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
            mNotificationManager.notify(notificationId, notification);
        }
    }
}
