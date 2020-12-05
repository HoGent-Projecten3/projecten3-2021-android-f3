package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.faith.data.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

/**
 * @author Arne De Schrijver
 */

class TalentViewModel @ViewModelInject constructor(
    private val repository: TalentRepository
): ViewModel() {

    fun getTalenten(): Flow<PagingData<ApiTalent>> {
        return repository.getTalenten()
    }
    fun getTalenten2(): Call<ApiTalentSearchResponse> {
        return repository.getTalenten2()
    }

    fun voegTalentToe(inhoud: String): Call<Message> {
        return repository.postTalent(inhoud);
    }
}