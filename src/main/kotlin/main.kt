import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import kotlin.js.Promise

@OptIn(ExperimentalJsExport::class)
@JsExport
fun fetch(request: Request, env: Env): Promise<Response> {
    return GlobalScope.promise {
        val headers: dynamic = object {}
        headers["content-type"] = "text/plain"
        val promise: Promise<Any> = env.DB.prepare("select * from comments where post_slug = ?").bind("blah").all()
        val res = promise.await()
        Response(
            "Kotlin Worker hello world ${JSON.stringify(res)}",
            ResponseInit(headers = headers)
        )
    }
}

external interface Env {
    val API_TOKEN: String
    val DB: dynamic
}