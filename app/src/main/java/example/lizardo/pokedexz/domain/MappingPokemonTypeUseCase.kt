package example.lizardo.pokedexz.domain

import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MappingPokemonTypeUseCase {
    fun execute(pokemonId: String): Flow<String>
}

class MappingPokemonTypeUseCaseImpl @Inject constructor(private var pokemonRepository: PokemonRepository) :
    MappingPokemonTypeUseCase {
    override  fun execute(pokemonId: String): Flow<String> {
        return pokemonRepository.getPokemonInfo(pokemonId)
            .map { response ->
                var types = ""
                response.types?.forEach {
                    types += if (types.isEmpty()) {
                        "${it.type?.name.orEmpty()},"
                    } else {
                        it.type?.name.orEmpty()
                    }
                }
                types
            }

    }

}