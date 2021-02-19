package id.co.woiapp.ui.joinbook

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.repository.JoinbookRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class JoinbookViewModel @ViewModelInject constructor(
    private val repository: JoinbookRepository
) : ViewModel() {

    private val _response = MutableLiveData<Resource<List<Book>>>()
    val response : LiveData<Resource<List<Book>>> = _response

    fun joinbook(body: Book){
        _response.value = Resource.loading()
        viewModelScope.launch {
            _response.value = repository.joinbook(body)
        }
    }
}