package com.example.faith.di

import android.content.Context
import com.example.faith.data.AppDatabase
import com.example.faith.data.BerichtDao
import com.example.faith.data.HulpbronDao
import com.example.faith.data.MediumDao
import com.example.faith.data.MediumRemoteKeyDao
import com.example.faith.data.TalentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
/**
 * @author Remi Mestdagh
 */
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
    @Provides
    fun provideHulpbronDao(appDatabase: AppDatabase): HulpbronDao {
        return appDatabase.hulpbronDao()
    }
    /*@Provides
    fun provideDoelDao(appDatabase: AppDatabase): DoelDao {
        return appDatabase.DoelDao()
    }*/

    @Provides
    fun provideMediumRemoteKeysDao(appDatabase: AppDatabase) : MediumRemoteKeyDao {
        return appDatabase.remoteKeys()
    }
    @Provides
    fun provideBerichtDao(appDatabase: AppDatabase): BerichtDao {
        return appDatabase.berichtDao()
    }

    @Provides
    fun provideTalentDao(appDatabase: AppDatabase): TalentDao {
        return appDatabase.talentDao()
    }
}
