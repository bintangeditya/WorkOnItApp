package id.co.woiapp.ui.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.co.woiapp.data.entities.Book
import id.co.woiapp.data.entities.User
import id.co.woiapp.data.repository.LoginRepository
import id.co.woiapp.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(private val repository: LoginRepository
) : ViewModel() {

    private val _loginUser = MutableLiveData<Resource<List<User>>>()
    val loginUser : LiveData<Resource<List<User>>> = _loginUser

    fun login(body : User){
        _loginUser.value = Resource.loading()
        viewModelScope.launch {
            _loginUser.value = repository.login(body)
        }
    }

}