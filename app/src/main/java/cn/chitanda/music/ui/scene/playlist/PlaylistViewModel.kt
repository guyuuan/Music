package cn.chitanda.music.ui.scene.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.repository.SongsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2021/12/31 16:40
 * @description:
 **/
@HiltViewModel
class PlaylistViewModel @Inject constructor(private val songsRepository: SongsRepository) :
    ViewModel() {
    fun getPlaylistDetail(id: String,callback:(String)->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = songsRepository.getPlaylistDetail(id)
            callback(response.toString())
        }
    }
}