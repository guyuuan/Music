package cn.chitanda.music.media

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import cn.chitanda.music.http.bean.SongUrl
import cn.chitanda.music.http.bean.Songs
import cn.chitanda.music.http.bean.artists
import cn.chitanda.music.media.extensions.*
import cn.chitanda.music.repository.SongsRepository
import kotlinx.coroutines.*

/**
 * @author: Chen
 * @createTime: 2022/1/25 14:45
 * @description:
 **/

private const val TAG = "MusicSourceImp"

class MusicSourceImp(private val songsRepository: SongsRepository) : AbstractMusicSource() {
    private var catalog: List<MediaMetadataCompat> = emptyList()
    private var loadJob: Job? = null
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        state = STATE_INITIALIZING
    }

    override fun load(playlist: String) {
        if (loadJob?.isActive == true) loadJob?.cancel()
        try {
            loadJob = coroutineScope.launch {
                updateCatLog(playlist)
            }
        } catch (e: Exception) {
            Log.e(TAG, "load: ", e)
        }
    }

    private suspend fun updateCatLog(
        playlist: String
    ) = withContext(Dispatchers.Default) {
        val response = songsRepository.getPlaylistAllSongs(playlist)
        if (response.code == 200) {
            val url = response.data?.joinToString { it.id.toString() }?.let { ids ->
                songsRepository.getSongUrl(ids)
            }
            response.data?.mapIndexed { i, song ->
                MediaMetadataCompat.Builder().from(song, url?.data?.getOrNull(i)).build()
            }
        }
        state = STATE_INITIALIZED
    }

    override fun iterator(): Iterator<MediaMetadataCompat> = catalog.listIterator()
}

fun MediaMetadataCompat.Builder.from(
    song: Songs.Song,
    url: SongUrl.Url?,
): MediaMetadataCompat.Builder {
    // The duration from the JSON is given in seconds, but the rest of the code works in
    // milliseconds. Here's where we convert to the proper units.
//    val durationMs = TimeUnit.SECONDS.toMillis(jsonMusic.duration)

    id = song.id.toString()
    title = song.name
    artist = song.artists
    album = song.al?.name
//    duration = durationMs
//    genre = jsonMusic.genre
    mediaUri = url?.url
    albumArtUri = song.al?.picUrl.toString()
//    trackNumber = jsonMusic
//    trackCount = jsonMusic.totalTrackCount
    flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE

    // To make things easier for *displaying* these, set the display properties as well.
    displayTitle = song.name
    displaySubtitle = song.artists
    displayDescription = song.al?.name
    displayIconUri = song.al?.picUrl.toString()

    // Add downloadStatus to force the creation of an "extras" bundle in the resulting
    // MediaMetadataCompat object. This is needed to send accurate metadata to the
    // media session during updates.
    downloadStatus = MediaDescriptionCompat.STATUS_NOT_DOWNLOADED

    // Allow it to be used in the typical builder style.
    return this
}