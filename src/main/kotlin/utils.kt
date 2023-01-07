import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.js.Promise

fun <T> promise(f: suspend () -> T): Promise<T> = Promise<T> { resolve, reject ->
    GlobalScope.launch {
        runCatching { resolve(f()) }.onFailure(reject)
    }
}