import org.khronos.webgl.ArrayBuffer
import kotlin.js.Promise


external interface D1Meta {
    val duration: Long
    val size_after: Long
    val rows_read: Long
    val rows_written: Long
    val last_row_id: Long
    val changed_db: Boolean
    val changes: Long
}
external interface D1Result<T> {
    val results: Array<T>
    val success: Boolean
    val meta: D1Meta
    val error: dynamic
}
external interface D1ExecResult {
    val count: Long
    val duration: Long
}
external class D1Database {
    fun prepare(query: String): D1PreparedStatement
    fun dump(): Promise<ArrayBuffer>
    fun <T> batch(statements: Array<D1PreparedStatement>): Promise<Array<D1Result<T>>>
    fun exec(query: String): Promise<D1ExecResult>
}
external class D1PreparedStatement {
    fun bind(vararg values: Any?): D1PreparedStatement
    fun <T> first(colName: String): Promise<T?>
    fun <T> first(): Promise<T?>
    fun run(): Promise<D1Result<Any?>>
    fun all(): Promise<D1Result<dynamic>>
    fun <T> raw(): Promise<Array<T>>
}