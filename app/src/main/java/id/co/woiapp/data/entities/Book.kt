package id.co.woiapp.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(

	@field:SerializedName("id_book")
	val idBook: Int? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("id_book_user")
	val idBookUser: Int? = null,

	@field:SerializedName("mute")
	var mute: Int? = null,

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("title")
	var title: String? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable
