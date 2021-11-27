package com.example.kirozh.itunesapp.ui.main

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kirozh.itunesapp.Album
import com.example.kirozh.itunesapp.AlbumRepository
import com.example.kirozh.itunesapp.Song
import com.example.kirozh.itunesapp.getDatabase
import kotlinx.coroutines.*

class MainViewModel(
    repository: AlbumRepository,
    application: Application
) : ViewModel() {
    var repository: AlbumRepository = repository

    private var database = getDatabase(application)

    val errorMessage = MutableLiveData<String>()
    val albumList = MutableLiveData<List<Album>>()
    val songList = MutableLiveData<List<Song>>()
    val albumData = MutableLiveData<Album>()
    val progressBarVisibility = MutableLiveData<Int>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    @DelicateCoroutinesApi
    fun getAllAlbums(queryString: String) {

        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            progressBarVisibility.postValue(View.VISIBLE)
            val response = repository.getAlbums(queryString)

            withContext(Dispatchers.Main) {
                try {
                    albumList.postValue(response)
                } catch (e: Exception) {
                    onError(e.message.toString())
                }
            }
            progressBarVisibility.postValue(View.GONE)
        }
    }

    @DelicateCoroutinesApi
    fun getAlbumSongs(albumId: String) {

        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = repository.getSongs(albumId)
            database.musicDao().insertSongsToRoom(response)
            withContext(Dispatchers.Main) {
                try {
                    songList.postValue(response)
                } catch (e: Exception) {
                    onError(e.message.toString())
                }
            }
        }
    }

    @DelicateCoroutinesApi
    fun getAlbumById(albumId: String) {
        GlobalScope.launch(Dispatchers.IO + exceptionHandler) {
            val response = repository.getAlbum(albumId)

            withContext(Dispatchers.Main) {
                try {
                    albumData.postValue(response as Album?)
                } catch (e: Exception) {
                    onError(e.message.toString())
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }
}