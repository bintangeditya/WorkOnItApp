package id.co.woiapp.data.remote.service

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailBook
import id.co.woiapp.data.entities.RootResponse
import retrofit2.Response
import retrofit2.http.*

interface DetailBookService {

    @GET("book/bookid/{id_book}/userid/{id_user}")
    suspend fun getDetailBooksByIdBookIdUser(@Path("id_book") idBook : Int,
                                 @Path("id_user") idUser : Int
    ) : Response<RootResponse<DetailBook>>

    @POST("book/update")
    suspend fun updateBook(@Body body : Book) : Response<RootResponse<String>>

    @DELETE("book/unjoin/{id_book_user}")
    suspend fun unjoin(@Path("id_book_user") idUserBook : Int)  : Response<RootResponse<String>>

    @DELETE("book/{id_book}")
    suspend fun deleteBook(@Path("id_book") idBook : Int)  : Response<RootResponse<String>>

    @POST("book/join/update")
    suspend fun mute(@Body body : Book) : Response<RootResponse<String>>
}