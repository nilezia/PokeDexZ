package example.lizardo.pokedexz.domain.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    var pokemonId: String = "",
    var name: String = "",
    var image: String = "",
    var type: String = ""
)
