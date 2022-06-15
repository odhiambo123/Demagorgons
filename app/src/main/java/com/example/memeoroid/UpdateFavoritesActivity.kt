package com.example.memeoroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.memeoroid.retrofit.*
import com.example.memeoroid.roomdb.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.android.synthetic.main.activity_update.* //changed from new

//Select a meme from a curated list fetched from an API online
//Add text to the selected meme and generate a custom meme to view/save to gallery/ add to favorites
class UpdateFavoritesActivity : AppCompatActivity() {

    //variables to access API and fetch json objects in the API
    private lateinit var vm: ApiViewModel
    lateinit var dbvm: DbViewModel
    private var descriptions: MutableList<String> = ArrayList()
    private var urls: MutableList<String> = ArrayList()

    //variables to collect user input text to add to meme
     lateinit var topText : EditText
     lateinit var bottomText : EditText

    //variable to pass the id of the image selected to the next page to generate and display the custom meme
    lateinit var imageSelected : String
    lateinit var imageDesc : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update) //changed from new

        //Hides the app title and the system notifications top bar
        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        //Floating button redirects to the home page
        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        topText = findViewById(R.id.editTopText)
        bottomText = findViewById(R.id.editBottomText)

        imageSelected = intent.getStringExtra("dropdown")!!
        topText.setText(intent.getStringExtra("topText"))
        bottomText.setText(intent.getStringExtra("bottomText"))

        dbvm = DbViewModel(application)

        val inter = RetroApiInterface.create()
        val repo = TemplateRepo(inter)
        vm = ApiViewModel(repo)

      vm.getAllTemplates()

        vm.templateList.observe(this) { for (item in it) { descriptions.add(item.name)

            urls.add(item.image) }

            val dropdownAdapter: ArrayAdapter<String> = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, descriptions)

            dropdown.adapter = dropdownAdapter
            dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected( parent: AdapterView<*>, view: View?,
                                             position: Int, id: Long){
                    //assigning image id to a variable to pass to the next page
                    // to fetch and display image on that page
                    Picasso.with(this@UpdateFavoritesActivity).load(urls[position]).into(imageView)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Picasso.with(this@UpdateFavoritesActivity).load(urls[0]).into(imageView)
                }
            }

            dropdown.setSelection(imageSelected.toInt())
        }

        //Resets both top and bottom text field and image
        cancelButton.setOnClickListener{
            val myIntent = Intent(this, FavoritesActivity::class.java)
            startActivity(myIntent)
        }

        //Redirects to the display custom generated meme page
        //passes the top and bottom text string to that page
        //passes the image id to the that page where the custom meme is generated
        // by fetching the image from the json object
        updateMemeButton.setOnClickListener{

            val topTemp = topText.text.toString()
            val bottomTemp = bottomText.text.toString()
            val img = dropdown.selectedItemPosition.toString()
            val id = intent.getIntExtra("memeId",0)

            dbvm.updateFavorite(Meme(id, topTemp, bottomTemp, img))

            val intent = Intent(this, CustomMemeDisplayActivity::class.java)

            intent.putExtra("imageSelected",img)
            intent.putExtra("topText",topTemp)
            intent.putExtra("bottomText",bottomTemp)
            Toast.makeText(applicationContext,"Loading Custom Meme", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

        floatingBtn.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }
    }
}