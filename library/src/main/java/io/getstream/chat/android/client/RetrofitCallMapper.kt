package io.getstream.chat.android.client

import io.getstream.chat.android.client.call.ChatCall
import io.getstream.chat.android.client.call.ChatCallImpl
import io.getstream.chat.android.client.errors.ChatNetworkError
import io.getstream.chat.android.client.gson.JsonParser
import retrofit2.Response

class RetrofitCallMapper(private val jsonParser: JsonParser) {

    fun <T> map(call: retrofit2.Call<T>): ChatCall<T> {

        return object : ChatCallImpl<T>() {

            override fun execute(): Result<T> {
                return execute(call)
            }

            override fun enqueue(callback: (Result<T>) -> Unit) {
                enqueue(call, callback)
            }

            override fun cancel() {
                super.cancel()
                call.cancel()
            }
        }
    }

    private fun <T> execute(call: retrofit2.Call<T>): Result<T> {
        return getResult(call)
    }

    private fun <T> enqueue(call: retrofit2.Call<T>, callback: (Result<T>) -> Unit) {
        call.enqueue(object : retrofit2.Callback<T> {

            override fun onResponse(call: retrofit2.Call<T>, response: Response<T>) {
                callback(getResult(response))
            }

            override fun onFailure(call: retrofit2.Call<T>, t: Throwable) {
                callback(failedResult(t))
            }
        })
    }

    private fun <T> failedResult(t: Throwable): Result<T> {
        return Result(null, failedError(t))
    }

    private fun failedError(t: Throwable): ChatNetworkError {

        var statusCode = -1
        var streamCode = -1

        return ChatNetworkError(t.message.toString(), t, streamCode, statusCode)
    }

    private fun <T> getResult(retroCall: retrofit2.Call<T>): Result<T> {
        return try {
            getResult(retroCall.execute())
        } catch (t: Throwable) {
            failedResult(t)
        }
    }

    private fun <T> getResult(retrofitResponse: Response<T>): Result<T> {

        var data: T? = null
        var error: ChatNetworkError? = null

        if (retrofitResponse.isSuccessful) {
            try {
                data = retrofitResponse.body()
            } catch (t: Throwable) {
                error = failedError(t)
            }
        } else {
            error = jsonParser.toError(retrofitResponse.raw())
        }

        return Result(data, error)
    }

}