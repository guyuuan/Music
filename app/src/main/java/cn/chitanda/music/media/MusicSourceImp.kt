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
private const val STEP = 20

class MusicSourceImp(private val songsRepository: SongsRepository) : AbstractMusicSource() {
    private var catalog: List<MediaMetadataCompat> = emptyList()
    private var loadJob: Job? = null
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        state = STATE_INITIALIZING
    }

    override fun load(playlist: String) {
        state = STATE_INITIALIZING
        if (loadJob?.isActive == true) {
            Log.d(TAG, "load: cancel job")
            loadJob?.cancel()
        }
        try {
            loadJob = coroutineScope.launch {
                updateCatLog(playlist)
            }
        } catch (e: Exception) {
            Log.e(TAG, "load: ", e)
            state = STATE_ERROR
        }
    }

    private suspend fun updateCatLog(
        playlist: String
    ) = withContext(Dispatchers.Default) {
        try {
            val response = songsRepository.getPlaylistAllSongs(playlist)
            if (response.code == 200) {
                val urls = mutableMapOf<Long?, SongUrl.Url>()
                response.data?.map { it.id.toString() }?.let { ids ->
                    val tasks = mutableListOf<Deferred<Unit?>>()
                    for (i in ids.indices step STEP) {
                        tasks += async {
                            songsRepository.getSongUrl(
                                ids.subList(
                                    i, (i + STEP - 1).coerceAtMost(ids.lastIndex) + 1
                                ).joinToString(separator = ",") { it }).data?.associateBy { it.id }
                                ?.let { map ->
                                    urls.putAll(map)
                                }
                        }
                    }
                    tasks.forEach { task -> task.await() }

                }
                response.data?.map { song ->
                    MediaMetadataCompat.Builder().from(song, urls.get(song.id)).build()
                }?.let {
                    catalog = it
                }
            }
            state = STATE_INITIALIZED
        } catch (e: Exception) {
            Log.e(TAG, "updateCatLog: ", e)
            state = STATE_ERROR
        }
    }

    override fun iterator(): Iterator<MediaMetadataCompat> = catalog.listIterator()
}

fun MediaMetadataCompat.Builder.from(
    song: Songs.Song,
    url: SongUrl.Url?,
): MediaMetadataCompat.Builder {
    val albumUri = song.al?.picUrl.toString()
    id = song.id.toString()
    title = song.name
    artist = song.artists
    album = song.al?.name
    mediaUri = url?.url
    albumArtUri = albumUri
    flag = MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
    // To make things easier for *displaying* these, set the display properties as well.
    displayTitle = song.name
    displaySubtitle = song.artists
    displayDescription = song.al?.name
    displayIconUri = albumUri

    // Add downloadStatus to force the creation of an "extras" bundle in the resulting
    // MediaMetadataCompat object. This is needed to send accurate metadata to the
    // media session during updates.
    downloadStatus = MediaDescriptionCompat.STATUS_NOT_DOWNLOADED
    // Allow it to be used in the typical builder style.
    return this
}