package io.getstream.chat.android.client.gson

import io.getstream.chat.android.client.Result
import io.getstream.chat.android.client.errors.ChatNetworkError
import okhttp3.Response
import retrofit2.Retrofit

interface JsonParser {
    fun toJson(any: Any): String
    fun <T> fromJson(raw: String, clazz: Class<T>): T
    fun <T> fromJsonOrError(raw: String, clazz: Class<T>): Result<T>
    fun toError(okHttpResponse: Response): ChatNetworkError
    fun configRetrofit(builder: Retrofit.Builder): Retrofit.Builder
}