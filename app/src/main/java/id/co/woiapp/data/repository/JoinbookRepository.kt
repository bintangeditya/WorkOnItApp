package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.JoinbookRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class JoinbookRepository @Inject constructor(
    private val remoteDataSource: JoinbookRemoteDataSource
){
    suspend fun joinbook(body : Book) = performGetRealValueOperation { remoteDataSource.joinbook(body) }
}