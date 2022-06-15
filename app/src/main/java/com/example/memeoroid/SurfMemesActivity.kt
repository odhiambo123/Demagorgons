package com.example.memeoroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.activity_surfmemes.*
import kotlinx.coroutines.delay

//Browse Randoms memes from the internet
class SurfMemesActivity : AppCompatActivity() {

    //Imageview component to load and display random meme
    private lateinit var memeImageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surfmemes)

        //Hides the app title and the system notifications top bar
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN


        //Floating button redirects to the home page
        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        memeImageView = findViewById(R.id.memeImageView)


        //Function loads new meme on page creation
        loadNewMeme()
        //Button listener to load a new meme on click
        nextButton.setOnClickListener{
            loadNewMeme()
            Toast.makeText(applicationContext,"New Meme", Toast.LENGTH_SHORT).show()
        }

        floatingBtn.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }
    }

    //function to load a new image into the imageview
    //Uses Volley to fetch and load images
    //Volley because it has inbuilt support for images and this page is only to fetch and display images
    private fun loadNewMeme(){
        loadingBar.visibility = View.VISIBLE
         val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        var currentImageURL : String?
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            Response.Listener{ response -> currentImageURL = response.getString("url")
                loadingBar.visibility = View.GONE
            Picasso.with(this@SurfMemesActivity).load(currentImageURL).into(memeImageView)},
            Response.ErrorListener {
                loadingBar.visibility = View.GONE
            Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show() })
        queue.add(jsonObjectRequest)
    }
}