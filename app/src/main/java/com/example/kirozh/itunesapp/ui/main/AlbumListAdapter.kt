package com.example.kirozh.itunesapp.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.kirozh.itunesapp.Album
import com.example.kirozh.itunesapp.R
import com.example.kirozh.itunesapp.databinding.AlbumItemBinding
import java.util.*

/**
 * @author Kirill Ozhigin on 19.11.2021
 */
private const val KEY = "com.example.kirozh.itunesapp.collectionId"

class AlbumListAdapter :
    RecyclerView.Adapter<AlbumListAdapter.ListViewHolder>() {
    private lateinit var albums: List<Album>

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setAlbums1")
    fun setAlbums(albums: List<Album>) {
        Collections.sort(albums)
        this.albums = albums
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = DataBindingUtil.inflate<AlbumItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.album_item,
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(KEY, album.collectionId)
            Navigation.findNavController(it)
                .navigate(R.id.action_listFragment_to_detailedFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class ListViewHolder(val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album) {
            binding.album = album
            binding.executePendingBindings()
        }
    }
}