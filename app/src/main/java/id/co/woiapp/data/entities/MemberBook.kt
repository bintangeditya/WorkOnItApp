package id.co.woiapp.data.entities

import com.google.gson.annotations.SerializedName


data class MemberBook(

    @field:SerializedName("id_book")
    val idBook: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("id_book_user")
    val idBookUser: Int? = null,

    @field:SerializedName("mute")
    val mute: Int? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)