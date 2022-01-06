package cn.chitanda.music.ui.scene.user_info

import androidx.lifecycle.ViewModel
import cn.chitanda.music.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2022/1/6 14:40
 * @description:
 **/
@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

}