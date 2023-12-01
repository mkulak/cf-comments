import hono.Hono
import kotlinx.coroutines.await
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import kotlin.js.Promise

val app = Hono().apply {
    get("/") { c ->
        promise {
            c.html("<h2>Comments API</h2>")
        }
    }
    get("/api/posts/:slug/comments") { c ->
        promise {
            val slug = c.req.param("slug")
            val promise: Promise<dynamic> = c.env.DB.prepare("select * from comments where post_slug = ?").bind(slug).all()
            val res = promise.await()
            c.json(res.results)
        }
    }
    post("/api/posts/:slug/comments") { c ->
        promise {
            val slug = c.req.param("slug")
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
        }
    }
}


@OptIn(ExperimentalJsExport::class)
@JsExport
fun fetch(request: Request, env: Env, ctx: ExecutionContext?): Promise<Response> {
    return app.dispatch(request, ctx, env)
}

external interface Env {
    val API_TOKEN: String
    val DB: dynamic
}

external interface ExecutionContext