package com.demo.gallerydemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.demo.gallerydemo.databinding.ImagesListItemBinding
import com.demo.gallerydemo.model.Photos
import javax.inject.Inject

class PhotosListAdapter @Inject constructor() :ListAdapter<Photos, PhotosListAdapter.PhotoViewHolder>(DiffCallback()){

    class PhotoViewHolder constructor(private val binding:ImagesListItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Photos){
            binding.photos = item
            binding.executePendingBindings()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Photos>() {
        override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photos, newItem: Photos) =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ImagesListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder = PhotoViewHolder(binding)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}