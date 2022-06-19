package com.demo.gallerydemo.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.demo.gallerydemo.BR
import com.demo.gallerydemo.R
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.parcelize.Parcelize


@Parcelize
data class Photos(

	@field:JsonProperty("albumId")
	var albumId: Int? = 0,

	@field:JsonProperty("id")
	var id: Int? = 0,

	@field:JsonProperty("title")
	var title: String? = "",

	@field:JsonProperty("url")
	var url: String? = "",

	@field:JsonProperty("thumbnailUrl")
	var thumbnailUrl: String? = ""
) : Parcelable

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
	view.load(imageUrl){
		crossfade(true)
		placeholder(R.drawable.image_not_supported)
	}
}
