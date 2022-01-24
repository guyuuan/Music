package cn.chitanda.music.media

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import com.example.android.uamp.media.extensions.displaySubtitle
import com.example.android.uamp.media.extensions.displayTitle
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * @author: Chen
 * @createTime: 2022/1/24 17:21
 * @description:
 **/
const val NOW_PLAYING_CHANNEL_ID = "cn.chitanda.music.media.NOW_PLAYING"
const val NOW_PLAYING_NOTIFICATION_ID = 0x666

class MediaNotificationManager(
    context: Context,
    sessionToken: MediaSessionCompat.Token,
    notificationListener: PlayerNotificationManager.NotificationListener
) {
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private val notificationManager: PlayerNotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
        }
        val mediaController = MediaControllerCompat(context, sessionToken)
        notificationManager = PlayerNotificationManager.Builder(
            context,
            NOW_PLAYING_NOTIFICATION_ID,
            NOW_PLAYING_CHANNEL_ID
        ).apply {
            setMediaDescriptionAdapter(DescriptionAdapter(mediaController))
            setNotificationListener(notificationListener)
        }.build()
    }

    private fun createNotificationChannel(
        context: Context,
    ) {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannelCompat.Builder(
            NOW_PLAYING_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_LOW
        ).setName(context.getString(R.string.notification_channel))
            .setDescription(context.getString(R.string.notification_channel_description))
            .build()
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(player: Player){
        notificationManager.setPlayer(player)
    }
    fun hideNotification(){
        notificationManager.setPlayer(null)
    }

    private inner class DescriptionAdapter(private val controller: MediaControllerCompat) :
        PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player) = controller.metadata.displayTitle ?: ""

        override fun createCurrentContentIntent(player: Player): PendingIntent? =
            controller.sessionActivity

        override fun getCurrentContentText(player: Player) = controller.metadata.displaySubtitle

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            return null
        }

    }
}