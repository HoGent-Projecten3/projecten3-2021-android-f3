package com.example.faith.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.faith.api.ApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
data class TalentRemoteMediator(
    private val db: AppDatabase,
    private
    val service: ApiService,
    private
    val talentNaam: String
) : RemoteMediator<Int, Talent>() {

    private val talentDao: TalentDao = db.talentDao()
    private val remoteKeyDao: MediumRemoteKeyDao = db.remoteKeys()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Talent>
    ): MediatorResult {

        try {
            // Get the closest item from PagingState that we want to load data around.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // Query DB for SubredditRemoteKey for the subreddit.
                    // SubredditRemoteKey is a wrapper object we use to keep track of page keys we
                    // receive from the Reddit API to fetch the next or previous page.
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByPost(talentNaam)
                    }

                    // We must explicitly check if the page key is null when appending, since the
                    // Reddit API informs the end of the list by returning null for page key, but
                    // passing a null key to Reddit API will fetch the initial page.
                    if (remoteKey.nextPageKey == (-1).toString()) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextPageKey
                }
            }

            val data = service.getTalenten(
                page = when (loadType) {
                    LoadType.REFRESH -> 0
                    else -> loadKey!!.toInt()
                },
                perPage = 20
            )

            val items = data.resultaten

            db.withTransaction {
                remoteKeyDao.insert(MediumRemoteKey(talentNaam, data.next))
                items.forEach {
                    talentDao.insertOne(
                        Talent(
                            it.talentId,
                            it.inhoud,
                            it.type
                        )
                    )
                }
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
