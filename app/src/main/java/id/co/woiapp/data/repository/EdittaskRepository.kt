package id.co.woiapp.data.repository

import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.BookRemoteDataSource
import id.co.woiapp.data.remote.BooktaskRemoteDataSource
import id.co.woiapp.data.remote.EdittaskRemoteDataSource
import id.co.woiapp.data.remote.NewtaskRemoteDataSource
import id.co.woiapp.utils.performGetRealValueOperation
import javax.inject.Inject

class EdittaskRepository @Inject constructor(
    private val remoteDataSource: EdittaskRemoteDataSource
) {

    suspend fun editTask(body : DetailTask) =
        performGetRealValueOperation { remoteDataSource.editTask(body) }


}