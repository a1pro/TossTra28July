package com.app.tosstra.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;

import com.app.tosstra.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.e("Notification", "Notification"+remoteMessage);
        try {
            remoteMessage.getData();
            Log.e("Notifiiiii", "1response==" + remoteMessage.getData().toString());
            Map<String, String> data = remoteMessage.getData();
            Log.e(TAG, "2response==" + data.toString());
            String questionTitle = data.get("body").toString();
            showNotification(questionTitle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("Notification", "Notification");
    }

    private void showNotification(String title) {
        Intent intent = null;
        intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        int value5 = getRandomNumberInRange(0, 99999);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, value5 , intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"1")
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo))
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_MAX).setChannelId("1")
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private static int getRandomNumberInRangeeee(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}