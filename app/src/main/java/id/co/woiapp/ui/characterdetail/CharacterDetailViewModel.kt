//package id.co.woiapp.ui.characterdetail
//
//import androidx.hilt.lifecycle.ViewModelInject
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.switchMap
//import id.co.woiapp.data.entities.Character
//import id.co.woiapp.data.repository.CharacterRepository
//import id.co.woiapp.utils.Resource
//
//class CharacterDetailViewModel @ViewModelInject constructor(
//    private val repository: CharacterRepository
//) : ViewModel() {
//
//    private val _id = MutableLiveData<Int>()
//
//    private val _character = _id.switchMap { id ->
//        repository.getCharacter(id)
//    }
//    val character: LiveData<Resource<Character>> = _character
//
//
//    fun start(id: Int) {
//        _id.value = id
//    }
//
//}
