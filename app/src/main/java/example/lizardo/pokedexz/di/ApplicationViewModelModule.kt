package example.lizardo.pokedexz.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import example.lizardo.pokedexz.di.scope.ViewModelKey
import example.lizardo.pokedexz.presentation.FirstFragmentViewModel

@Module
interface ApplicationViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(FirstFragmentViewModel::class)
    fun bindsFirstFragmentViewModel(firstFragmentViewModel: FirstFragmentViewModel): ViewModel

}