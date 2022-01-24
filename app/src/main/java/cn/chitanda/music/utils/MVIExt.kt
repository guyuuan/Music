package cn.chitanda.music.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KProperty1

/**
 * @author: Chen
 * @createTime: 2022/1/5 15:46
 * @description:
 **/

suspend inline fun <T> MutableStateFlow<T>.setStat(reducer: T.() -> T) {
    this.emit(this.value.reducer())
}

@SuppressLint("StateFlowValueCalledInComposition", "ProduceStateDoesNotAssignValue")
@Composable
fun <T, A> StateFlow<T>.collectPartAsState(
    context: CoroutineContext = EmptyCoroutineContext, part: KProperty1<T, A>,
): State<A> =
    produceState(part.get(value), this, context) {
        if (context == EmptyCoroutineContext) {
            map { StateTuple1(part.get(it)) }.distinctUntilChanged().collect {
                value = it.a
            }
        } else withContext(context) {
            map { StateTuple1(part.get(it)) }.distinctUntilChanged().collect {
                value = it.a
            }
        }
    }

internal data class StateTuple1<A>(val a: A)