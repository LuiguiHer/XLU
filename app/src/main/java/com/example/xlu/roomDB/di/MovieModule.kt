package com.example.xlu.roomDB.di

import android.content.Context
import androidx.room.Room
import com.example.xlu.roomDB.network.DaoMovie
import com.example.xlu.roomDB.network.MoviesDB
import com.example.xlu.roomDB.repository.RepositoryMovieImpl
import com.example.xlu.roomDB.repository.RepositoryMovieRoom
import com.example.xlu.ui.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    fun providesMovieRoomDb(
        @ApplicationContext
        context: Context
    )= Room.databaseBuilder(context,MoviesDB::class.java, Utils.TABLE_NAME_ROOM)
        .build()

    @Provides
    fun providesDaoMovies(
        movieDb: MoviesDB
    ) = movieDb.daoMovie()

    @Provides
    fun providesRepositoryMovieRoom(
        daoMovie: DaoMovie
    ): RepositoryMovieRoom = RepositoryMovieImpl(
        daoMovie = daoMovie
    )
}