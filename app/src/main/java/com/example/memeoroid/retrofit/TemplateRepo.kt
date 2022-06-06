package com.example.memeoroid.retrofit

class TemplateRepo(val inter : RetroApiInterface) {
    suspend fun getAllTemplates() = inter.getAllTemplates()
}