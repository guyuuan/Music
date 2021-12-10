package cn.chitanda.music.ui.scene

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.chitanda.music.ui.monet.Monet
import cn.chitanda.music.ui.monet.theme.MonetColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Chen
 * @createTime: 2021/12/10 5:08 下午
 * @description:
 **/
@HiltViewModel
class ThemeViewModel @Inject constructor() : ViewModel() {
    private val _color = mutableStateOf<MonetColor?>(null)
    val monetColor: State<MonetColor?> get() = _color

    fun getMonetColor(seed: Color) {
        viewModelScope.launch(Dispatchers.Default) {
            val new = Monet.getMonetColor(seed.toArgb())
            _color.value = new
        }
    }
}