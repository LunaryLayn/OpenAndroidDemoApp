/*
 * © 2025 Hugo Polo - Github: https://github.com/LunaryLayn
 */

package com.hugopolog.data.api

import com.hugopolog.data.entities.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("cards")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonResponse>
}