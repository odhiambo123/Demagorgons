package com.example.memeoroid.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

// DB Entity, which defines the data being stored
// table named memes has four columns: memeId, topText, bottomText, and image_description
// these are the fields necessary to generate a meme, according to the API documentation
@Entity(tableName = "memes")
data class Meme(@PrimaryKey(autoGenerate = true) var memeId:Int?,
                  var topText:String,
                  var bottomText:String,
                  var image_description:String)