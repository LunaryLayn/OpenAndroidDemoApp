package com.hugopolog.data.api

import retrofit2.Response
import retrofit2.http.POST


interface ApiService {
    @POST("/example")
    suspend fun doSomething(): Response<Unit>
}