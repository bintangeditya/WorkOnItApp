package id.co.woiapp.data.remote

import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.remote.service.BookService
import id.co.woiapp.data.remote.service.DetailBookService
import javax.inject.Inject

class DetailBookRemoteDataSource @Inject constructor(
    private val detailbookService: DetailBookService
): BaseDataSource() {

    suspend fun getDetailBooksByIdBookIdUser(idBook : Int, idUser : Int) = getResult { detailbookService.getDetailBooksByIdBookIdUser(idBook,idUser) }
    suspend fun updateBook(body : Book) = getResult { detailbookService.updateBook(body) }
    suspend fun mute(body : Book) = getResult { detailbookService.mute(body) }
    suspend fun unjoin(idUserBook : Int) = getResult { detailbookService.unjoin(idUserBook) }
    suspend fun deleteBook(idBook : Int) = getResult { detailbookService.deleteBook(idBook) }
}