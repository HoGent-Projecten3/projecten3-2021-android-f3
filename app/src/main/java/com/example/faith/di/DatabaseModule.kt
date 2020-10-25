package com.example.faith.di

import android.content.Context
import com.example.faith.data.AppDatabase
import com.example.faith.data.MediumDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideMediumDao(appDatabase: AppDatabase): MediumDao {
        return appDatabase.mediumDao()
    }
}