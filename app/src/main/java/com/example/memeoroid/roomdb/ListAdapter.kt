package com.example.memeoroid.roomdb

import com.example.memeoroid.R
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

// Adapter, which makes a view for each meme in the dataset
class ListAdapter(private val favoriteList: List<Meme>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meme_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add element to view holder
        val itemVM = favoriteList[position]
        holder.topText.text = itemVM.topText
        holder.bottomText.text = itemVM.bottomText

        holder.card.setOnClickListener() {
            Log.d("CARD CLICKED", itemVM.memeId.toString())
            /*var context = holder.card.getContext()
            var intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("ID", itemVM.memeId )
            context.startActivity(intent)*/
        }
    }

    override fun getItemCount(): Int {
        // size of the list/datasource
        return favoriteList.size
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val topText : TextView = view.findViewById(R.id.topText)
    val bottomText : TextView = view.findViewById(R.id.bottomText)
    val card : CardView = view.findViewById(R.id.memeCard)
}