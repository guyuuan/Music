package cn.chitanda.music.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @author: Chen
 * @createTime: 2022/3/11 11:42
 * @description:
 **/


fun <T> ViewModel.launchFlow(block: FlowScope<T>.() -> Unit) {
    val scope = FlowScope<T>()
    flow {
        scope.apply(block).check()
        emit(scope.onEmit())
    }.onStart {
        viewModelScope.launch{
            scope.onStart()
        }
    }
        .onEach { scope.onEach(it) }
        .catch { scope.onError(it) }
        .launchIn(viewModelScope)
}

class FlowScope<T> {
    lateinit var onEmit: suspend () -> T
    lateinit var onEach: suspend (T) -> Unit
    var onStart: suspend () -> Unit = {}
    var onError: suspend (Throwable) -> Unit = {}


    fun check() {
        require(this::onEmit.isInitialized) { "onEmit must be isInitialized" }
        require(this::onEach.isInitialized) { "onEach must be isInitialized" }
    }
}