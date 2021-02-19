package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Book

import id.co.woiapp.data.entities.RootResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface NewbookService {

    @POST("book/new")
    suspend fun newbook(@Body book : Book) : Response<RootResponse<Book>>

}