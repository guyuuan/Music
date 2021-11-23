package cn.chitanda.music.ui.scene

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.UserAccount
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:06
 *@description:
 **/
private const val TAG = "UserViewModel"

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _user = MutableStateFlow<RequestStatus<UserProfile>>(RequestStatus())
    val user: StateFlow<RequestStatus<UserProfile>>
        get() = _user

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginWithPassword(username, password)
        }
    }

    fun fetchUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.fetchUserInfo(_user)
        }
    }

    fun refreshLoginStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.refreshLoginStatus()
        }
    }
}