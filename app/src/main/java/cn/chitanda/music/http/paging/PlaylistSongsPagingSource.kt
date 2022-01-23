package cn.chitanda.music.http.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.chitanda.music.http.bean.Songs

/**
 *@author: Chen
 *@createTime: 2022/1/23 21:59
 *@description:
 **/
private const val TAG = "PlaylistSongsPaging"

class PlaylistSongsPagingSource(
    private val maxSize: Int,
    private val load: suspend (Int, Int) -> Songs
) : PagingSource<Int, Songs.Song>() {
    companion object {
        const val PageSize = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Songs.Song>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Songs.Song> {
        val key = params.key ?: 0
        Log.d(TAG, "load: $key,maxsize = $maxSize max offset = ${maxSize/ PageSize} ${Thread.currentThread().name}")
        try {
            val response = load(key, PageSize)
            return if (response.code == 200) {
                val songs = if (key == maxSize/ PageSize) response.data?.subList(0,maxSize% PageSize) else response.data
                LoadResult.Page(
                    data = songs ?: emptyList(),
                    prevKey = if (key == 0) null else key - 1,
                    nextKey = (if (songs.isNullOrEmpty() || key >= maxSize / PageSize ) null else key + 1).also {
                        Log.d(TAG, "load: next key = $it songs = ${songs?.size}")
                    }
                )
            } else {
                LoadResult.Error(Exception("load playlist songs failed: ${response.message ?: response.msg}"))
            }

        } catch (e: Exception) {
            Log.e(TAG, "load: ", e)
            return LoadResult.Error(e)
        }
    }
}