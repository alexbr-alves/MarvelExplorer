package alexProject.config

import alexProject.data.repository.MarvelRepository
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
}