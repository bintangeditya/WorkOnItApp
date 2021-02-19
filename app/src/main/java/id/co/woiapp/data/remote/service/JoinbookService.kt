package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Book

import id.co.woiapp.data.entities.RootResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface JoinbookService {

    @POST("book/join")
    suspend fun joinbook(@Body book : Book) : Response<RootResponse<Book>>

}