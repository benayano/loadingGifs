package com.example.loadinggifs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.loadinggifs.model.network.Data

class BigGif : AppCompatActivity() {
    private val imageView: ImageView by lazy { findViewById(R.id.fullImage) }


    private lateinit var send: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.full_screen_image)

        val url = intent.getParcelableExtra<Data>("url")

        url?.let {
            loadImage(it.images.downsized_medium.url)
        }

        val actionBar = supportActionBar
        actionBar?.let {
            it.title = "this big gif"
            it.setDisplayHomeAsUpEnabled(true)
        }

        send = findViewById(R.id.chatButton)
        send.setOnClickListener {
            url?.let {  sendUrl(it.images.downsized_medium.url) }
        }
    }
    private fun sendUrl(url: String){
        val intent = Intent(Intent.ACTION_SEND)
        val messange:String ="Hello oreld this my app send for you this text and URL ${url} \n " +
                "but I did not fully understand how to do that😉😉" +
                "\n Benaya"

        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, messange)
        startActivity(Intent.createChooser(intent, "sher to :"))
    }

    private fun loadImage(url: String) {
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .thumbnail(Glide.with(this).load(R.drawable.loading))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}