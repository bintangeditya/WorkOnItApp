package id.co.woiapp.data.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckTaskBody(

	@field:SerializedName("id_user")
	val idUser: Int? = null,

	@field:SerializedName("done_status")
	val doneStatus: Int? = null,

	@field:SerializedName("id_task")
	val idTask: Int? = null
) : Parcelable
