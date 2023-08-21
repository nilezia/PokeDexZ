package example.lizardo.pokedexz.domain

import example.lizardo.pokedexz.data.model.PokemonResponse
import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import example.lizardo.pokedexz.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetPokemonUseCase {
    fun execute(): Flow<List<Pokemon>>
}
class GetPokemonUseCaseImpl @Inject constructor(
    private var mappingPokemonTypeUseCase: MappingPokemonTypeUseCase,
    private var pokemonRepository: PokemonRepository
) : GetPokemonUseCase {
    override fun execute(): Flow<List<Pokemon>> {
        return pokemonRepository.getPokemonList().flatMapConcat { pokemonResponse ->

            val doMainData = mapDataToDomain(pokemons = pokemonResponse)
            doMainData.forEach { pokemon ->
                pokemon.type = mappingPokemonTypeUseCase.execute(pokemon.pokemonId).first()
            }
            flowOf(doMainData)
        }
    }

    private suspend fun mapDataToDomain(pokemons: List<PokemonResponse.Pokemon>): List<Pokemon> {
        return pokemons.map { pokemon ->
            Pokemon().apply {
                val index = pokemon.url?.split("/".toRegex())?.dropLast(1)?.last()
                this.pokemonId = index ?: "-1"
                this.name = pokemon.name.orEmpty()
                this.image =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
                // this.type = mappingPokemonTypeUseCase.execute(index.toString())
            }
        }
    }
}