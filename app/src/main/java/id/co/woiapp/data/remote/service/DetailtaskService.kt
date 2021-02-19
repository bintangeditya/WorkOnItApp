package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Character
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.entities.RootResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailtaskService {
    @GET("task/detail/{id_task}")
    suspend fun getDetailTask(@Path("id_task") idTask: Int): Response<RootResponse<DetailTask>>

    @DELETE("task/{id_task}")
    suspend fun deleteTask(@Path("id_task") idTask : Int) : Response<RootResponse<String>>

}