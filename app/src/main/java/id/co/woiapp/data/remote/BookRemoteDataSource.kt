package id.co.woiapp.data.remote

import id.co.woiapp.data.remote.service.BookService
import javax.inject.Inject

class BookRemoteDataSource @Inject constructor(
    private val bookService: BookService
) : BaseDataSource() {

    suspend fun getBooksByIdUser(idUser: Int) = getResult { bookService.getBooksByIdUser(idUser) }
    suspend fun getQuote() = getResultQ { bookService.getQuote() }
}