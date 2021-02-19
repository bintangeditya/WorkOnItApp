package id.co.woiapp.ui.booktask

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.*
import id.co.woiapp.data.repository.BookRepository
import id.co.woiapp.data.repository.BooktaskRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class BooktaskViewModel @ViewModelInject constructor(
    private val repository: BooktaskRepository
) : ViewModel() {

    private val _tasks = MutableLiveData<Resource<List<BookTask>>>()
    val tasks : LiveData<Resource<List<BookTask>>> = _tasks

    private val _responseCheckTask= MutableLiveData<Resource<List<String>>>()
    val responseCheckTask : LiveData<Resource<List<String>>> = _responseCheckTask

    fun getTask(idUser:Int,idBook:Int){
        _tasks.value = Resource.loading()
        viewModelScope.launch {
            _tasks.value = repository.getTasksByIdUserIdBook(idUser,idBook)
        }
    }

    fun chekDone(body :CheckTaskBody){
        _responseCheckTask.value = Resource.loading()
        viewModelScope.launch {
            _responseCheckTask.value = repository.checkTask(body)
        }
    }
}
