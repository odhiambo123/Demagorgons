package com.example.memeoroid

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.roomdb.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_favorites.*
import kotlinx.android.synthetic.main.activity_surfmemes.*
import kotlinx.android.synthetic.main.meme_list_item.*

class FavoritesActivity : AppCompatActivity() {

    // variables necessary for recycler view to work
    lateinit var vm: DbViewModel
    var favoritesList = ArrayList<Meme>()
    lateinit var adapter: ListAdapter
    val limit = 5
    var offset =0
    var search =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        // initialize db viewmodel
        vm = DbViewModel(application)

        // get all favorite memes
        vm.allFavorites.observe(this) { favoritesList ->
            getFavorites(favoritesList)
            loadingPanel.visibility = View.GONE
            if (favoritesList.isEmpty()) {
                emptyListText.text = "No Memes? (͡๏̯͡๏)"
            } else {
                emptyListText.text = ""
            }
            if(favoritesList.size<limit){
                LoadMore.visibility = View.GONE
            }
            else{
                LoadMore.visibility = View.VISIBLE
            }
            if(offset == 0 ){
                Previous.visibility = View.GONE
            }else{
                Previous.visibility = View.VISIBLE
            }
        }

        // get reference to view to populate
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // create adapter with data source and assign adapter
        adapter = ListAdapter(favoritesList)
        recyclerView.adapter = adapter
        //DAB CODE
        //LeftSwipe
        val swipeDelete = object : OnSwipeLeft(this){
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                    val alertDialogBuilder = AlertDialog.Builder(viewHolder.itemView.context)
                    alertDialogBuilder.setTitle("Please confirm")
                    alertDialogBuilder.setMessage("Delete meme forever?")
                    alertDialogBuilder.setPositiveButton("Yes") {
                        DialogInterface: DialogInterface, i: Int ->
                        vm.deleteFavorite(favoritesList.get(viewHolder.adapterPosition))
                        adapter.deleteSwipedFavorite(viewHolder.adapterPosition)
                    }
                    alertDialogBuilder.setNegativeButton("No") {
                        DialogInterface: DialogInterface, i: Int ->
                        adapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                    alertDialogBuilder.show()
            }
        }

        val toLeftTouchHelper = ItemTouchHelper(swipeDelete)
        toLeftTouchHelper.attachToRecyclerView(recyclerView)

        //RightSwipe
        val updateFavoritesActivity = Intent(this, UpdateFavoritesActivity::class.java)


        val swipeUpdate = object : OnSwipeRight(this){
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) { updateFavoritesActivity.putExtra("memeId",favoritesList.get(viewHolder.adapterPosition).memeId)
                updateFavoritesActivity.putExtra("topText",favoritesList.get(viewHolder.adapterPosition).topText)
                updateFavoritesActivity.putExtra("bottomText",favoritesList.get(viewHolder.adapterPosition).bottomText)
                updateFavoritesActivity.putExtra("dropdown",favoritesList.get(viewHolder.adapterPosition).image_description)
                startActivity(updateFavoritesActivity)
                 //must change to update/edit, is currently delete
            }
        }

        val toRightTouchHelper = ItemTouchHelper(swipeUpdate)
        toRightTouchHelper.attachToRecyclerView(recyclerView)
        //DAB CODE END
        //search bar functionality
        searchBar.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                // get string from the searchbar EditText
                var searchText = searchBar.text.toString()
                // if empty, get all favorite memes
                // else, send text to the viewmodel
                if (searchText != "") {
                    offset = 0
                    vm.search(searchText, limit, offset)
                    search =true
                } else {
                    vm.selectAllFavorites(10,0)
                    search =  false
                }
                // necessary to update recycler view after search
           // on search we will have to visibility gone for load more button.
                vm.allFavorites.observe(this@FavoritesActivity) { favoritesList ->
                    getFavorites(favoritesList)
                    if (favoritesList.isEmpty()) {
                        emptyListText.text = "No Memes? (͡๏̯͡๏)"
                    } else {
                        emptyListText.text = ""
                    }
                    if(favoritesList.size < limit){
                        LoadMore.visibility = View.GONE
                    }else{
                        LoadMore.visibility = View.VISIBLE
                    }
                    if(offset == 0 ){
                        Previous.visibility = View.GONE
                    }else{
                        Previous.visibility = View.VISIBLE
                    }
                }
            }
        })

        LoadMore.setOnClickListener {
            //Log.d("offset&Limit", "$offset, $limit")
            offset+=limit
            loadData()
        }
        Previous.setOnClickListener {
            offset -= limit
            loadData()

        }

        addButton.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"Redirecting to Create Meme", Toast.LENGTH_LONG).show()
        }

        floatingBtn.setOnClickListener{
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
            Toast.makeText(applicationContext,"Redirecting to Home Page", Toast.LENGTH_LONG).show()
        }

    }
fun loadData(){

    if(search){
        var searchText = searchBar.text.toString()
        vm.search(searchText, limit, offset)
    }else{
        vm.selectAllFavorites(limit,offset)
    }

    vm.allFavorites.observe(this){
        getFavorites(it)
        if(it.size < limit){
            LoadMore.visibility = View.GONE
        }else{
            LoadMore.visibility = View.VISIBLE
        }
        if(offset == 0 ){
            Previous.visibility = View.GONE

        }else{
            Previous.visibility = View.VISIBLE
        }
        if (it.isEmpty()) {
            emptyListText.text = "LIST EMPTY"
        } else {
            emptyListText.text = ""
        }
    }
}
    // function used to refresh recycler view
    fun getFavorites(favoritesList: List<Meme>) {
        this.favoritesList.clear()
        this.favoritesList.addAll(favoritesList)
        adapter.notifyDataSetChanged()
    }
}