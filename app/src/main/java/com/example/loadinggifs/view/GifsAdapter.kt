package com.example.loadinggifs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loadinggifs.R
import com.example.loadinggifs.model.network.Data
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition

class GifsAdapter:RecyclerView.Adapter<GifViewHolder>() {

    var giflist:List<Data> = emptyList()
    var clickListener:((Data)-> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GifViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.gif_item_view,parent,false)
        )

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val thisGif = giflist[position]

        holder.bind(title = thisGif.title,gifUrl =thisGif.images.downsized_medium.url)

        holder.itemView.setOnClickListener{
            clickListener?.invoke(thisGif)
        }
    }

    override fun getItemCount() = giflist.size

    fun submitList(gifList: List<Data>){
        this.giflist =gifList
        notifyDataSetChanged()
    }

}
class GifViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    private val imageView: ImageView = itemView.findViewById(R.id.gifImageView)
    private val titleView: TextView = itemView.findViewById(R.id.gifTitle)

    fun bind(title:String, gifUrl:String){
        titleView.text =title

        Glide.with(imageView.context)
            .load(gifUrl)
            .placeholder(R.drawable.loading)
            .thumbnail(Glide.with(itemView.context).load(R.drawable.loading))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}