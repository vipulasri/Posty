package com.vipulasri.posty.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloNetworkException
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.resume

/**
 * Created by Vipul Asri on 09/12/21.
 */

suspend fun <T> ApolloCall<T>.awaitSafeResult(): SafeResult<T> =
    suspendCancellableCoroutine { cont ->

        cont.invokeOnCancellation {
            cancel()
        }

        enqueue(object : ApolloCall.Callback<T>() {

            private val wasCalled = AtomicBoolean(false)

            override fun onResponse(response: Response<T>) {
                if (!wasCalled.getAndSet(true)) {
                    val result = response.data?.let { data ->
                        SafeResult.Success(data)
                    } ?: kotlin.run {
                        if (response.hasErrors()) {
                            SafeResult.Failure(Exception(response.errors?.toString()))
                        } else {
                            SafeResult.Failure(Exception("Unknown error occurred"))
                        }
                    }

                    cont.resume(result)
                }
            }

            override fun onFailure(exception: ApolloException) {
                if (!wasCalled.getAndSet(true)) {
                    Timber.e("awaitSafeResult", exception.message.toString())
                    val error = when (exception) {
                        is ApolloNetworkException -> SafeResult.Failure(Exception("No Internet Connection!"))
                        else -> SafeResult.Failure(exception)
                    }
                    cont.resume(error)
                }
            }
        })
    }