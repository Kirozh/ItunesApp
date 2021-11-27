package com.example.kirozh.itunesapp.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.example.kirozh.itunesapp.Song
import com.example.kirozh.itunesapp.R
import com.example.kirozh.itunesapp.databinding.SongItemBinding


/**
 * @author Kirill Ozhigin on 22.11.2021
 */
class SongListAdapter : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    private lateinit var songs: List<Song>

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setAlbums1")
    fun setSongs(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = DataBindingUtil.inflate<SongItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.song_item,
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.executePendingBindings()
        }
    }
}