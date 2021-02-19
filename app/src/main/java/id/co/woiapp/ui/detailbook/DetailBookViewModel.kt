package id.co.woiapp.ui.detailbook

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailBook
import id.co.woiapp.data.entities.User
import id.co.woiapp.data.repository.BookRepository
import id.co.woiapp.data.repository.DetailBookRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class DetailBookViewModel @ViewModelInject constructor(
    private val repository: DetailBookRepository
) : ViewModel() {

    private val _books = MutableLiveData<Resource<List<DetailBook>>>()
    val books : LiveData<Resource<List<DetailBook>>> = _books

    private val _result = MutableLiveData<Resource<List<String>>>()
    val result : LiveData<Resource<List<String>>> = _result

    private val _resultUnjoin = MutableLiveData<Resource<List<String>>>()
    val resultUnjoin : LiveData<Resource<List<String>>> = _resultUnjoin

    private val _resultDelete = MutableLiveData<Resource<List<String>>>()
    val resultDelete : LiveData<Resource<List<String>>> = _resultDelete

    private val _resultMute = MutableLiveData<Resource<List<String>>>()
    val resultMute : LiveData<Resource<List<String>>> = _resultMute


    fun getDetailBook(idBook:Int, idUser:Int){
        _books.value = Resource.loading()
        viewModelScope.launch {
            _books.value = repository.getDetailBooksByIdBookIdUser(idBook, idUser)
        }
    }
    fun updateBook(book : Book){
        _result.value = Resource.loading()
        viewModelScope.launch {
            _result.value = repository.updateBook(book)
        }
    }

    fun mute(book : Book){
        _resultMute.value = Resource.loading()
        viewModelScope.launch {
            _resultMute.value = repository.mute(book)
        }
    }

    fun unjoin(idUserBook : Int){
        _resultUnjoin.value = Resource.loading()
        viewModelScope.launch {
            _resultUnjoin.value = repository.unjoin(idUserBook)
        }
    }

    fun unjoinMe(idUserBook : Int){
        _resultDelete.value = Resource.loading()
        viewModelScope.launch {
            _resultDelete.value = repository.unjoin(idUserBook)
        }
    }

    fun deleteBook(idBook : Int){
        Log.d("asdsddeletesad","ddsd")
        _resultDelete.value = Resource.loading()
        viewModelScope.launch {
            _resultDelete.value = repository.deleteBook(idBook)
        }
    }



}
