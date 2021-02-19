package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.JoinbookRemoteDataSource
import id.co.woiapp.data.remote.NewbookRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class NewbookRepository @Inject constructor(
    private val remoteDataSource: NewbookRemoteDataSource
){
    suspend fun newbook(body : Book) = performGetRealValueOperation { remoteDataSource.newbook(body) }
}