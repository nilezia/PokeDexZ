package example.lizardo.pokedexz.domain

import example.lizardo.pokedexz.data.model.PokemonResponse
import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPokemonUseCaseTest {

    private lateinit var getPokemonUseCase: GetPokemonUseCase
    private var mappingPokemonTypeUseCase: MappingPokemonTypeUseCase = mockk()
    private var pokemonRepository: PokemonRepository = mockk()

    @BeforeEach
    fun setUp() {

        getPokemonUseCase = GetPokemonUseCaseImpl(
            mappingPokemonTypeUseCase = mappingPokemonTypeUseCase,
            pokemonRepository = pokemonRepository
        )
    }


    @Test
    fun test_when_getPokemonList_should_return_data() = runTest {
        val responseData = PokemonResponse(
            next = "next",
            results = listOf(
                PokemonResponse.Pokemon(
                    name = "babasur",
                    url = "com.test/1"
                ),
                PokemonResponse.Pokemon(
                    name = "babasur1",
                    url = "com.test/2"
                )
            )
        )
        coEvery { mappingPokemonTypeUseCase.execute(any()) } returns flow {
            emit("grass")
        }
        coEvery { pokemonRepository.getPokemonList() } returns flow {
            emit(responseData)
        }

        getPokemonUseCase.execute(false, "").collect {
            assert(it.isNotEmpty())
            assert(it.size == 2)
            assert(it.first().type == "grass")
        }
    }

    @Test
    fun test_when_urlData_is_not_collect_should_filter_out_fromList() = runTest {
        val responseData =  PokemonResponse(
        next = "next",
        results = listOf(
            PokemonResponse.Pokemon(
                name = "babasur",
                url = "1"
            ),
            PokemonResponse.Pokemon(
                name = "babasur1",
                url = "com.test/2"
            )
        ))
        coEvery { mappingPokemonTypeUseCase.execute(any()) } returns flow {
            emit("grass")
        }
        coEvery { pokemonRepository.getPokemonList() } returns flow {
            emit(responseData)
        }

        getPokemonUseCase.execute(false, "").collect {
            assert(it.isNotEmpty())
            assert(it.size == 1)
            assert(it.first().type == "grass")
        }
    }
}