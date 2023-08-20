package example.lizardo.pokedexz

import android.app.Application
import example.lizardo.pokedexz.injection.ApplicationComponent
import example.lizardo.pokedexz.injection.DaggerApplicationComponent

class MainApplication : Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}