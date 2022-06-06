package com.example.memeoroid.roomdb
import androidx.lifecycle.LiveData
import androidx.room.*

// DB Data Access Object, which defines the database interactions
// AKA SQL statements
@Dao
interface MemeDao {
    // Create
    @Insert
    fun insertFavorite(meme: Meme)

    // Read
    @Query("SELECT * FROM memes ORDER BY memeId DESC")
    fun selectAllFavorites() : LiveData<List<Meme>>

    @Query("SELECT * FROM memes WHERE memeId = :key")
    fun selectFavorite(key: Int): LiveData<Meme>

    @Query("SELECT * FROM memes WHERE topText LIKE :text OR bottomText LIKE :text")
    fun search(text: String) : LiveData<List<Meme>>

    // Update
    @Update
    fun updateFavorite(meme: Meme)

    // Delete
    @Delete
    fun deleteFavorite(meme: Meme)

    @Query("delete from memes")
    fun deleteAllFavorites()

}