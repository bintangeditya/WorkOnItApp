//package id.co.woiapp.data.local
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.co.woiapp.data.entities.Character
//import id.co.woiapp.data.entities.User
//
//@Dao
//interface LoginDao {
//
//    @Query("SELECT * FROM user")
//    fun getAllUser() : LiveData<List<User>>
//
//    @Query("SELECT * FROM user WHERE id_user = :id")
//    fun getUser(id_user: Int): LiveData<Character>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(users: List<User>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(user: User)
//
//
//}