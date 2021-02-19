package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NewtaskService {

    @POST("task/new")
    suspend fun newTask(@Body book : BodyTask) : Response<RootResponse<String>>

}