package id.co.woiapp.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookTask(

	@field:SerializedName("title_task")
	val titleTask: String? = null,

	@field:SerializedName("done_status")
	var doneStatus: Int? = null,

	@field:SerializedName("id_task")
	val idTask: Int? = null,

	@field:SerializedName("due_date")
	val dueDate: String? = null,

	@field:SerializedName("description_task")
	val desc: String? = null


	) : Parcelable
