package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.JoinbookService
import javax.inject.Inject

class JoinbookRemoteDataSource @Inject constructor(
    private val joinbookService: JoinbookService
): BaseDataSource() {

    suspend fun joinbook(body : Book) = getResult { joinbookService.joinbook(body) }
}