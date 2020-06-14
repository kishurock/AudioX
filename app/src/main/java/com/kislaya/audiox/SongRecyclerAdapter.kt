package com.kislaya.audiox

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongRecyclerAdapter(val context: Context, private val songList: ArrayList<SliderItem>) :
    RecyclerView.Adapter<SongRecyclerAdapter.SongRecyclerViewHolder>() {
    class SongRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val songName: TextView = view.findViewById(R.id.txtSongName)
        val songImage: ImageView = view.findViewById(R.id.imgSongImage)
        val rlOne: RelativeLayout = view.findViewById(R.id.rlOne)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_item_single, parent, false)
        return SongRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }


    override fun onBindViewHolder(holder: SongRecyclerViewHolder, position: Int) {
        val songObject = songList[position]
        holder.songName.text = songObject.title
        holder.songImage.setImageResource(songObject.image)
        holder.rlOne.setOnClickListener {
            val intent = Intent(context, MusicActivity::class.java)
            intent.putExtra("songImage", songObject.image)
            intent.putExtra("songTitle", songObject.title)
            intent.putExtra("song", songObject.song)
            context.startActivity(intent)
        }

    }
}