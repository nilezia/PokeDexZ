package example.lizardo.pokedexz.data.rerpository

import example.lizardo.pokedexz.data.api.PokemonApi
import example.lizardo.pokedexz.data.model.PokemonInfoResponse
import example.lizardo.pokedexz.data.model.PokemonResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PokemonRepository {
    fun getPokemonList(offset: String = "0", limit: String = "20"): Flow<PokemonResponse>

    fun getPokemonInfo(pokemonId: String): Flow<PokemonInfoResponse>
}

class PokemonRepositoryImpl @Inject constructor(private var api: PokemonApi) : PokemonRepository {
    override fun getPokemonList(offset: String, limit: String): Flow<PokemonResponse> {
        return flow {
            val response = api.getPokemonList(
                offset = offset,
                limit = limit
            )
            if (response.isSuccessful) {
                val pokemonResponse = response.body()

                pokemonResponse?.let { _pokemonResponse ->
                    if (_pokemonResponse.results?.isNotEmpty() == true) {
                        emit(_pokemonResponse)
                    } else {
                        error("Data is Empty")
                    }
                }
            } else {
                error(response.message())
            }
        }
    }

    override fun getPokemonInfo(pokemonId: String): Flow<PokemonInfoResponse> {

        return flow {
            val response = api.getPokemonInfo(pokemonId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                error(response.message())
            }
        }
    }
}