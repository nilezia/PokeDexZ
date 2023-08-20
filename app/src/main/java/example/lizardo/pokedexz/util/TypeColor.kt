package example.lizardo.pokedexz.util

import example.lizardo.pokedexz.R

object TypeColor {

    fun getTypeColor(type: String): Int {

        return when (type) {
            "normal" -> R.color.normal
            "fighting" -> R.color.figthing
            "flying" -> R.color.flying
            "poison" -> R.color.poison
            "ground" -> R.color.ground
            "rock" -> R.color.rock
            "bug" -> R.color.bug
            "ghost" -> R.color.ghost
            "steel" -> R.color.steel
            "fire" -> R.color.fire
            "water" -> R.color.water
            "grass" -> R.color.grass
            "electric" -> R.color.electric
            "psychic" -> R.color.psychic
            "ice" -> R.color.ice
            "dragon" -> R.color.dragon
            "dark" -> R.color.dark
            "fairy" -> R.color.fairy
            "unknown" -> R.color.unknown
            "shadow" -> R.color.shadow

            else -> {
                R.color.figthing
            }
        }
    }

}