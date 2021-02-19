package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.RootResponse
import id.co.woiapp.data.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {
//    @GET("book/userId/6")
//    suspend fun login() : Response<RootResponse<Book>>
    @POST("user/login")
    suspend fun login(@Body body:User) : Response<RootResponse<User>>
}