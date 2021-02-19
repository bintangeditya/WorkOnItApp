package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.*
import retrofit2.Response
import retrofit2.http.*

interface EdittaskService {

    @POST("task/update")
    suspend fun editTask(@Body book : DetailTask) : Response<RootResponse<String>>


}