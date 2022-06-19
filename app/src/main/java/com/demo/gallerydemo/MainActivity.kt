package com.demo.gallerydemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.gallerydemo.databinding.ActivityMainBinding
import com.demo.gallerydemo.model.Photos
import com.demo.gallerydemo.repo.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val photosViewModel by viewModels<PhotosViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var showRefreshBtn: Boolean = true

    @Inject
    lateinit var photosListAdapter: PhotosListAdapter
    private var photoList: MutableList<Photos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        addListener()
        observeData()
    }

    private fun initUI() {
        showProgressBar(true)
        photoList.clear()
        photosViewModel.startIndex = 0
        photosViewModel.endIndex = 199
        fetchPhotos(true)
        binding.apply {
            imagesRv.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = photosListAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (showRefreshBtn) {
                            if (dy > 0)
                                refreshFab.hide()
                            else if (dy < 0)
                                refreshFab.show()
                        }
                    }
                })
            }
        }
    }

    private fun addListener() {
        binding.refreshFab.setOnClickListener {
            it.isVisible = false
            showProgressBar(true)
            photosViewModel.startIndex = photosViewModel.endIndex + 1
            photosViewModel.endIndex = photosViewModel.endIndex + 200
            fetchPhotos(false)
        }
    }

    private fun observeData() {
        photosViewModel.response.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    showRefreshBtn = true
                    binding.refreshFab.isVisible = true
                    response.data?.let {
                        photoList.addAll(photosViewModel.parseResponse(it))
                        photosListAdapter.submitList(photoList)
                        Log.e("responseList", photoList.size.toString())
                        Log.e("responseList", photoList.toString())
                        showProgressBar(false)
                    }
                }

                is Resource.Error -> {
                    showRefreshBtn = true
                    binding.refreshFab.isVisible = true
                    showProgressBar(false)
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showProgressBar(true)
                }
            }
        }
    }

    private fun fetchPhotos(isFirstTimeFetch: Boolean) {
        if (isFirstTimeFetch) {
            photosViewModel.fetchPhotos()
        } else {
            if (photosViewModel.endIndex < photosViewModel.totalCount) {
                photosViewModel.fetchPhotos()
            } else {
                binding.refreshFab.visibility = View.GONE
                showRefreshBtn = false
                Toast.makeText(this, "End of records", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showProgressBar(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }
}