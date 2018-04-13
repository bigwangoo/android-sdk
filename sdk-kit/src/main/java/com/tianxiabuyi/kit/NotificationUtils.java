package com.demo.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Notification
 * Created by WangYD on 2017/6/16.
 */
public class NotificationUtils {

    private static NotificationManager manager;

    public static void sendNotification(Context context, int id, int smallIcon, CharSequence contentTitle,
                                        CharSequence contentText, PendingIntent pendingIntent) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setTicker("")
                .setSmallIcon(smallIcon)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        Notification notification = builder.build();
        notification.priority = Notification.PRIORITY_HIGH;
        manager.notify(id, notification);
    }

    public void sendProgressNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void sendBigPicNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void sendBigTxtNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void sendMailBoxNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }

    public void sendMediaNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.build();
        manager.notify(1, notification);
    }


//     TaskStackBuilder tsb = TaskStackBuilder.create(this);
//        tsb.addParentStack(MyMessageActivity.class);
//        tsb.addNextIntent(resultIntent);
//    PendingIntent pi = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

}
