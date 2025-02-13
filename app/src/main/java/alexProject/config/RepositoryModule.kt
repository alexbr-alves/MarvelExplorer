package alexProject.config

import alexProject.data.repository.CharacterRepository
import alexProject.data.repository.ComicRepository
import alexProject.data.repository.MarvelRepository
import alexProject.domain.repository.CharacterRepositoryImpl
import alexProject.domain.repository.ComicRepositoryImpl
import alexProject.domain.repository.MarvelRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsMarvelRepository(
        repository: MarvelRepositoryImpl
    ): MarvelRepository

    @Binds
    fun bindsComicRepository(
        repository: ComicRepositoryImpl
    ): ComicRepository

    @Binds
    fun bindsCharacterRepository(
        repository: CharacterRepositoryImpl
    ): CharacterRepository
}