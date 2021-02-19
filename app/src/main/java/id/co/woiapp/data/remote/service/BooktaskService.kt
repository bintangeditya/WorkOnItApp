package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BooktaskService {

    @GET("task/userId/{user_id}/bookId/{book_id}")
    suspend fun getTasksByIdUserIdBook(@Path("user_id") idUser : Int,
                                       @Path("book_id") idBook : Int) : Response<RootResponse<BookTask>>

    @POST("task/check")
    suspend fun checkTask(@Body body : CheckTaskBody) : Response<RootResponse<String>>

}