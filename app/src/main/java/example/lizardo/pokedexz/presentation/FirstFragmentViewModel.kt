package example.lizardo.pokedexz.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import example.lizardo.pokedexz.domain.GetPokemonUseCase
import example.lizardo.pokedexz.domain.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstFragmentViewModel @Inject constructor(private val getPokemonUseCase: GetPokemonUseCase) :
    ViewModel() {

    val onUpdatePokemonList = MutableLiveData<List<Pokemon>>()
    fun getPokemon() {
        viewModelScope.launch {
            getPokemonUseCase.execute().flowOn(Dispatchers.IO)
                .collect {
                    //Log.d("aa", "$it")
                    onUpdatePokemonList.value = it
                }
        }
    }
}
