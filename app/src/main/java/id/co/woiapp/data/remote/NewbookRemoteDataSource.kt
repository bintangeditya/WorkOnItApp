package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.JoinbookService
import id.co.woiapp.data.remote.service.NewbookService
import javax.inject.Inject

class NewbookRemoteDataSource @Inject constructor(
    private val newbookService: NewbookService
): BaseDataSource() {

    suspend fun newbook(body : Book) = getResult { newbookService.newbook(body) }
}