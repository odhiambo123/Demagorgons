package com.example.memeoroid.retrofit

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.R
import com.squareup.picasso.Picasso

class GalleryAdapter(private var templateList: List<MemeTemplate>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setItems(templateList: List<MemeTemplate>){
        this.templateList = templateList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // add element to view holder
        val itemVM = templateList[position]
        holder.name.text = itemVM.name

        var context = holder.name.getContext()
        Picasso.with(context).load(itemVM.image).into(holder.image)
    }

    override fun getItemCount(): Int {
        // size of the list/datasource
        return templateList.size
    }
}

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val name : TextView = view.findViewById(R.id.memeName)
    val image : ImageView = view.findViewById(R.id.templateImage)
}