package cn.chitanda.music.ui.scene

/**
 * @author: Chen
 * @createTime: 2022/1/5 15:30
 * @description:
 **/
sealed class PageState {
    object Loading : PageState()
    object Success : PageState()
    object Empty : PageState()
    data class Error(val tr: Throwable) : PageState()
}

val PageState.isLoading: Boolean get() = this == PageState.Loading
