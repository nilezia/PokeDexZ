package example.lizardo.pokedexz.data.model

import com.google.gson.annotations.SerializedName

data class PokemonInfoResponse(
    @SerializedName("height")
    var height: String? = null,
    @SerializedName("weight")
    var weight: String? = null,
    @SerializedName("types")
    var types: List<TypeInfoResponse>? = null
) {

    data class TypeInfoResponse(
        @SerializedName("slot")
        val slot: Int? = null,
        @SerializedName("type")
        val type: Type? = null
    )

    data class Type(
        @SerializedName("name")
        val name: String?,
    )
}
