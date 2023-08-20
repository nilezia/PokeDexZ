package example.lizardo.pokedexz.extension

import android.app.Activity
import androidx.fragment.app.Fragment
import example.lizardo.pokedexz.MainApplication
import example.lizardo.pokedexz.injection.ApplicationComponent


fun Fragment.getAppComponent(): ApplicationComponent =
    (requireActivity().applicationContext as MainApplication).appComponent

fun Activity.getAppComponent(): ApplicationComponent = (applicationContext as MainApplication).appComponent
