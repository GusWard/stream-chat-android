package io.getstream.chat.android.client.call

import io.getstream.chat.android.client.utils.Result

interface ChatCall<T> {
    fun execute(): Result<T>
    fun enqueue(callback: (Result<T>) -> Unit)
    fun cancel()
    fun <K> map(mapper: (T) -> K): ChatCall<K>
}