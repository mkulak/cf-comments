@file:JsModule("hono")
@file:JsNonModule
package hono

import Env
import ExecutionContext
import org.w3c.fetch.Headers
import org.w3c.workers.FetchEvent
import kotlin.js.Promise
import org.w3c.fetch.Request as FetchRequest
import org.w3c.fetch.Response as FetchResponse

external class Hono {
    fun get(path: String, f: (Context) -> Promise<Response>)
    fun post(path: String, f: (Context) -> Promise<Response>)
    fun dispatch(request: FetchRequest, ctx: ExecutionContext?, env: Env): Promise<FetchResponse>
}

external interface Request {
    fun param(name: String): String?
    fun <T> json(): Promise<T>
}

external interface Response 

external interface Context {
    val req: Request
    val env: Env
    val event: FetchEvent
    val executionCtx: ExecutionContext
    var res: Response
    fun header(name: String, value: String)
    fun status(status: Int)
    fun text(text: String, status: Int = definedExternally, headers: Headers = definedExternally): Response
    fun json(any: Any, status: Int = definedExternally, headers: Headers = definedExternally): Response
    fun html(html: String, status: Int = definedExternally, headers: Headers = definedExternally): Response
    fun redirect(location: String, status: Int = definedExternally): Response
    fun notFound(): Response
    fun cookie(name: String, value: String, opt: dynamic = definedExternally)
    val runtime: String
}

