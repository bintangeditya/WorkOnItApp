package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.BooktaskService
import id.co.woiapp.data.remote.service.NewtaskService
import javax.inject.Inject

class NewtaskRemoteDataSource @Inject constructor(
    private val newtaskService: NewtaskService
) : BaseDataSource() {

    suspend fun newTask(body : BodyTask) = getResult { newtaskService.newTask(body) }
}