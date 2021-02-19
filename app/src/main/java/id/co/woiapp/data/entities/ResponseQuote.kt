package id.co.woiapp.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseQuote(

	@field:SerializedName("copyright")
	val copyright: Copyright? = null,

	@field:SerializedName("baseurl")
	val baseurl: String? = null,

	@field:SerializedName("contents")
	val contents: Contents? = null,

	@field:SerializedName("success")
	val success: Success? = null
)

data class Copyright(

	@field:SerializedName("year")
	val year: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class QuotesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("quote")
	val quote: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("background")
	val background: String? = null,

	@field:SerializedName("length")
	val length: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("permalink")
	val permalink: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("tags")
	val tags: List<String?>? = null
)

data class Contents(

	@field:SerializedName("quotes")
	val quotes: List<QuotesItem?>? = null
)

data class Success(

	@field:SerializedName("total")
	val total: Int? = null
)
