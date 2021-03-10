package com.example.loadinggifs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.loadinggifs.model.network.ImageData
import com.example.loadinggifs.model.network.GifApiServiceImpl
import com.example.loadinggifs.model.network.GifResponse
import com.example.loadinggifs.view.GifsAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {


    private val IMAGE_DATA_KAY = "image_kay"

    private lateinit var gifsAdapter: GifsAdapter
    private lateinit var gifsListView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var imageDataList = emptyList<ImageData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        gifsListView = findViewById(R.id.gifsList)


        gifsAdapter = GifsAdapter().apply {
            clickListener = this@MainActivity::onItemClicked
        }

        gifsListView.adapter = gifsAdapter

        if (savedInstanceState == null) {
            loadGifs()
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelableArrayList<ImageData>(IMAGE_DATA_KAY)?.takeIf { it.isNotEmpty() }?.let { ArrListImage ->
            imageDataList = ArrListImage
            gifsAdapter.submitList(ArrListImage)
            setProgressVisibility(false)
        } ?: run {
            loadGifs()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(IMAGE_DATA_KAY,ArrayList(imageDataList))
        super.onSaveInstanceState(outState)
    }

    private fun onItemClicked(imageData: ImageData) {
        val fullScreenIntent = Intent(this, BigGif::class.java).apply {
            putExtra("url", imageData)
        }
        startActivity(fullScreenIntent)
    }

    private fun setProgressVisibility(show: Boolean) {
        progressBar.isVisible = show
    }

    private fun loadGifs() {
        val call = GifApiServiceImpl
                .service
                .fetchTrendingGifs("cpI91hYtFNmKJz9y8TcR7Jp8qnSIY753")

        call.enqueue(GifCallback())
        setProgressVisibility(true)
    }

    inner class GifCallback : Callback<GifResponse> {
        override fun onResponse(call: Call<GifResponse>, response: Response<GifResponse>) {
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    gifsAdapter.submitList(it)
                }
            }
            setProgressVisibility(false)
        }

        override fun onFailure(call: Call<GifResponse>, t: Throwable) {
            setProgressVisibility(false)
            repeat(30) {
                Toast.makeText(this@MainActivity, "it's failure", Toast.LENGTH_SHORT).show()
            }
            Log.d(MainActivity::javaClass.name, "Api call failed", t)
        }

    }


}