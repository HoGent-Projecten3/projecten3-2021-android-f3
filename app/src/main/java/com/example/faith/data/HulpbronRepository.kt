package com.example.faith.data

import android.os.Message
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.faith.api.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class HulpbronRepository @Inject constructor(
    private val hulpbronDao: HulpbronDao,
    private val service: ApiService,
    private val db: AppDatabase
) {

    @ExperimentalPagingApi
    fun getHulpbronnen(
        textFilter: String,
        includePublic: Boolean,
        includePrivate: Boolean,
        hulpbronNaam: String
    ): Flow<PagingData<Hulpbron>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 20
            ),
            remoteMediator = HulpbronRemoteMediator(db, service, hulpbronNaam, textFilter, includePublic, includePrivate),
        ) {

            hulpbronDao.getAll()
        }
            .flow
    }

    fun getHulpbron(id: Int) = hulpbronDao.getOne(id)

    fun postHulpbron(
        titel: String,
        beschrijving: String,
        url: String,
        telefoonnummer: String,
        emailadres: String,
        chatUrl: String
    ): Call<Message> {
        return service.postHulpbron(HulpbronBody(titel, beschrijving, url, telefoonnummer, emailadres, chatUrl))
    }

    fun deleteHulpbron(hulpbronId: Int): Call<Int> {
        return service.deleteHulpbron(hulpbronId)
    }

    suspend fun insertOne(hulpbron: Hulpbron) = hulpbronDao.insertOne(hulpbron)
    suspend fun deleteHulpbronRoom(hulpbronId: Int) {
        hulpbronDao.removeHulpbron(hulpbronId)
    }
}
