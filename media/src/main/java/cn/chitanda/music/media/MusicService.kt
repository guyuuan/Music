package cn.chitanda.music.media

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ui.PlayerNotificationManager


class MusicService : MediaBrowserServiceCompat() {
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var mediaSessionConnector: MediaSessionConnector
    private var isForegroundService = false
    private lateinit var notificationManager: MediaNotificationManager
    override fun onCreate() {
        super.onCreate()
        val sessionPendingInent =
            packageManager?.getLaunchIntentForPackage(packageName)?.let { intent ->
                PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_IMMUTABLE
                )
            }
        mediaSession = MediaSessionCompat(this, "MusicService")
            .apply {
                setSessionActivity(sessionPendingInent)
                isActive = true
            }
        sessionToken = mediaSession.sessionToken
        notificationManager =
            MediaNotificationManager(this, mediaSession.sessionToken, PlayerNotificationListener())

        mediaSessionConnector = MediaSessionConnector(mediaSession).apply {
            setPlaybackPreparer(PlaybackPreparer())
        }

//        notificationManager.showNotification()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(BROWSABLE_ROOT, null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.detach()
        result.sendResult(null)
    }

    override fun onDestroy() {
        notificationManager.hideNotification()
        super.onDestroy()
    }

//    private inner class QueueNavigator(
//        mediaSession: MediaSessionCompat
//    ) : TimelineQueueNavigator(mediaSession) {
//        override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat =
//
//    }

    private inner class PlaybackPreparer : MediaSessionConnector.PlaybackPreparer {
        override fun onCommand(
            player: Player,
            command: String,
            extras: Bundle?,
            cb: ResultReceiver?
        ): Boolean {
            return true
        }

        /**
         * UAMP supports preparing (and playing) from search, as well as media ID, so those
         * capabilities are declared here.
         *
         * TODO: Add support for ACTION_PREPARE and ACTION_PLAY, which mean "prepare/play something".
         */
        override fun getSupportedPrepareActions(): Long =
            PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH

        override fun onPrepare(playWhenReady: Boolean) {
        }

        override fun onPrepareFromMediaId(
            mediaId: String,
            playWhenReady: Boolean,
            extras: Bundle?
        ) {
        }

        /**
         * This method is used by the Google Assistant to respond to requests such as:
         * - Play Geisha from Wake Up on UAMP
         * - Play electronic music on UAMP
         * - Play music on UAMP
         *
         * For details on how search is handled, see [AbstractMusicSource.search].
         */
        override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle?) {

        }

        override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle?) = Unit

        /**
         * Builds a playlist based on a [MediaMetadataCompat].
         *
         * TODO: Support building a playlist by artist, genre, etc...
         *
         * @param item Item to base the playlist on.
         * @return a [List] of [MediaMetadataCompat] objects representing a playlist.
         */
        private fun buildPlaylist(item: MediaMetadataCompat): List<MediaMetadataCompat> =
            emptyList()
    }

    private inner class PlayerNotificationListener :
        PlayerNotificationManager.NotificationListener {
        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            if (ongoing && !isForegroundService) {
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(applicationContext, this@MusicService.javaClass)
                )

                startForeground(notificationId, notification)
                isForegroundService = true
            }
        }

        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            stopForeground(true)
            isForegroundService = false
            stopSelf()
        }
    }
}

const val BROWSABLE_ROOT = "/"
const val EMPTY_ROOT = "@empty@"
const val RECOMMENDED_ROOT = "__RECOMMENDED__"
const val ALBUMS_ROOT = "__ALBUMS__"
const val RECENT_ROOT = "__RECENT__"