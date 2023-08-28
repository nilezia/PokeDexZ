package example.lizardo.pokedexz.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<Pokemon>? = null
) {
    data class Pokemon(
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("url")
        var url: String? = null
    )
}
