package com.example.memeoroid.retrofit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ApiViewModel(val repo: TemplateRepo) : ViewModel() {
    var templateList = MutableLiveData<List<MemeTemplate>>()
    var job : Job? = null

    fun getAllTemplates() {
        job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getAllTemplates()
            if (res.isSuccessful) {
                println("Success!")
                templateList!!.postValue(res.body())
            } else {
                println("Not successful!")
            }
        }
    }
}