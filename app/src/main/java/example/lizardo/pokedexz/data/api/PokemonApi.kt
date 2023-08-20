package example.lizardo.pokedexz.data.api

import example.lizardo.pokedexz.data.model.PokemonInfoResponse
import example.lizardo.pokedexz.data.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<PokemonResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemonInfo(
        @Path("id") id:String
    ): Response<PokemonInfoResponse>
}