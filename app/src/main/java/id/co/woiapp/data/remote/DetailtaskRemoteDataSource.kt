package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.CheckTaskBody
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.BooktaskService
import id.co.woiapp.data.remote.service.DetailtaskService
import javax.inject.Inject

class DetailtaskRemoteDataSource @Inject constructor(
    private val detailtaskService: DetailtaskService
) : BaseDataSource() {

    suspend fun getDetailTask(idTask : Int ) = getResult { detailtaskService.getDetailTask(idTask) }
    suspend fun deleteTask(idTask : Int) = getResult { detailtaskService.deleteTask(idTask) }

}