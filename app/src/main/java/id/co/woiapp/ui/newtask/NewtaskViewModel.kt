package id.co.woiapp.ui.newtask

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.repository.JoinbookRepository
import id.co.woiapp.data.repository.NewtaskRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class NewtaskViewModel @ViewModelInject constructor(
    private val repository: NewtaskRepository
) : ViewModel() {

    private val _response = MutableLiveData<Resource<List<String>>>()
    val response : LiveData<Resource<List<String>>> = _response

    fun newtask(body: BodyTask){
        _response.value = Resource.loading()
        viewModelScope.launch {
            _response.value = repository.newTask(body)
        }
    }
}