package com.example.faith.data

import SeedDatabaseWorker
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.faith.utilities.DATABASE_NAME
/**
 * @author Remi Mestdagh
 */

@Database(entities = arrayOf(Medium::class,Bericht::class,Hulpbron::class, MediumRemoteKey::class), version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mediumDao(): MediumDao
    abstract fun berichtDao(): BerichtDao
    abstract fun remoteKeys(): MediumRemoteKeyDao
    abstract fun hulpbronDao(): HulpbronDao


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
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
