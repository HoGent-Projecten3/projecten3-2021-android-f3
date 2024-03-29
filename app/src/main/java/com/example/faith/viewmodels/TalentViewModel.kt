package com.example.faith.viewmodels

import android.os.Message
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.faith.data.TalentRepository
import retrofit2.Call

/**
 * @author Arne De Schrijver
 */

class TalentViewModel @ViewModelInject constructor(
    private val repository: TalentRepository
) : ViewModel() {

    fun voegTalentToe(inhoud: String): Call<Message> {
        return repository.postTalent(inhoud)
    }
}
