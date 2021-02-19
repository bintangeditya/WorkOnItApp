package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.CheckTaskBody
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.BookRemoteDataSource
import id.co.woiapp.data.remote.BooktaskRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class BooktaskRepository @Inject constructor(
    private val remoteDataSource: BooktaskRemoteDataSource
) {
    suspend fun getTasksByIdUserIdBook(
        idUser: Int,
        idBook: Int
    ) = performGetRealValueOperation { remoteDataSource.getTasksByIdUserIdBook(idUser,idBook) }

    suspend fun checkTask(body : CheckTaskBody) = performGetRealValueOperation { remoteDataSource.checkTask(body) }
}