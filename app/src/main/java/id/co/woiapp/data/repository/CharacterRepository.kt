//package id.co.woiapp.data.repository
//
//import id.co.woiapp.data.local.CharacterDao
//import id.co.woiapp.data.remote.CharacterRemoteDataSource
//import id.co.woiapp.utils.performGetOperation
//import javax.inject.Inject
//
//class CharacterRepository @Inject constructor(
//    private val remoteDataSource: CharacterRemoteDataSource,
//    private val localDataSource: CharacterDao
//) {
//
//    fun getCharacter(id: Int) = performGetOperation(
//        databaseQuery = { localDataSource.getCharacter(id) },
//        networkCall = { remoteDataSource.getCharacter(id) },
//        saveCallResult = { localDataSource.insert(it) }
//    )
//
//    fun getCharacters() = performGetOperation(
//        databaseQuery = { localDataSource.getAllCharacters() },
//        networkCall = { remoteDataSource.getCharacters() },
//        saveCallResult = { localDataSource.insertAll(it.results) }
//    )
//}