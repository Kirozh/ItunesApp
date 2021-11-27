package com.example.kirozh.itunesapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.kirozh.itunesapp.*
import com.example.kirozh.itunesapp.databinding.FragmentDetailedBinding
import com.squareup.picasso.Picasso

const val COLLECTION_KEY = "com.example.kirozh.itunesapp.collectionId"

class DetailedFragment : Fragment() {
    private var album: Album? = null
    private lateinit var binding: FragmentDetailedBinding
    private lateinit var recyclerView: RecyclerView
    private var adapter = SongListAdapter()
    lateinit var viewModel: MainViewModel
    lateinit var collectionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = RetrofitApi.getInstance()
        val database = getDatabase(requireContext())

        val repository = AlbumRepository(retrofitService, database)

        collectionId = arguments?.getString(COLLECTION_KEY).toString()

        viewModel = ViewModelProvider(
            requireActivity(), MainViewModelFactory(
                repository, requireActivity().application
            )
        ).get(MainViewModel::class.java)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false)
        val view = binding.root
        recyclerView = binding.songRecyclerView
        binding.album = album

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.albumData.observe(this, {
            album = it
            binding.album = album

            Picasso.get()
                .load(album?.artworkUrl100)
                .resize(300, 300)
                .placeholder(context!!.resources.getDrawable(R.drawable.image_default))
                .into(binding.image)
        })

        recyclerView.adapter = adapter
        adapter.setSongs(emptyList())

        viewModel.songList.observe(this, {
            adapter.setSongs(it)
            Log.d("TAG", it.size.toString())
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        // Downloading list of songs
        viewModel.getAlbumById(collectionId)
        viewModel.getAlbumSongs(collectionId)

    }
}