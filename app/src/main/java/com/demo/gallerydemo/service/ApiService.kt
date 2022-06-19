package com.demo.gallerydemo.service

import com.demo.gallerydemo.APIConstants
import com.demo.gallerydemo.model.Photos
import com.demo.gallerydemo.repo.Resource
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(APIConstants.photosEndUrl)
    suspend fun getPhotos():Response<MutableList<Photos>>
}