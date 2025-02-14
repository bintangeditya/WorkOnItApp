//package id.co.woiapp.data.remote
//
//import id.co.woiapp.data.remote.service.CharacterService
//import javax.inject.Inject
//
//class CharacterRemoteDataSource @Inject constructor(
//    private val characterService: CharacterService
//): BaseDataSource() {
//
//    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
//    suspend fun getCharacter(id: Int) = getResult { characterService.getCharacter(id) }
//}