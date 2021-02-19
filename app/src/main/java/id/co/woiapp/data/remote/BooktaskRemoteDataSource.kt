package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.CheckTaskBody
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.BooktaskService
import javax.inject.Inject

class BooktaskRemoteDataSource @Inject constructor(
    private val booktaskService: BooktaskService
) : BaseDataSource() {

    suspend fun getTasksByIdUserIdBook(
        idUser: Int,
        idBook: Int
    ) = getResult { booktaskService.getTasksByIdUserIdBook(idUser,idBook) }

    suspend fun checkTask(body : CheckTaskBody) = getResult { booktaskService.checkTask(body) }
}