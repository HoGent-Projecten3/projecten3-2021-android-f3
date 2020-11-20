/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.faith.data.AppDatabase
import com.example.faith.data.Hulpbron
import com.example.faith.data.Medium
import com.example.faith.utilities.MEDIA_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(MEDIA_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->





                    val mediumType = object : TypeToken<List<Medium>>() {}.type
                    val mediumList: List<Medium> = Gson().fromJson(jsonReader, mediumType)

                    val hulpbronType = object : TypeToken<List<Hulpbron>>() {}.type
                    val hulpbronList: List<Hulpbron> = Gson().fromJson(jsonReader, hulpbronType)

                    val database = AppDatabase.getInstance(applicationContext)

                    database.mediumDao().insertAll(mediumList)
                    database.hulpbronDao().insertAll(hulpbronList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
