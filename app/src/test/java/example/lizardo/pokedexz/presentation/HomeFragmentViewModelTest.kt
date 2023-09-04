package example.lizardo.pokedexz.presentation

import example.lizardo.pokedexz.domain.GetPokemonUseCase
import example.lizardo.pokedexz.domain.model.Pokemon
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeFragmentViewModelTest {

    private lateinit var homeFragmentViewModel: HomeFragmentViewModel
    private var getPokemonUseCase: GetPokemonUseCase = mockk()
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        homeFragmentViewModel = HomeFragmentViewModel(getPokemonUseCase = getPokemonUseCase)
    }

    @Test
    fun test_when_execute_getPokemonUseCase_should_verify_1time() = runTest {
        coEvery { getPokemonUseCase.execute(any()) } returns flow {
            emit(
                listOf(
                    Pokemon(
                        pokemonId = "1",
                        name = "name1",
                        image = "img1",
                        type = "type1"
                    ),
                    Pokemon(
                        pokemonId = "2",
                        name = "name2",
                        image = "img2",
                        type = "type2"
                    ),
                    Pokemon(
                        pokemonId = "3",
                        name = "name3",
                        image = "img3",
                        type = "type3"
                    )
                )
            )
        }
        homeFragmentViewModel.getPokemon()
        coVerify(exactly = 1) { getPokemonUseCase.execute(any()) }
    }
}