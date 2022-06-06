package com.example.memeoroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.memeoroid.retrofit.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_new.*


class NewActivity : AppCompatActivity() {

    lateinit var vm: ApiViewModel
    var templateList: List<MemeTemplate> = listOf<MemeTemplate>();
    var descriptions: MutableList<String> = ArrayList()
    var urls: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        val inter = RetroApiInterface.create()
        val repo = TemplateRepo(inter)
        vm = ApiViewModel(repo)

        vm.getAllTemplates()

        vm.templateList.observe(this) {
            //println(it)
            for (item in it) {
                descriptions.add(item.name)
                urls.add(item.image)
            }
            //println(descriptions)
            //println(urls)
            var dropdownAdapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, descriptions)
            dropdown.adapter = dropdownAdapter
            dropdown.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>, view: View?,
                    position: Int, id: Long
                ) {
                    //Log.v("item", parent.getItemAtPosition(position) as String)
                    Picasso.with(this@NewActivity).load(urls[position]).into(imageView)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Picasso.with(this@NewActivity).load(urls[0]).into(imageView)
                }
            })
        }

        resetButton.setOnClickListener() {
            editTopText.text.clear()
            editBottomText.text.clear()
            dropdown.setSelection(0)
            Picasso.with(this@NewActivity).load(urls[0]).into(imageView)
        }

    }
}