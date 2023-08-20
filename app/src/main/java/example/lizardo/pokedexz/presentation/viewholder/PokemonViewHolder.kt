package example.lizardo.pokedexz.presentation.viewholder

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import example.lizardo.pokedexz.R
import example.lizardo.pokedexz.databinding.ItemPokemonBinding
import example.lizardo.pokedexz.domain.model.Pokemon
import example.lizardo.pokedexz.util.TypeColor

class PokemonViewHolder(private var binding: ItemPokemonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(pokemon: Pokemon, itemClick: (Pokemon) -> Unit) = with(binding) {
        pokemonIdTextview.text = String.format("#%03d", pokemon.pokemonId.toInt())
        pokemonNameTextview.text = pokemon.name
        Glide
            .with(root)
            .load(pokemon.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(pokemonImageView)
        val mainType = pokemon.type.split(",")
        val bgColor = TypeColor.getTypeColor(mainType.first())
        elementTextView.text = mainType.first()
        bgCardView.setCardBackgroundColor(
            ResourcesCompat.getColor(
                root.resources,
                bgColor,
                null
            )
        )

        root.setOnClickListener {
            itemClick.invoke(pokemon)
        }
    }
}