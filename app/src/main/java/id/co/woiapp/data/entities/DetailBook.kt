package id.co.woiapp.data.entities

import com.google.gson.annotations.SerializedName

data class DetailBook(

	@field:SerializedName("id_book")
	val idBook: Int? = null,

	@field:SerializedName("member")
	val member: List<MemberBook?>? = null,

	@field:SerializedName("description")
	var description: String? = null,

	@field:SerializedName("title")
	var title: String? = null,

	@field:SerializedName("type")
	var type: String? = null
)

