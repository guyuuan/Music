package cn.chitanda.music.ui.scene

import android.os.Build
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.PlaylistJson
import cn.chitanda.music.http.bean.UserProfile
import cn.chitanda.music.preference.PreferenceManager
import cn.chitanda.music.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 *@author: Chen
 *@createTime: 2021/8/31 16:06
 *@description:
 **/
private const val TAG = "UserViewModel"

@HiltViewModel
class LocaleUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    val uid: String get() = preferenceManager.uid
    private val _user = MutableStateFlow<RequestStatus<UserProfile>>(RequestStatus())
    val user: StateFlow<RequestStatus<UserProfile>>
        get() = _user
    private val _isReady = mutableStateOf(Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
    val isReady: State<Boolean> get() = _isReady
    private var _loginSuccess = false
    val loginSuccess: Boolean
        get() = _loginSuccess

    private val _playlist = MutableStateFlow<RequestStatus<PlaylistJson>>(RequestStatus())
    val playlist: StateFlow<RequestStatus<PlaylistJson>> get() = _playlist

    init {
        if (!isReady.value) {
            viewModelScope.launch(Dispatchers.IO) {
                if (uid.isNotEmpty()) userRepository.fetchUserInfo(_user)?.let {
                    _loginSuccess = it.code == 200
                }
                withContext(Dispatchers.Main) {
                    _isReady.value = true
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginWithPassword(username, password)
        }
    }

    fun logout() {
        preferenceManager.uid = ""
        preferenceManager.cookies = ""
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

    fun getUserPlayList(uid: String = this.uid) {
        Log.d(TAG, "getUserPlayList: ${this.uid}")
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserPlayList(
                uid = uid, _playlist
            )
        }
    }
}