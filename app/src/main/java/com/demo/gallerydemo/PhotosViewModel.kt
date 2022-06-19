package com.demo.gallerydemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.gallerydemo.model.Photos
import com.demo.gallerydemo.repo.PhotosRepository
import com.demo.gallerydemo.repo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject internal constructor
    (
    private val repository:PhotosRepository,
    application: Application):AndroidViewModel(application){
    private val _response:MutableLiveData<Resource<MutableList<Photos>>>
    = MutableLiveData()
    val response:LiveData<Resource<MutableList<Photos>>> = _response
    var startIndex:Int = 0
    var endIndex:Int = 0
    var totalCount:Int = 0

    fun fetchPhotos() = viewModelScope.launch {
        repository.getPhotos().collect{ values ->
            if (totalCount == 0) { values.data?.let { totalCount = it.size - 1 } }
            _response.value = values
        }
    }

    fun parseResponse(list: MutableList<Photos>):MutableList<Photos>{
        return list.slice(startIndex..endIndex).toMutableList()
    }
}