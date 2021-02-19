package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.BookRemoteDataSource
import id.co.woiapp.data.remote.DetailBookRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import retrofit2.http.Body
import javax.inject.Inject

class DetailBookRepository @Inject constructor(
    private val remoteDataSource: DetailBookRemoteDataSource
){
    suspend fun getDetailBooksByIdBookIdUser(idBook : Int, idUser :Int) = performGetRealValueOperation { remoteDataSource.getDetailBooksByIdBookIdUser(idBook,idUser) }
    suspend fun updateBook(body : Book) = performGetRealValueOperation { remoteDataSource.updateBook(body) }
    suspend fun mute(body : Book) = performGetRealValueOperation { remoteDataSource.mute(body) }
    suspend fun unjoin(idUserBook : Int) = performGetRealValueOperation { remoteDataSource.unjoin(idUserBook) }
    suspend fun deleteBook(idBook : Int) = performGetRealValueOperation { remoteDataSource.deleteBook(idBook) }
}