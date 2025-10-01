/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.data.util

import android.util.Log
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Query
import com.hugopolog.domain.entities.config.DataError
import com.hugopolog.domain.entities.config.DataResult
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <D : Query.Data, R> safeGraphQlCall(
    crossinline apiCall: suspend () -> ApolloResponse<D>,
    crossinline mapper: (D) -> R
): DataResult<R, DataError> {
    return try {
        val response = apiCall()

        when {
            response.hasErrors() -> {
                DataResult.Error(DataError.Generic.ServerError)
            }

            response.data == null -> {
                DataResult.Error(DataError.Generic.NoData)
            }

            else -> {
                try {
                    DataResult.Success(mapper(response.data!!))
                } catch (_: Exception) {
                    DataResult.Error(DataError.Generic.MappingError)
                }
            }
        }
    } catch (e: CancellationException) {
        DataResult.Error(DataError.Generic.Canceled)
    } catch (e: IOException) {
        DataResult.Error(DataError.Generic.NetworkError)
    } catch (_: TimeoutException) {
        DataResult.Error(DataError.Generic.Timeout)
    } catch (_: Exception) {
        DataResult.Error(DataError.Generic.UnknownError)
    }
}


