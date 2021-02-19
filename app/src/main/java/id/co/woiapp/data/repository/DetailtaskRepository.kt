package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.CheckTaskBody
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.BookRemoteDataSource
import id.co.woiapp.data.remote.BooktaskRemoteDataSource
import id.co.woiapp.data.remote.DetailtaskRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class DetailtaskRepository @Inject constructor(
    private val remoteDataSource: DetailtaskRemoteDataSource
) {
    suspend fun getDetailTask(idTask : Int ) = performGetRealValueOperation { remoteDataSource.getDetailTask(idTask) }
    suspend fun deleteTask(idTask : Int) =
        performGetRealValueOperation { remoteDataSource.deleteTask(idTask) }
}