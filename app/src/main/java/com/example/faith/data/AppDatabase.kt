package com.example.faith.data

import SeedDatabaseWorker
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.faith.utilities.DATABASE_NAME
/**
 * @author Remi Mestdagh
 */

@Database(entities = arrayOf(Medium::class, Bericht::class, Hulpbron::class, MediumRemoteKey::class, Talent::class), version = 16, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediumDao(): MediumDao
    abstract fun berichtDao(): BerichtDao
    abstract fun remoteKeys(): MediumRemoteKeyDao
    abstract fun hulpbronDao(): HulpbronDao
    abstract fun talentDao(): TalentDao

    companion object {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .fallbackToDestructiveMigration().build()
        }
    }
}
