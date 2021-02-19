package id.co.woiapp.ui.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.User
import id.co.woiapp.data.repository.BookRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val repository: BookRepository
) : ViewModel() {

    private val _books = MutableLiveData<Resource<List<Book>>>()
    val books : LiveData<Resource<List<Book>>> = _books

    private val _quote = MutableLiveData<Resource<List<String>>>()
    val quote : LiveData<Resource<List<String>>> = _quote

    fun getBook(idUser:Int){
        Log.d("dasdasdasue",idUser.toString())
        _books.value = Resource.loading()
        viewModelScope.launch {
            _books.value = repository.getBooksByIdUser(idUser)
        }
    }

    fun getQuote(){
        _quote.value = Resource.loading()
        viewModelScope.launch {
            _quote.value = repository.getQuote()
        }
    }
}
