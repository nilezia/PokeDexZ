package example.lizardo.pokedexz.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.lizardo.pokedexz.domain.GetPokemonUseCase
import example.lizardo.pokedexz.domain.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    ViewModel() {

    val onUpdatePokemonList = MutableLiveData<List<Pokemon>>()
    val onShowError = MutableLiveData<Unit>()
    fun getPokemon(isLoadMore: Boolean = false) {
        viewModelScope.launch {
            getPokemonUseCase.execute(isLoadMore)
                .flowOn(Dispatchers.IO)
                .catch {
                    onShowError.value = Unit
                }
                .collect {
                    onUpdatePokemonList.value = it
                }
        }
    }
    fun getPokemonLoadMore(isLoadMore: Boolean = true,next:String ){
        viewModelScope.launch {
            getPokemonUseCase.execute(isLoadMore, next)
                .flowOn(Dispatchers.IO)
                .catch {
                    onShowError.value = Unit
                }
                .collect {
                    onUpdatePokemonList.value = it
                }
        }
    }
}
