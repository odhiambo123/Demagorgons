package com.example.memeoroid.retrofit

data class MemeTemplate(var ID: Int,
                        var bottomText: String,
                        var image: String,
                        var name: String,
                        var rank: Int,
                        var tags: String,
                        var topText: String)