package cn.chitanda.music

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.media.connect.MusicServiceConnection
import cn.chitanda.music.media.extensions.currentPlayBackPosition
import cn.chitanda.music.media.extensions.duration
import cn.chitanda.music.media.extensions.id
import cn.chitanda.music.media.extensions.isPlayEnabled
import cn.chitanda.music.media.extensions.isPlaying
import cn.chitanda.music.media.extensions.isPrepared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToLong

/**
 * @author: Chen
 * @createTime: 2022/1/24 17:01
 * @description:
 **/
private const val TAG = "MusicViewModel"
private const val UPDATE_CURRENT_MUSIC_PLAY_POSITION = 100L
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {
    val nowPlaying: LiveData<MediaMetadataCompat> = musicServiceConnection.nowPlaying
    val playbackState: LiveData<PlaybackStateCompat> = musicServiceConnection.playbackState
    private var _currentPlaylist = ""
    private val _currentPosition = MutableLiveData(0L)
    val currentPosition: LiveData<Long> get() = _currentPosition

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

    fun pause() {
        musicServiceConnection.transportControls.pause()
    }

    fun resume() {
        musicServiceConnection.transportControls.play()
    }

    fun seekTo(percent: Float) {
        val nowPlaying = nowPlaying.value?.let { music ->
            musicServiceConnection.transportControls.seekTo((percent * music.duration).roundToLong())
        }
    }

    fun toNext(){
        musicServiceConnection.transportControls.skipToNext()
    }

    fun toPrevious(){
        musicServiceConnection.transportControls.skipToPrevious()
    }

    init {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                val currPosition = playbackState.value?.currentPlayBackPosition ?: continue
                if (_currentPosition.value != currPosition)
                    _currentPosition.postValue(currPosition)
                delay(UPDATE_CURRENT_MUSIC_PLAY_POSITION)
            }
        }
    }
}