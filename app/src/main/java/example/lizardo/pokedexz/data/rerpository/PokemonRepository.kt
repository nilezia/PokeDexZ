package example.lizardo.pokedexz.data.rerpository

import android.util.Log
import example.lizardo.pokedexz.data.model.PokemonResponse
import example.lizardo.pokedexz.data.api.PokemonApi
import example.lizardo.pokedexz.data.model.PokemonInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface PokemonRepository {
    fun getPokemonList(): Flow<List<PokemonResponse.Pokemon>>

    fun getPokemonInfo(pokemonId: String): Flow<PokemonInfoResponse>
}

class PokemonRepositoryImpl @Inject constructor(private var api: PokemonApi) : PokemonRepository {
    override fun getPokemonList(): Flow<List<PokemonResponse.Pokemon>> {
        return flow {
            val response = api.getPokemonList()
            if (response.isSuccessful) {
                response.body()?.results?.let {
                    emit(it)
                }
            } else {
                error(response.message())
            }
        }
    }

    override fun getPokemonInfo(pokemonId: String): Flow<PokemonInfoResponse> {

        return flow {
            try {
                val response = api.getPokemonInfo(pokemonId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(it)
                    }
                } else {
                    error(response.message())
                }
            }catch (e :Exception){
                Log.d("excep",e.message.toString())
            }

        }
    }
}