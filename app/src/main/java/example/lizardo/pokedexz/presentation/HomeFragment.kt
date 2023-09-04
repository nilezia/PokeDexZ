package example.lizardo.pokedexz.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import example.lizardo.pokedexz.databinding.FragmentFirstBinding
import example.lizardo.pokedexz.domain.model.Pokemon
import example.lizardo.pokedexz.presentation.adapter.PokemonListAdapter


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding: FragmentFirstBinding by lazy {
        FragmentFirstBinding.inflate(LayoutInflater.from(context))
    }

    private val viewModel: HomeFragmentViewModel by viewModels()

    private var itemClick: (Pokemon) -> Unit = {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_LONG).show()
    }

    private var pokemonListAdapter: PokemonListAdapter? = null
    private var isScrolling = false
    private var isLastPage = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        viewModel.getPokemon()
    }

    private fun initView() {
        pokemonListAdapter = PokemonListAdapter(itemClick)
        val gridLayoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.apply {
            adapter = pokemonListAdapter
            addItemDecoration(SpaceItemDecoration(4))
            layoutManager = gridLayoutManager
            addOnScrollListener(PaginationListener(gridLayoutManager))

        }
    }

    private fun initViewModel() = with(viewModel) {
        onUpdatePokemonList.observe(viewLifecycleOwner) {
            pokemonListAdapter?.submitList(it.toMutableList())
            this@HomeFragment.isScrolling = false
        }
    }

    inner class PaginationListener(private val layoutManager: RecyclerView.LayoutManager) :
        PaginationScrollListener(layoutManager) {

        override fun loadMoreItems() {
            isScrolling = true
            viewModel.getPokemonLoadMore(isLoadMore = true, pokemonListAdapter?.currentList?.last()?.next!!)
        }

        override fun isLastPage(): Boolean = isLastPage //getPresenter().isLastPage
        override fun isLoading(): Boolean = isScrolling //getPresenter().isLoading
        override fun onScrollChange(p0: View?, p1: Int, p2: Int, p3: Int, p4: Int) {
            isScrolling = true
        }
    }
}
