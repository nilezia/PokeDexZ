package example.lizardo.pokedexz.injection

import dagger.Component
import example.lizardo.pokedexz.MainActivity
import example.lizardo.pokedexz.di.ApplicationBindsModule
import example.lizardo.pokedexz.di.ApplicationViewModelModule
import example.lizardo.pokedexz.di.NetworkModule
import example.lizardo.pokedexz.di.ViewModelFactory
import example.lizardo.pokedexz.presentation.FirstFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationViewModelModule::class,
        ApplicationBindsModule::class, NetworkModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(firstFragment: FirstFragment)
}