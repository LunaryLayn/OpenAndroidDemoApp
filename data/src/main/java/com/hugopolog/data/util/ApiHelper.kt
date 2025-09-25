package com.hugopolog.data.util

import android.util.Log
import com.google.gson.JsonParseException
import com.hugopolog.domain.entities.config.error.DataError
import com.hugopolog.domain.entities.config.error.DataResult
import java.io.IOException
import java.util.concurrent.TimeoutException
import kotlin.coroutines.cancellation.CancellationException

object ApiHelper {
    suspend inline fun <T, R> safeApiCall(
        crossinline apiCall: suspend () -> retrofit2.Response<T>,
        crossinline mapper: (T) -> R,
        crossinline errorMapper: (code: Int) -> DataError = { DataError.Generic.UnknownError }
    ): DataResult<R, DataError> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    try {
                        DataResult.Success(mapper(body))
                    } catch (_: Exception) {
                        DataResult.Error(DataError.Generic.UnknownError)
                    }
                } else {
                    DataResult.Error(DataError.Generic.NoData)
                }
            } else {
                // 👇 primero intento con errorMapper, si no lo reconoce, uso genéricos
                val custom = errorMapper(response.code()) // Mapeo específico del endpoint
                val error = when (custom) {
                    DataError.Generic.UnknownError -> when (response.code()) {
                        400 -> DataError.Generic.BadRequest
                        401 -> DataError.Generic.Unauthorized
                        403 -> DataError.Generic.Forbidden
                        404 -> DataError.Generic.NotFound
                        409 -> DataError.Generic.Conflict
                        500 -> DataError.Generic.ServerError
                        else -> DataError.Generic.UnknownError
                    }

                    else -> custom
                }
                DataResult.Error(error)
            }
        }
        catch (e: CancellationException) {
            Log.d("DataUtil", "safeApiCall: coroutine cancelada -> ${e.message}")
            return DataResult.Error(DataError.Generic.Canceled)
        }
        catch (e: IOException) {
            DataResult.Error(DataError.Generic.NetworkError)
        } catch (_: TimeoutException) {
            DataResult.Error(DataError.Generic.Timeout)
        } catch (e: JsonParseException) { // Gson / Moshi parsing error
            DataResult.Error(DataError.Generic.DeserializationError)
        } catch (e: Exception) {
            DataResult.Error(DataError.Generic.UnknownError)
        }
    }
}