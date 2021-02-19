package id.co.woiapp.data.repository

import id.co.woiapp.data.remote.BookRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val remoteDataSource: BookRemoteDataSource
){
    suspend fun getBooksByIdUser(idUser :Int) = performGetRealValueOperation { remoteDataSource.getBooksByIdUser(idUser) }
    suspend fun getQuote() = performGetRealValueOperation { remoteDataSource.getQuote() }
}