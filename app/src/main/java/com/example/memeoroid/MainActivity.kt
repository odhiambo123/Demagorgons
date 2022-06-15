package com.example.memeoroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

//Home Page/ Main Activity page
//Navigates to the different functionalities and pages of the app
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("Opening main")
        //Hides the app title and the system notifications top bar
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //Redirect to the new meme creation page
        createMeme.setOnClickListener{
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"Redirecting to Create Meme", Toast.LENGTH_LONG).show()
        }

        //Redirect to the favorite meme list page
        favorites.setOnClickListener{
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"Redirecting to Favorite Memes", Toast.LENGTH_LONG).show()
        }

        //Redirect to the surf memes randomly selected and display from reddit
        surfMeme.setOnClickListener{
            val intent = Intent(this, SurfMemesActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"Redirecting to Browse Memes", Toast.LENGTH_LONG).show()
        }
    }
}