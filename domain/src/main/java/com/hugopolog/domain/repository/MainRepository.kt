package com.hugopolog.domain.repository

import com.hugopolog.domain.entities.config.error.DataError
import com.hugopolog.domain.entities.config.error.DataResult

interface MainRepository {
    suspend fun doSomething(): DataResult<Unit, DataError>
}