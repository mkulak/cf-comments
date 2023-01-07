@file:JsModule("hono")
@file:JsNonModule
package hono

import Env
import ExecutionContext
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import kotlin.js.Promise

external class Hono {
    fun get(path: String, f: (dynamic) -> Promise<Any>)
    fun post(path: String, f: (dynamic) -> Promise<Any>)
    fun dispatch(request: Request, ctx: ExecutionContext?, env: Env): Promise<Response>
}