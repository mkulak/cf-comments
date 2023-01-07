import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import kotlin.js.Promise
import hono.Hono

val app = Hono().apply {
    get("/api/posts/:slug/comments") { c ->
        promise {
            val slug = c.req.param().slug
            val promise: Promise<dynamic> = c.env.DB.prepare("select * from comments where post_slug = ?").bind(slug).all()
            val res = promise.await()
            c.json(res.results)
        }
    }
    post("/api/posts/:slug/comments") { c ->
        promise {
            val slug = c.req.param().slug
            val jsonPromise: Promise<dynamic> = c.req.json()
            val json = jsonPromise.await()
            val author = json.author
            val body = json.body
            if (author == null) {
                c.status(400)
                return@promise c.text("author is missing")
            }
            if (body == null) {
                c.status(400)
                return@promise c.text("body is missing")
            }
            val resPromise: Promise<Any?> =
                c.env.DB.prepare("insert into comments (author, body, post_slug) values (?, ?, ?)")
                    .bind(author, body, slug).run()

            val success = resPromise.await() != null
            if (success) {
                c.status(201)
                return@promise c.text("Created")
            } else {
                c.status(500)
                return@promise c.text("Something went wrong")
            }
//            c.text("slug: $slug, author: $author, body: $body")
        }
    }
}


@OptIn(ExperimentalJsExport::class)
@JsExport
fun fetch(request: Request, env: Env, ctx: ExecutionContext?): Promise<Response> {
    return app.dispatch(request, ctx, env)
//    return GlobalScope.promise {
//        val headers: dynamic = object {}
//        headers["content-type"] = "text/plain"
//        val promise: Promise<Any> = env.DB.prepare("select * from comments where post_slug = ?").bind("blah").all()
//        val res = promise.await()
//        Response(
//            "Kotlin Worker hello world ${JSON.stringify(res)}",
//            ResponseInit(headers = headers)
//        )
//    }
}

external interface Env {
    val API_TOKEN: String
    val DB: dynamic
}

external interface ExecutionContext