package com.example.memeoroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memeoroid.retrofit.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val recyclerView2 = findViewById<RecyclerView>(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(this)

        var adapter = GalleryAdapter(templateList)
        recyclerView2.adapter = adapter

        val inter = RetroApiInterface.create()
        val repo = TemplateRepo(inter)
        vm = ApiViewModel(repo)

        vm.getAllTemplates()

        vm.templateList.observe(this) {
            adapter.setItems(it)
            //println(it)
        }

    }
}