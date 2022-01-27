package cn.chitanda.music

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cn.chitanda.music.media.connect.MusicServiceConnection
import cn.chitanda.music.media.extensions.id
import cn.chitanda.music.media.extensions.isPlayEnabled
import cn.chitanda.music.media.extensions.isPlaying
import cn.chitanda.music.media.extensions.isPrepared
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2022/1/24 17:01
 * @description:
 **/
private const val TAG = "MusicViewModel"

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {
    val nowPlaying: LiveData<MediaMetadataCompat> = musicServiceConnection.nowPlaying
    val playbackState: LiveData<PlaybackStateCompat> = musicServiceConnection.playbackState
    private var _currentPlaylist = ""

    fun play(playlist: String, mediaId: String?, pauseAllowed: Boolean = true) {

        val nowPlaying = nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        when {
            isPrepared && playlist == _currentPlaylist && mediaId == nowPlaying?.id -> {
                musicServiceConnection.playbackState.value?.let { playbackState ->
                    when {
                        playbackState.isPlaying ->
                            if (pauseAllowed) transportControls.pause() else Unit
                        playbackState.isPlayEnabled -> transportControls.play()
                        else -> {
                            Log.w(
                                TAG,
                                "Playable item clicked but neither play nor pause are enabled!" +
                                        " (mediaId=${mediaId})"
                            )
                        }
                    }
                }
            }
            _currentPlaylist.isEmpty() -> {
                musicServiceConnection.subscribe(playlist,
                    object : MediaBrowserCompat.SubscriptionCallback() {
                        override fun onChildrenLoaded(
                            parentId: String,
                            children: List<MediaBrowserCompat.MediaItem>
                        ) {
                            _currentPlaylist = playlist
                            transportControls.playFromMediaId(
                                mediaId ?: children.firstOrNull()?.mediaId, null
                            )
                        }
                    })
            }
            playlist != _currentPlaylist && _currentPlaylist.isNotEmpty() -> {
                musicServiceConnection.unsubscribe(
                    _currentPlaylist,
                    object : MediaBrowserCompat.SubscriptionCallback() {})
                musicServiceConnection.subscribe(playlist,
                    object : MediaBrowserCompat.SubscriptionCallback() {
                        override fun onChildrenLoaded(
                            parentId: String,
                            children: List<MediaBrowserCompat.MediaItem>
                        ) {
                            _currentPlaylist = playlist
                            transportControls.playFromMediaId(
                                mediaId ?: children.firstOrNull()?.mediaId, null
                            )
                        }
                    })
            }
            else -> transportControls.playFromMediaId(mediaId, null)
        }
    }

}