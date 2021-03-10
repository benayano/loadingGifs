package com.example.loadinggifs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.loadinggifs.model.network.ImageData

class BigGif : AppCompatActivity() {

    private val imageView: ImageView by lazy { findViewById(R.id.fullImage) }
    private val send: ImageView by lazy { findViewById(R.id.chatButton) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.full_screen_image)

        val url = intent.getParcelableExtra<ImageData>("url")

        url?.let {
            loadImage(it.images.downsized_medium.url)
        }

        val actionBar = supportActionBar
        actionBar?.let {
            it.title = "this big gif"
            it.setDisplayHomeAsUpEnabled(true)
        }

        send.setOnClickListener {
            url?.let {  sendUrl(it.images.downsized_medium.url) }
        }
    }
    private fun sendUrl(url: String){

        val messange : String ="Hello oreld this my app send for you this text and URL ${url} \n " +
                "but I did not fully understand how to do that😉😉" +
                "\n Benaya"
        val SendIntent = Intent().apply{
            action =Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, messange)
        }

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