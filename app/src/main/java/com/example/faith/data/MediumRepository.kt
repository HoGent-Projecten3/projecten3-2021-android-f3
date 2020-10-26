package com.example.faith.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediumRepository @Inject constructor(private val mediumDao:MediumDao) {

    fun getMedia()= mediumDao.getMedia()
    fun getMedium(mediumId:Int)= mediumDao.getMedium(mediumId)
}