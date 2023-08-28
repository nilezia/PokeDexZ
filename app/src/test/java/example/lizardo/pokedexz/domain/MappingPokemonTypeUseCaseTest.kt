package example.lizardo.pokedexz.domain

import example.lizardo.pokedexz.data.model.PokemonInfoResponse
import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MappingPokemonTypeUseCaseTest {

    private lateinit var mappingPokemonTypeUseCase: MappingPokemonTypeUseCase
    private var pokemonRepository: PokemonRepository = mockk()

    @BeforeEach
    fun setup() {
        mappingPokemonTypeUseCase = MappingPokemonTypeUseCaseImpl(
            pokemonRepository = pokemonRepository
        )
    }
    @Test
    fun getPokemonInfo() = runTest {
        val pokemonId = "1"
        val mockData = PokemonInfoResponse(
            height = "18",
            weight = "20",
            types = listOf(
                PokemonInfoResponse.TypeInfoResponse(
                    slot = null,
                    type = PokemonInfoResponse.Type(name = "water")
                ),
                PokemonInfoResponse.TypeInfoResponse(
                    slot = null,
                    type = PokemonInfoResponse.Type(name = "rock")
                )
            )
        )
        coEvery { pokemonRepository.getPokemonInfo(pokemonId) } returns flow {
            emit(mockData)
        }

        mappingPokemonTypeUseCase.execute(pokemonId).collect {
            assertEquals("water,rock", it)
        }
    }
}