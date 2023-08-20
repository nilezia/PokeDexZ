package example.lizardo.pokedexz.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import example.lizardo.pokedexz.MainApplication
import example.lizardo.pokedexz.R
import example.lizardo.pokedexz.databinding.FragmentFirstBinding
import example.lizardo.pokedexz.di.ViewModelFactory
import example.lizardo.pokedexz.domain.model.Pokemon
import example.lizardo.pokedexz.extension.getAppComponent
import example.lizardo.pokedexz.presentation.adapter.PokemonListAdapter
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val binding: FragmentFirstBinding by lazy {
        FragmentFirstBinding.inflate(LayoutInflater.from(context))
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: FirstFragmentViewModel by lazy {
        viewModelFactory.create(FirstFragmentViewModel::class.java)
    }

    private var itemClick: (Pokemon) -> Unit = {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
    }

    private var pokemonListAdapter: PokemonListAdapter? = null
    private var lifecycleOwner: LifecycleOwner? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getAppComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLifeOwner()
        initViewModel()
        initView()
        viewModel.getPokemon()
    }

    private fun initView() {
        pokemonListAdapter = PokemonListAdapter(itemClick)
        binding.recyclerView.apply {
            adapter = pokemonListAdapter
            addItemDecoration(SpaceItemDecoration(4))
        }
    }

    private fun initViewModel() = with(viewModel) {
        lifecycleOwner?.let { _lifecycleOwner ->
            onUpdatePokemonList.observe(_lifecycleOwner) {
                pokemonListAdapter?.submitList(it)
            }
        }
    }

    fun initLifeOwner() {
        lifecycleOwner = (requireContext() as LifecycleOwner)

    }

}