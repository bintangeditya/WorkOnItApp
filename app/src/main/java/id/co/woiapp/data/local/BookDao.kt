//package id.co.woiapp.data.local
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import id.co.woiapp.data.entities.Book
//import id.co.woiapp.data.entities.Character
//
//@Dao
//interface BookDao {
//
//    @Query("SELECT * FROM books")
//    fun getAllCharacters() : LiveData<List<Character>>
//
//    @Query("SELECT * FROM books WHERE book_id = :book_id")
//    fun getCharacter(book_id: Int): LiveData<Character>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(books: List<Book>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(books: Book)
//
//
//}