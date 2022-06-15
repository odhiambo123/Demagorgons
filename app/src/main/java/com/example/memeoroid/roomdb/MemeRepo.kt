package com.example.memeoroid.roomdb

import android.content.Context
import androidx.lifecycle.LiveData

// DB Data Repository
// Near identical to the DAO
// Like the DbViewModel, its job is to separate the data from the UI
class MemeRepo(var dao : MemeDao ){

    // Create
    fun insertFavorite(meme: Meme) {
        dao?.insertFavorite(meme)
    }

    // Read
    fun selectAllFavorites(limit: Int, offset: Int) : LiveData<List<Meme>>? {
        return dao?.selectAllFavorites(limit, offset)
    }

    fun selectFavorite(key: Int, limit :Int, offset: Int): LiveData<Meme>? {
        return dao?.selectFavorite(key, limit , offset)
    }

    fun search(text: String, limit:Int, offset : Int) : LiveData<List<Meme>>? {
        return dao?.search("%" + text + "%", limit, offset )
    }

    // Update
    fun updateFavorite(meme: Meme) {
        dao?.updateFavorite(meme)
    }

    // Delete
    fun deleteFavorite(meme: Meme) {
        dao?.deleteFavorite(meme)
    }

    fun deleteAllFavorites() {
        dao?.deleteAllFavorites()
    }
}