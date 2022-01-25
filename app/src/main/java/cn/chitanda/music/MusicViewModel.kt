package cn.chitanda.music

import android.support.v4.media.MediaBrowserCompat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.media.connect.MusicServiceConnection
import cn.chitanda.music.media.extensions.id
import cn.chitanda.music.media.extensions.isPlayEnabled
import cn.chitanda.music.media.extensions.isPlaying
import cn.chitanda.music.media.extensions.isPrepared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            musicServiceConnection.subscribe("7193604184",
                object : MediaBrowserCompat.SubscriptionCallback() {
                    override fun onChildrenLoaded(
                        parentId: String,
                        children: List<MediaBrowserCompat.MediaItem>
                    ) {
                        Log.d(TAG, "onChildrenLoaded: $children")
                    }
                })
            delay(2000)
//            musicServiceConnection.sendCommand(command = "123", parameters = null) { i, b ->
//                Log.d(TAG, "init: $i $b")
//            }
            play("1859245776")
        }

    }

    fun play(mediaId: String, pauseAllowed: Boolean = true) {
        val nowPlaying = musicServiceConnection.nowPlaying.value
        val transportControls = musicServiceConnection.transportControls

        val isPrepared = musicServiceConnection.playbackState.value?.isPrepared ?: false
        Log.d(TAG, "play: isPrepared $isPrepared")
        if (isPrepared && mediaId == nowPlaying?.id) {
            musicServiceConnection.playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying ->
                        if (pauseAllowed) transportControls.pause() else Unit
                    playbackState.isPlayEnabled -> transportControls.play()
                    else -> {
                        Log.w(
                            TAG, "Playable item clicked but neither play nor pause are enabled!" +
                                    " (mediaId=${mediaId})"
                        )
                    }
                }
            }
        } else {
            transportControls.playFromMediaId(mediaId, null)
        }
    }

}