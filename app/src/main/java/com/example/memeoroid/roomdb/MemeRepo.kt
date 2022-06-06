package com.example.memeoroid.roomdb

import android.content.Context
import androidx.lifecycle.LiveData

// DB Data Repository
// Near identical to the DAO
// Like the DbViewModel, its job is to separate the data from the UI
class MemeRepo(context: Context) {
    var db:MemeDao? = AppDatabase.getInstance(context)?.memeDao()

    // Create
    fun insertFavorite(meme: Meme) {
        db?.insertFavorite(meme)
    }

    // Read
    fun selectAllFavorites() : LiveData<List<Meme>>? {
        return db?.selectAllFavorites()
    }

    fun selectFavorite(key: Int): LiveData<Meme>? {
        return db?.selectFavorite(key)
    }

    fun search(text: String) : LiveData<List<Meme>>? {
        return db?.search("%" + text + "%")
    }

    // Update
    fun updateFavorite(meme: Meme) {
        db?.updateFavorite(meme)
    }

    // Delete
    fun deleteFavorite(meme: Meme) {
        db?.deleteFavorite(meme)
    }

    fun deleteAllFavorites() {
        db?.deleteAllFavorites()
    }
}