package id.co.woiapp.ui.edittask

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.BodyTask
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.DetailTask
import id.co.woiapp.data.repository.EdittaskRepository
import id.co.woiapp.data.repository.JoinbookRepository
import id.co.woiapp.data.repository.NewtaskRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class EdittaskViewModel @ViewModelInject constructor(
    private val repository: EdittaskRepository
) : ViewModel() {

    private val _response = MutableLiveData<Resource<List<String>>>()
    val response : LiveData<Resource<List<String>>> = _response



    fun edittask(body: DetailTask){
        _response.value = Resource.loading()
        viewModelScope.launch {
            _response.value = repository.editTask(body)
        }
    }

}