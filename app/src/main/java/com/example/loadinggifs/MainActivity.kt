package com.example.loadinggifs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.loadinggifs.model.network.Data
import com.example.loadinggifs.model.network.GifApiServiceImpl
import com.example.loadinggifs.model.network.GifResponse
import com.example.loadinggifs.view.GifsAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class MainActivity : AppCompatActivity() {


    private lateinit var gifsAdapter: GifsAdapter
    private lateinit var gifsListView: RecyclerView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        gifsListView = findViewById(R.id.gifsList)


        gifsAdapter = GifsAdapter().apply {
            clickListener = this@MainActivity::onItemClicked
        }

        gifsListView.adapter = gifsAdapter

        loadGifs()

    }

    private fun onItemClicked(data: Data) {
        val fullScreenIntent = Intent(this, BigGif::class.java).apply {
            putExtra("url", data)
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
                Toast.makeText(this@MainActivity, "it's pailure", Toast.LENGTH_SHORT).show()
            }
            Log.d(MainActivity::javaClass.name, "Api call failed", t)
        }

    }


}