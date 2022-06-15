package com.example.memeoroid.roomdb

import com.example.memeoroid.R
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.CustomMemeDisplayActivity
import com.example.memeoroid.retrofit.ApiViewModel
import com.example.memeoroid.retrofit.MemeTemplate
import com.example.memeoroid.retrofit.RetroApiInterface
import com.example.memeoroid.retrofit.TemplateRepo
import com.squareup.picasso.Picasso

// Adapter, which makes a view for each meme in the dataset
class ListAdapter(private val favoriteList: MutableList<Meme>): RecyclerView.Adapter<ViewHolder>() { //changed to mutable list from list

    lateinit var vm: ApiViewModel
    var templateList: List<MemeTemplate> = listOf<MemeTemplate>();
    private var urls: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.meme_list_item, parent, false)

        val inter = RetroApiInterface.create()
        val repo = TemplateRepo(inter)
        vm = ApiViewModel(repo)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add element to view holder
        val itemVM = favoriteList[position]
        holder.topText.text = itemVM.topText
        holder.bottomText.text = itemVM.bottomText

        var context = holder.card.getContext()

        vm.getAllTemplates()
        vm.templateList.observe(context as AppCompatActivity) {
            for (item in it) {
                urls.add(item.image)
            }
            Picasso.with(context).load(urls[itemVM.image_description.toInt()]).into(holder.thumbnail)
        }
        //holder.thumbnail = itemVM.image_description

        holder.card.setOnClickListener() {
            var intent = Intent(context, CustomMemeDisplayActivity::class.java)
            intent.putExtra("imageSelected",itemVM.image_description)
            intent.putExtra("topText",itemVM.topText)
            intent.putExtra("bottomText",itemVM.bottomText)
            intent.putExtra("memeId",itemVM.memeId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        // size of the list/datasource
        return favoriteList.size
    }
    //DAB CODE
     fun deleteSwipedFavorite(index: Int){
        favoriteList.removeAt(index)
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val topText : TextView = view.findViewById(R.id.topText)
    val bottomText : TextView = view.findViewById(R.id.bottomText)
    val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    val card : CardView = view.findViewById(R.id.memeCard)
}