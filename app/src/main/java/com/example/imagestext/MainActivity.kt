package com.example.imagestext

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var image1 : ImageView
    //lateinit var image2 : ImageView
    lateinit var btnDrawText : Button
    lateinit var saveBtn : Button
    lateinit var textIn : EditText
    lateinit var textIn2 : EditText
    lateinit var bitmapOriginal : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDrawText = findViewById(R.id.drawtext)
        saveBtn = findViewById(R.id.saveImage)
        textIn = findViewById(R.id.textin)
        textIn2 = findViewById(R.id.textin2)

        image1 = findViewById(R.id.image1)
        //image2 = findViewById(R.id.image2)


        val bigImage =BitmapFactory.decodeResource(resources, R.drawable.whiteback)
        val smallImage = BitmapFactory.decodeResource(resources, R.drawable.dog)

        bitmapOriginal = loadBackground(bigImage, smallImage)
        image1.setImageBitmap(bitmapOriginal)

        btnDrawText.setOnClickListener{
            loadImageText()
        }

        saveBtn.setOnClickListener{
            var filename : String = String.format("%d.png",System.currentTimeMillis())
            image1.buildDrawingCache()
            val uri = saveImageToGallery("$filename")
            image1.setImageURI(uri)
            Toast.makeText(applicationContext, "File Saved", Toast.LENGTH_LONG).show()
        }

        loadImageText()
    }

    private fun saveImageToGallery(title:String): Uri {
        val bitmap = (image1.getDrawable() as BitmapDrawable).bitmap
        val savedImageURL = MediaStore.Images.Media.insertImage(contentResolver, bitmap, title,
            "Image of $title")
        return Uri.parse(savedImageURL)
    }

    private fun loadBackground(firstImage: Bitmap, secondImage: Bitmap): Bitmap {

        val backgroundHeight = 1600
        val whiteSpace = 800f

        val result = Bitmap.createBitmap(secondImage.width, secondImage.height+backgroundHeight, secondImage.config)
        val canvas = Canvas(result)

        canvas.drawBitmap(firstImage, 0f, 0f, null)
        canvas.drawBitmap(secondImage, 0f, whiteSpace, null)
        return result
    }


    private fun loadImageText(){

        var fontColor = Color.BLACK
        var fontSize = 500.0F

        val textDrawTop = textIn.text.toString()
        val textDrawBottom = textIn2.text.toString()
        val newBitmap = bitmapOriginal.copy(bitmapOriginal.config,true)
        val newCanvas = Canvas(newBitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = fontColor
        paint.textSize = fontSize
        val bounds = Rect()
        paint.getTextBounds(textDrawTop,0,textDrawTop.length,bounds)
        paint.getTextBounds(textDrawBottom,0,textDrawBottom.length,bounds)



        val x =800
        val y = 400
        val g = 800
        val h =newBitmap.height - 100

        newCanvas.drawText(textDrawTop,x.toFloat(),y.toFloat(),paint)
        newCanvas.drawText(textDrawBottom,g.toFloat(),h.toFloat(),paint)

        image1.setImageBitmap(newBitmap)
        //image2.setImageBitmap(newBitmap)
    }
}