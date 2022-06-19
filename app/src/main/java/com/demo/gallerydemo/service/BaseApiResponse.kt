package com.demo.gallerydemo.service

import com.demo.gallerydemo.repo.Resource
import retrofit2.Response

abstract class BaseApiResponse {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>):
            Resource<T>{
        try {
            val response = apiCall()
            when {
                response.isSuccessful -> {
                    val body = response.body()
                    body?.let {
                        return Resource.Success(body)
                    }
                }
                response.code() == 403 -> {
                    return error("Error 404 not found")
                }
                response.code() == 404 -> {
                    return error("Forbidden")
                }
                response.code() == 502 -> {
                    return error("Bad Gateway")
                }
            }
            return error("${response.code()} ${response.message()}")
        }catch (e:Exception){
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage:String):Resource<T> =
        Resource.Error("Api error $errorMessage",null)
}