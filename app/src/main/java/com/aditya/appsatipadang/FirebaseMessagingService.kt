package com.aditya.appsatipadang

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aditya.appsatipadang.utils.Constant.FCM_CHANNEL_ID
import com.aditya.appsatipadang.utils.Constant.FCM_CHANNEL_NAME
import com.aditya.appsatipadang.utils.Constant.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage?.from}")

        remoteMessage?.data?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            sendNotification(it.title.toString(), it.body.toString())
        }

        FirebaseApp.initializeApp(this)
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            val fcmChannel = NotificationChannel(FCM_CHANNEL_ID, FCM_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            fcmChannel.enableVibration(true)
            fcmChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)

            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(fcmChannel)
        }

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.i("FCM_TOKEN", token)
    }

    private fun sendNotification(title: String, body : String) {
        val title = title
        val body = body
        val notification = NotificationCompat.Builder(this, FCM_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification_clear_all)
            .setContentTitle(title)
            .setContentText(body)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}