package id.co.woiapp.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTask(

	@field:SerializedName("title_task")
	var titleTask: String? = null,

	@field:SerializedName("id_task")
	val idTask: Int? = null,

	@field:SerializedName("description_task")
	var descriptionTask: String? = null,

	@field:SerializedName("due_date")
	var dueDate: String? = null,

	@field:SerializedName("id_lable")
	val idLable: Int? = null,

	@field:SerializedName("id_book")
	val idBook: Int? = null

) : Parcelable
