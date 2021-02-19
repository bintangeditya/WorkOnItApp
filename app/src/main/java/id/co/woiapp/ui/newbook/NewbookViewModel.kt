package id.co.woiapp.ui.newbook

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.repository.JoinbookRepository
import id.co.woiapp.data.repository.NewbookRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class NewbookViewModel @ViewModelInject constructor(
    private val repository: NewbookRepository
) : ViewModel() {

    private val _response = MutableLiveData<Resource<List<Book>>>()
    val response : LiveData<Resource<List<Book>>> = _response

    fun newbook(body: Book){
        _response.value = Resource.loading()
        viewModelScope.launch {
            _response.value = repository.newbook(body)
        }
    }
}