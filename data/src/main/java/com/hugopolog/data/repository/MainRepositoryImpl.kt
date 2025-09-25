package com.hugopolog.data.repository

import android.util.Log
import com.hugopolog.data.api.ApiService
import com.hugopolog.data.util.ApiHelper.safeApiCall
import com.hugopolog.domain.entities.config.error.DataError
import com.hugopolog.domain.entities.config.error.DataResult
import com.hugopolog.domain.repository.MainRepository

class MainRepositoryImpl(
    private val service: ApiService
) : MainRepository {
    override suspend fun doSomething(): DataResult<Unit, DataError> {
        return safeApiCall(
            apiCall = { service.doSomething() },
            mapper = { response ->
                Log.d("MainRepositoryImpl", "Response: $response")
            }
        )
    }
}