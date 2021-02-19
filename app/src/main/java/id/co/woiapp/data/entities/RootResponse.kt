package id.co.woiapp.data.entities

import com.google.gson.annotations.SerializedName

data class RootResponse <T> (

	@field:SerializedName("data")
	val data: List<T> = listOf(),

	@field:SerializedName("message")
	val message: String = "",

	@field:SerializedName("status")
	val status: Boolean = true
)
