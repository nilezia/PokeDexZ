package example.lizardo.pokedexz.data.rerpository

import example.lizardo.pokedexz.data.api.PokemonApi
import example.lizardo.pokedexz.data.model.PokemonResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryTest {

    private lateinit var pokemonRepository: PokemonRepository
    private val api: PokemonApi = mockk()

    @BeforeEach
    fun setUp() {
        pokemonRepository = PokemonRepositoryImpl(api)
    }

    @Test
    fun test_when_getPokemonList_success_should_return_data() = runTest {
        val mockPokemonListData = PokemonResponse(
            count = null,
            next = null,
            previous = null,
            results = listOf(
                PokemonResponse.Pokemon(
                    name = "babasur",
                    url = "com/1"
                ),
                PokemonResponse.Pokemon(
                    name = "chalizard",
                    url = "com/2"
                ),
                PokemonResponse.Pokemon(
                    name = "pikachu",
                    url = "com/3"
                )
            )
        )
        coEvery { api.getPokemonList(any(), any()) } returns Response.success(mockPokemonListData)
        pokemonRepository.getPokemonList().collect {
            assertNotNull(it)
            assertEquals(it.results?.get(0)?.name, "babasur")
            assertEquals(it.results?.get(0)?.url, "com/1")
            assertEquals(it.results?.get(1)?.name, "chalizard")
            assertEquals(it.results?.get(1)?.url, "com/2")
            assertEquals(it.results?.get(2)?.name, "pikachu")
            assertEquals(it.results?.get(2)?.url, "com/3")
        }

    }

    @Test
    fun test_when_getPokemonList_isEmpty_should_return_error() = runTest {

        coEvery { api.getPokemonList(any(),any()) } returns Response.success(PokemonResponse())
        pokemonRepository.getPokemonList().catch {
            assertEquals("Data is Empty", it.message)
        }

    }

    @Test
    fun test_when_getPokemonList_unSuccess_should_return_error() = runTest {

        val responseBody = "BOOM!".toResponseBody("application/json".toMediaTypeOrNull())
        coEvery { api.getPokemonList(any(), any()) } returns Response.error(400, responseBody)
        pokemonRepository.getPokemonList().catch {
            assertEquals("BOOM!", it.message)
        }

    }
}