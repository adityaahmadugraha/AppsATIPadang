package com.aditya.appsatipadang

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aditya.appsatipadang.teknik.ui_teknisi.nontifikasi_laporan.ActivityNontofikasiLaporanTeknisi
import com.aditya.appsatipadang.utils.Constant.FCM_CHANNEL_ID
import com.aditya.appsatipadang.utils.Constant.FCM_CHANNEL_NAME
import com.aditya.appsatipadang.utils.Constant.IDLAPORAN
import com.aditya.appsatipadang.utils.Constant.TAG
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@Suppress("NAME_SHADOWING")
class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: ${remoteMessage.from}")

        remoteMessage.data.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        remoteMessage.notification?.let {
            val id = remoteMessage.data["id"].toString()
            Log.d(TAG, "Message Notification Body: $id")
            sendNotification(it.title.toString(), it.body.toString(), id)
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

    private fun sendNotification(title: String, body : String, idlaporan : String? = null) {
        val title = title
        val body = body
        // kirim data intent
        val intent = Intent(this, ActivityNontofikasiLaporanTeknisi::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(IDLAPORAN, idlaporan)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        Log.d("CEKPAJAKO:::", "sendNotification: $idlaporan")
        val notification = if (idlaporan.isNullOrEmpty())
            NotificationCompat.Builder(this, FCM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_clear_all)
                .setContentTitle(title)
                .setContentText(body)
                .build()
        else
            NotificationCompat.Builder(this, FCM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_clear_all)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }
}