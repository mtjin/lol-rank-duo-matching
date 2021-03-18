package com.mtjin.lolrankduo.service.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val to = remoteMessage.to.toString()
        if (remoteMessage.data["isScheduled"] == "false") { // 즉시 전송
            sendNotification(remoteMessage)
        } else { // 예약전송

        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
    }

    private fun scheduleAlarm(
        scheduledTime: String,
        title: String,
        message: String
    ) {
    }

    override fun onNewToken(token: String) {
        Log.d("FCM new token -> ", token)
    }
}