package cn.chitanda.music.ui.scene.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.http.RequestStatus
import cn.chitanda.music.http.bean.LoginJson
import cn.chitanda.music.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _login = MutableStateFlow<RequestStatus<LoginJson>>(RequestStatus())
    val login: StateFlow<RequestStatus<LoginJson>>
        get() = _login

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.loginWithPassword(username, password, _login)
        }
    }

}