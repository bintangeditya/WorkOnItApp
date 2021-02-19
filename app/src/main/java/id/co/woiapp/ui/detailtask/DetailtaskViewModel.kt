package id.co.woiapp.ui.detailtask

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.*
import id.co.woiapp.data.repository.BookRepository
import id.co.woiapp.data.repository.BooktaskRepository
import id.co.woiapp.data.repository.DetailtaskRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class DetailtaskViewModel @ViewModelInject constructor(
    private val repository: DetailtaskRepository
) : ViewModel() {

    private val _tasks = MutableLiveData<Resource<List<DetailTask>>>()
    val tasks : LiveData<Resource<List<DetailTask>>> = _tasks

    private val _responseDelete = MutableLiveData<Resource<List<String>>>()
    val responseDelete : LiveData<Resource<List<String>>> = _responseDelete


    fun getTask(idTask:Int){
        _tasks.value = Resource.loading()
        viewModelScope.launch {
            _tasks.value = repository.getDetailTask(idTask)
        }
    }


    fun deletetask(idTask: Int){
        _responseDelete.value = Resource.loading()
        viewModelScope.launch {
            _responseDelete.value = repository.deleteTask(idTask)
        }
    }

}
