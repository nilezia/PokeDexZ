package example.lizardo.pokedexz

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import example.lizardo.pokedexz.databinding.ActivityMainBinding
import example.lizardo.pokedexz.di.ViewModelFactory
import example.lizardo.pokedexz.domain.GetPokemonUseCase
import example.lizardo.pokedexz.domain.GetPokemonUseCaseImpl
import example.lizardo.pokedexz.extension.getAppComponent
import example.lizardo.pokedexz.presentation.FirstFragmentViewModel
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        //val navController = findNavController(R.id.nav_host_fragment_content_main)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}