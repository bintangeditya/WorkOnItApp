package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.BooktaskService
import id.co.woiapp.data.remote.service.EdittaskService
import id.co.woiapp.data.remote.service.NewtaskService
import javax.inject.Inject

class EdittaskRemoteDataSource @Inject constructor(
    private val edittaskService: EdittaskService
) : BaseDataSource() {

    suspend fun editTask(body : DetailTask) = getResult { edittaskService.editTask(body) }
}