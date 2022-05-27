package com.example.memeapp

import android.content.Intent
import android.content.Intent.ACTION_CHOOSER
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    var geturl: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme()
    {
        val image=findViewById<ImageView>(R.id.imageView)
        val progress=findViewById<ProgressBar>(R.id.Progress)
        progress.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
         val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest =JsonObjectRequest (
            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                geturl=response.getString("url")
                Glide.with(this).load(geturl).listener(object :RequestListener<Drawable>  {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                       progress.visibility=View.GONE
                        return false
                    }
                }).into(image)
            },
            {
                Toast.makeText(this,"something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun sharememe(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"share ... $geturl")
        val chooser=Intent.createChooser(intent,"share this meme using...")
        startActivity(chooser)
    }
    fun nextmeme(view: View) {
        loadmeme()
    }
}