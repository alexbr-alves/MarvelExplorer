package alexProject.config

import alexProject.data.repository.CharacterRepository
import alexProject.data.repository.ComicRepository
import alexProject.data.repository.DetailsRepository
import alexProject.data.repository.FavoriteRepository
import alexProject.data.repository.GithubRepository
import alexProject.data.repository.MarvelRepository
import alexProject.data.repository.TimelineRepository
import alexProject.domain.repository.CharacterRepositoryImpl
import alexProject.domain.repository.ComicRepositoryImpl
import alexProject.domain.repository.DetailsRepositoryImpl
import alexProject.domain.repository.FavotiteRepositoryImpl
import alexProject.domain.repository.GithubRepositoryImpl
import alexProject.domain.repository.MarvelRepositoryImpl
import alexProject.domain.repository.TimelineRepositoryImpl
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

    @Binds
    fun bindsTimelineRepository(
        repository: TimelineRepositoryImpl
    ): TimelineRepository

    @Binds
    fun bindsGithubRepository(
        repository: GithubRepositoryImpl
    ): GithubRepository

    @Binds
    fun bindsFavoriteRepository(
        repository: FavotiteRepositoryImpl
    ): FavoriteRepository

    @Binds
    fun bindsDetailsRepository(
        repository: DetailsRepositoryImpl
    ): DetailsRepository
}