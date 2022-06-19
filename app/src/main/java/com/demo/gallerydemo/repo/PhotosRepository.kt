package com.demo.gallerydemo.repo

import com.demo.gallerydemo.model.Photos
import com.demo.gallerydemo.service.ApiService
import com.demo.gallerydemo.service.BaseApiResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class PhotosRepository @Inject internal constructor(var apiService: ApiService):BaseApiResponse(){
    suspend fun getPhotos():Flow<Resource<MutableList<Photos>>>{
        return flow {
            emit(safeApiCall { apiService.getPhotos() })
        }.flowOn(Dispatchers.IO)
    }
}