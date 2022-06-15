package com.example.memeoroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.retrofit.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    // Retrofit is a type-safe HTTP client for kotlin and Android
    // First steps..
    // 1. Add dependencies in Gradle
    // 2. Add permission to access the internet in Manifest
    // 3. Create retrofit instance
    // 4. Create retrofit interface
    // 5. Consume Rest API endpoints (response --> success, error)
    // 6. Process and attach it to recyclerview

    lateinit var vm: ApiViewModel
    var templateList: List<MemeTemplate> = listOf<MemeTemplate>();
    var startpoint = 0
    val limit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        supportActionBar?.hide()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val floatingBtn : FloatingActionButton = findViewById(R.id.floatingBtn)

        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(this)

        val adapter = GalleryAdapter(templateList)
        recyclerView2.adapter = adapter

        val inter = RetroApiInterface.create()
        val repo = TemplateRepo(inter)
        vm = ApiViewModel(repo)

        vm.getAllTemplates()

        vm.templateList.observe(this) {
            templateList = it
            startpoint = 0
            var endpoint = startpoint+ limit
            if(endpoint > (templateList.size-1)){
                endpoint = templateList.size-1
            }
            var buffer = templateList.subList(startpoint , startpoint+limit)
            adapter.setItems(buffer)

            //set load more visibility true
        }
        NextGallary.setOnClickListener {
            startpoint += limit

            loadData(startpoint, adapter)

        }

        PrevGallary.setOnClickListener {
            startpoint -= limit
            if(startpoint <0){
                startpoint = 0
            }
            loadData(startpoint, adapter)
        }
    }

    fun loadData(startpoint: Int, adapter: GalleryAdapter){
        var endpoint = startpoint+ limit

        if(endpoint > (templateList.size-1)){
            endpoint = templateList.size-1
        }

        if(endpoint == templateList.size-1){
            NextGallary.visibility = View.GONE
            PrevGallary.visibility = View.VISIBLE
        }else{
            NextGallary.visibility = View.VISIBLE
        }
        if(startpoint > 0){
            PrevGallary.visibility = View.VISIBLE
        }else{
            PrevGallary.visibility = View.GONE
        }
        var buffer = templateList.subList(startpoint , startpoint+limit)
        adapter.setItems(buffer)
    }
}