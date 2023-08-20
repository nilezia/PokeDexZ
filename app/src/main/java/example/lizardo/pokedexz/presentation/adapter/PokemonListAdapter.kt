package example.lizardo.pokedexz.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import example.lizardo.pokedexz.databinding.ItemPokemonBinding
import example.lizardo.pokedexz.domain.model.Pokemon
import example.lizardo.pokedexz.presentation.viewholder.PokemonViewHolder

class PokemonListAdapter(private var itemClick: (Pokemon) -> Unit) :
    ListAdapter<Pokemon, PokemonViewHolder>(PokemonAdapterDiffUtil()) {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), itemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }
}

class PokemonAdapterDiffUtil : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.pokemonId == newItem.pokemonId
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.image == newItem.image
    }
}

