package cn.chitanda.music.ui.scene

import android.os.Build
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.preference.PreferenceManager
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
    preferenceManager: PreferenceManager
) : ViewModel() {
    val uid = preferenceManager.uid
    private val _user = MutableStateFlow<RequestStatus<UserProfile>>(RequestStatus())
    val user: StateFlow<RequestStatus<UserProfile>>
        get() = _user
    private val _isReady = mutableStateOf(false)
    val isReady: State<Boolean> get() = _isReady
    private var _loginSuccess = false
    val loginSuccess: Boolean
        get() = _loginSuccess

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            viewModelScope.launch(Dispatchers.IO) {
                if (uid.isNotEmpty()) userRepository.fetchUserInfo(_user)?.let {
                    _loginSuccess = it.code == 200
                }
                _isReady.value = true
            }
        } else {
            _isReady.value = true
        }
    }

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

    fun getUserPlayList(uid:String = this.uid){
        userRepository
    }
}