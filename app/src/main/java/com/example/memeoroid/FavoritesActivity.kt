package com.example.memeoroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.roomdb.*
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    // variables necessary for recycler view to work
    lateinit var vm: DbViewModel
    var favoritesList = ArrayList<Meme>()
    lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // initialize db viewmodel
        vm = DbViewModel(application)

        /*
        // tests for CRUD operations
        vm.insertFavorite(Meme(null, "Top1", "Bottom1", "Drake Hotline Bling"))
        vm.insertFavorite(Meme(null, "Top2", "Bottom2", "Drake Hotline Bling"))
        vm.insertFavorite(Meme(null, "Top3", "Bottom3", "Drake Hotline Bling"))

        vm.updateFavorite(Meme(3, "Finding a new meme format", "Still using the Drake meme after 2 years", "Drake Hotline Bling"))
        vm.deleteFavorite(Meme(1, "Top1", "Bottom1", "Drake Hotline Bling"))
         */

        // get all favorite memes
        vm.allFavorites.observe(this, {
                favoritesList -> getFavorites(favoritesList)
            if (favoritesList.isEmpty()) {
                emptyListText.text = "LIST EMPTY"
            } else {
                emptyListText.text = ""
            }
        })

        // get reference to view to populate
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // create adapter with data source and assign adapter
        adapter = ListAdapter(favoritesList)
        recyclerView.adapter = adapter

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
                    vm.search(searchText)
                } else {
                    vm.selectAllFavorites()
                }
                // necessary to update recycler view after search
                vm.allFavorites.observe(this@FavoritesActivity, {
                        favoritesList -> getFavorites(favoritesList)
                    if (favoritesList.isEmpty()) {
                        emptyListText.text = "LIST EMPTY"
                    } else {
                        emptyListText.text = ""
                    }
                })
            }
        })

    }

    // function used to refresh recycler view
    fun getFavorites(favoritesList: List<Meme>) {
        this.favoritesList.clear()
        this.favoritesList.addAll(favoritesList)
        adapter.notifyDataSetChanged()
    }
}