package example.lizardo.pokedexz.domain

import android.net.Uri
import android.util.Log
import example.lizardo.pokedexz.data.model.PokemonResponse
import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import example.lizardo.pokedexz.domain.model.Pokemon
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface GetPokemonUseCase {
    fun execute(isLoadMore: Boolean, next: String = ""): Flow<List<Pokemon>>
}

class GetPokemonUseCaseImpl @Inject constructor(
    private var mappingPokemonTypeUseCase: MappingPokemonTypeUseCase,
    private var pokemonRepository: PokemonRepository
) : GetPokemonUseCase {
    @OptIn(FlowPreview::class)
    override fun execute(isLoadMore: Boolean, next: String): Flow<List<Pokemon>> {

        return if (isLoadMore && next.isNotEmpty()) {
            val uri =
                Uri.parse(next)
            val limit = uri.getQueryParameter("limit")
            val offset = uri.getQueryParameter("offset")
            if (limit == null || offset == null) error("Data Empty")
            pokemonRepository.getPokemonList(
                offset = offset, limit = limit
            ).flatMapConcat { pokemonResponse ->
                getTypePokemon(pokemonResponse)
            }
        } else {
            pokemonRepository.getPokemonList().flatMapConcat { pokemonResponse ->
                getTypePokemon(pokemonResponse)
            }
        }
    }

    private suspend fun getTypePokemon(pokemonResponse: PokemonResponse): Flow<List<Pokemon>> {
        val doMainData = pokemonResponse.results?.let {
            mapDataToDomain(pokemons = it, pokemonResponse = pokemonResponse)
        } ?: error("Data is Empty")
        doMainData.forEach { pokemon ->
            pokemon.type = mappingPokemonTypeUseCase.execute(pokemon.pokemonId).first()
        }
        return flowOf(doMainData.filterNot {
            it.pokemonId == "-1"
        })
    }

    private fun mapDataToDomain(
        pokemonResponse: PokemonResponse,
        pokemons: List<PokemonResponse.Pokemon>
    ): List<Pokemon> {
        return pokemons.map { pokemon ->
            Pokemon().apply {
                val index = try {
                    pokemon.url?.split("/".toRegex())?.dropLast(1)?.last() ?: "-1"
                } catch (e: Exception) {
                    "-1"
                }
                this.next = pokemonResponse.next.orEmpty()
                this.pokemonId = index
                this.name = pokemon.name.orEmpty()
                this.image =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
            }
        }
    }
}