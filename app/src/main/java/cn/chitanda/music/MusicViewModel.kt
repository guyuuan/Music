package cn.chitanda.music

import androidx.lifecycle.ViewModel
import cn.chitanda.music.media.connect.MusicServiceConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2022/1/24 17:01
 * @description:
 **/
@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    fun  init(){}

}