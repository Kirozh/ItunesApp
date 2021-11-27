package com.example.kirozh.itunesapp.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.kirozh.itunesapp.*

class ListFragment : Fragment() {
    lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AlbumListAdapter
    private lateinit var viewModel: MainViewModel
    private var isConnected: Boolean = true
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = RetrofitApi.getInstance()
        val database = getDatabase(requireContext())

        val repository = AlbumRepository(retrofitService, database)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(repository, requireActivity().application)
        ).get(MainViewModel::class.java)

        adapter = AlbumListAdapter()

        val connectionManager: ConnectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

        if (!isConnected) {
            Toast.makeText(activity, getString(R.string.noInternetConnection), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        searchView = view.findViewById(R.id.searchView)

        if (isConnected)
            searchView.queryHint = getString(R.string.search)
        else
            searchView.queryHint = getString(R.string.searchInLocalDatabase)

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    viewModel.getAllAlbums(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    viewModel.getAllAlbums(newText!!)
                    return false
                }
            })

        progressBar = view.findViewById(R.id.progressBar)

        recyclerView = view.findViewById(R.id.albumList)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = adapter
        adapter.setAlbums(emptyList())

        viewModel.albumList.observe(this, {
            adapter.setAlbums(it)
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })

        viewModel.progressBarVisibility.observe(this, {
            progressBar.visibility = it
        })

        viewModel.getAllAlbums("")

        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity ).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity ).supportActionBar?.show()
    }
}