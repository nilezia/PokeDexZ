package example.lizardo.pokedexz.di

import dagger.Binds
import dagger.Module
import example.lizardo.pokedexz.data.rerpository.PokemonRepository
import example.lizardo.pokedexz.data.rerpository.PokemonRepositoryImpl
import example.lizardo.pokedexz.domain.GetPokemonUseCase
import example.lizardo.pokedexz.domain.GetPokemonUseCaseImpl
import example.lizardo.pokedexz.domain.MappingPokemonTypeUseCase
import example.lizardo.pokedexz.domain.MappingPokemonTypeUseCaseImpl

@Module
interface ApplicationBindsModule {
    @Binds
    fun bindsGetPokemonUseCase (
        getPokemonUseCaseImpl: GetPokemonUseCaseImpl
    ): GetPokemonUseCase

    @Binds
    fun bindsMappingPokemonTypeUseCase (
        mappingPokemonTypeUseCase: MappingPokemonTypeUseCaseImpl
    ): MappingPokemonTypeUseCase

    @Binds
    fun bindsPokemonRepository (
        pokemonRepositoryImpl: PokemonRepositoryImpl
    ): PokemonRepository
}