package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.ResponseQuote
import id.co.woiapp.data.entities.RootResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BookService {

    @GET("book/userId/{id_user}")
    suspend fun getBooksByIdUser(@Path("id_user") idUser : Int) : Response<RootResponse<Book>>

    @GET("book/qotd")
    suspend fun getQuote() : Response<ResponseQuote>


}